package com.example.chatroom.events;


public interface SocketIOMessageListener {
    void onGotMessage(String messages);
}
