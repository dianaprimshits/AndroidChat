package com.bigsur.AndroidChatWithMaps.DBManager;


import java.util.List;
import java.util.concurrent.ExecutionException;

public interface StorageManager {
     void create(DataFromDB contact);
     void update(DataFromDB contact);
     void delete(int id);
     DataFromDB getById(int id) throws ExecutionException, InterruptedException;
     List<DataFromDB> getAll() throws ExecutionException, InterruptedException;
}
