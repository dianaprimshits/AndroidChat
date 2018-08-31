package com.bigsur.AndroidChatWithMaps.DBManager;


import java.util.ArrayList;

public interface StorageManager {
     void create(Contacts contact);
     void update(Contacts contact);
     void delete(int id);
     Contacts getOne(int id);
     ArrayList<Contacts> getAll();
     //   CustomAdapter getAdapter(Context context);

}
