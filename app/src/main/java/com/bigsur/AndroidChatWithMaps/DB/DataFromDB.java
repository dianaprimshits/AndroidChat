package com.bigsur.AndroidChatWithMaps.DB;


public class DataFromDB<T> {
    private T data;


    public DataFromDB(T data) {
        this.data = data;
    }

    public String toString() {
        return String.format("{%s, %s}", data.getClass().getSimpleName(), data);
    }

    public T getData() {
        return data;
    }
}
