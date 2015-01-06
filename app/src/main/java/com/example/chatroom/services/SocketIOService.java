package com.example.chatroom.services;

import android.util.Log;

import com.example.chatroom.R;
import com.example.chatroom.events.SocketIOMessageEventInitiator;
import com.example.chatroom.events.SocketIOMessageListener;
import com.example.chatroom.interfaces.ISocketIOService;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.emitter.Emitter.Listener;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.inject.Singleton;

import java.net.URISyntaxException;

import roboguice.inject.InjectResource;

@Singleton
public class SocketIOService implements ISocketIOService {
    @InjectResource(R.string.socket_io_event_name)
    private String EVENT_NAME = "chat message";

    private Socket socket;
    private SocketIOMessageEventInitiator gotMessageEvent;
    private Emitter.Listener messageArrivedListener = new Listener() {

        @Override
        public void call(Object... args) {
            if (args.length > 0) {
                Log.d("Got message!", args[0].toString());
                gotMessageEvent.sendMessage(args[0].toString());
            }
        }
    };

    public SocketIOService() {
        gotMessageEvent = new SocketIOMessageEventInitiator();

        try {
            // default SSLContext for all sockets
            IO.Options opts = new IO.Options();

            opts.forceNew = true;
            opts.reconnection = true;
            opts.secure = true;

            socket = IO.socket("https://socket-chatroom-server.herokuapp.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("SocketIOService: ", "socket connected");
                socket.emit(EVENT_NAME, "hi");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("SocketIOService: ", "socket connection error!");
            }
        })
                .on(EVENT_NAME, messageArrivedListener).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("SocketIOService: ", "socket disconnected");
            }
        });

        socket.connect();
    }

    @Override
    public void setOnGotMessageListener(SocketIOMessageListener listener) {
        gotMessageEvent.addListener(listener);
    }

    @Override
    public void emitMessage(String message) {
        socket.emit(EVENT_NAME, message);
    }

}
