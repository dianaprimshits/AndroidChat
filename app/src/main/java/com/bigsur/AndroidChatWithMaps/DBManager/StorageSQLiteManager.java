package com.bigsur.AndroidChatWithMaps.DBManager;


import android.util.Log;

import com.bigsur.AndroidChatWithMaps.DBManager.crud.Create;
import com.bigsur.AndroidChatWithMaps.DBManager.crud.Delete;
import com.bigsur.AndroidChatWithMaps.DBManager.crud.GetAll;
import com.bigsur.AndroidChatWithMaps.DBManager.crud.GetById;
import com.bigsur.AndroidChatWithMaps.DBManager.crud.Update;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

public class StorageSQLiteManager implements StorageManager {

    @Override
    public void create(DataFromDB data) {
        Create db = new Create();
        db.execute(data);
    }

    @Override
    public void update(DataFromDB data) {
        Update db = new Update();
        db.execute(data);
    }

    @Override
    public void delete(int id) {
        Delete db = new Delete();
        db.execute(id);
    }

    @Override
    public DataFromDB getById(int id) throws ExecutionException, InterruptedException {
        GetById db = new GetById();
        db.execute(id);
        return db.get();
    }

    @Override
    public ArrayList<DataFromDB> getAll() throws InterruptedException, ExecutionException {
        GetAll db = new GetAll();
        db.execute();
        Log.d(TAG, "getAll: " + db.get().toString());
        return (ArrayList<DataFromDB>) db.get();
    }
}





