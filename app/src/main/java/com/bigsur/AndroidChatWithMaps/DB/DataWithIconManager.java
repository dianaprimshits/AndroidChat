package com.bigsur.AndroidChatWithMaps.DB;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface DataWithIconManager {
    void create(DataWithIcon data);
    void update(DataWithIcon data);
    void delete(int id);
    DataWithIcon getById(int id) throws ExecutionException, InterruptedException;
    ArrayList<DataWithIcon> getAll() throws ExecutionException, InterruptedException;

}
