package com.example.chatroom.interfaces;

import com.example.chatroom.events.SocketIOMessageListener;

public interface ISocketIOService {
    void setOnGotMessageListener(SocketIOMessageListener listener);

    void emitMessage(String message);
}
