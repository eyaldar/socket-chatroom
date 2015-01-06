package com.example.chatroom.interfaces;

import com.example.chatroom.events.CollectionChangedEventListener;

import java.util.List;

public interface IMainActivityViewModel {
    List<String> getMessages();

    void setOnCollectionChangedListener(CollectionChangedEventListener<String> collectionChangedListener);

    void sendMessage(String message);
}
