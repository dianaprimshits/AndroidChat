package com.bigsur.AndroidChatWithMaps.DBManager;


public class DataFromDB<T> {
    private T data;


    public DataFromDB(T data) {
        this.data = data;
    }

    public String toSting() {
        return String.format("{%s, %s}", data.getClass().getSimpleName(), data);
    }

    public T getData() {
        return data;
    }
}
