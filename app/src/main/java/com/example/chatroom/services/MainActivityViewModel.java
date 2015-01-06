package com.example.chatroom.services;


import android.content.Context;

import com.example.chatroom.events.CollectionChangedEventInitiator;
import com.example.chatroom.events.CollectionChangedEventListener;
import com.example.chatroom.events.SocketIOMessageListener;
import com.example.chatroom.interfaces.IMainActivityViewModel;
import com.example.chatroom.interfaces.ISocketIOService;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;

public class MainActivityViewModel implements IMainActivityViewModel {
    @Inject
    private ISocketIOService socketIOService;
    private List<String> messages;
    private CollectionChangedEventInitiator collectionChangedEvent;

    @Inject
    public MainActivityViewModel(Provider<Context> contextProvider) {

        Context c = contextProvider.get();
        RoboGuice.injectMembers(c, this);

        messages = new ArrayList<>();
        collectionChangedEvent = new CollectionChangedEventInitiator<>(messages);

        socketIOService.setOnGotMessageListener(new SocketIOMessageListener() {
            @Override
            public void onGotMessage(String message) {
                collectionChangedEvent.add(message);
            }
        });
    }


    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public void setOnCollectionChangedListener(CollectionChangedEventListener<String> collectionChangedListener) {
        collectionChangedEvent.addListener(collectionChangedListener);
    }

    @Override
    public void sendMessage(String message) {
        socketIOService.emitMessage(message);
    }

}
