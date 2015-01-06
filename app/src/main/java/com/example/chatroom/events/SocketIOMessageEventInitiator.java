package com.example.chatroom.events;

import java.util.ArrayList;
import java.util.List;

public class SocketIOMessageEventInitiator {
    List<SocketIOMessageListener> listeners = new ArrayList<SocketIOMessageListener>();

    public void addListener(SocketIOMessageListener toAdd) {
        listeners.add(toAdd);
    }

    public void sendMessage(String message) {
        System.out.println(message);

        // Notify everybody that may be interested.
        for (SocketIOMessageListener ml : listeners)
            ml.onGotMessage(message);
    }
}
