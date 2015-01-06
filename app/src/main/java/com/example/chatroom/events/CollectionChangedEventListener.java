package com.example.chatroom.events;

public interface CollectionChangedEventListener<Type> {
    void onCollectionChanged(Type item, CollectionChangedType eventType);
}
