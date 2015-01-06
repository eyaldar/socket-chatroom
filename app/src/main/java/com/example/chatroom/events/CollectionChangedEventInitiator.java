package com.example.chatroom.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eyal on 06/01/2015.
 */
public class CollectionChangedEventInitiator<Type> {
    List<Type> collection;
    List<CollectionChangedEventListener<Type>> listeners = new ArrayList<>();

    public CollectionChangedEventInitiator(List<Type> collection) {
        this.collection = collection;
    }

    public void addListener(CollectionChangedEventListener<Type> toAdd) {
        listeners.add(toAdd);
    }

    public void add(Type item) {
        collection.add(item);
        fireEvent(item, CollectionChangedType.Added);
    }

    private void fireEvent(Type item, CollectionChangedType eventType) {
        // Notify everybody that may be interested.
        for (CollectionChangedEventListener<Type> ccel : listeners)
            ccel.onCollectionChanged(item, eventType);
    }

    public void remove(Type item) {
        collection.remove(item);
        fireEvent(item, CollectionChangedType.Removed);
    }
}
