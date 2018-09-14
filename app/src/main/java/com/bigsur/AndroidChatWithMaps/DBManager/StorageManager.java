package com.bigsur.AndroidChatWithMaps.DBManager;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface StorageManager {
     void create(DataFromDB contact);
     void update(DataFromDB contact);
     void delete(int id);
     DataFromDB getById(int id) throws ExecutionException, InterruptedException;
     ArrayList<DataFromDB> getAll() throws ExecutionException, InterruptedException;
}
