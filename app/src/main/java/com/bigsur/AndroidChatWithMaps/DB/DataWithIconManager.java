package com.bigsur.AndroidChatWithMaps.DB;


import java.util.ArrayList;
import java.util.WeakHashMap;

public abstract class DataWithIconManager {
    WeakHashMap<Object, Runnable> listeners = new WeakHashMap<>();

    public abstract void create(DataWithIcon data);
    public abstract void update(DataWithIcon data);
    public abstract void delete(int id);
    public abstract DataWithIcon getById(int id);
    public abstract ArrayList<DataWithIcon> getAll();

    public void addDataChangeListener(Object listener, Runnable callBack) {
        listeners.put(listener, callBack);
    }

    public void  removeDataChangeListener(Object listener) {
        listeners.remove(listener);
    }

    protected void dataUpdated() {
        for (Object listener: listeners.keySet()){
            listeners.get(listener).run();
        }
    }
}
