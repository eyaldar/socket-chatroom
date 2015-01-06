package com.example.chatroom.application;

import com.example.chatroom.interfaces.IMainActivityViewModel;
import com.example.chatroom.interfaces.ISocketIOService;
import com.example.chatroom.services.MainActivityViewModel;
import com.example.chatroom.services.SocketIOService;
import com.google.inject.AbstractModule;

public class IOCModule extends AbstractModule {

    @Override
    protected void configure() {
        binder.bind(ISocketIOService.class).to(SocketIOService.class);
        binder.bind(IMainActivityViewModel.class).to(MainActivityViewModel.class);
    }
}
