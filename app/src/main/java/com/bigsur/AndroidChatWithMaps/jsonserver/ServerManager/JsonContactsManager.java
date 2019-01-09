package com.bigsur.AndroidChatWithMaps.jsonserver.ServerManager;


import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.jsonserver.RetrofitBuilder;
import com.bigsur.AndroidChatWithMaps.jsonserver.api.ContactsApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.FutureTask;

import retrofit2.Call;
import retrofit2.Retrofit;

public class JsonContactsManager {
    private static JsonContactsManager instance;
    Retrofit retrofit;
    private String TAG = "!!!LOG!!!";

    public JsonContactsManager() {
        this.retrofit = RetrofitBuilder.getRetrofitInstance();
    }

    public static JsonContactsManager getInstance() {
        if (instance == null) {
            instance = new JsonContactsManager();
        }
        return instance;
    }


    public FutureTask create(Contacts data) {
        return null;
    }


    public FutureTask<Boolean> update(Contacts data) {
        return null;
    }


    public FutureTask<Boolean> delete(int id) {
        FutureTask<Boolean> deleteContact = new FutureTask<>(() -> {
            ContactsApi contactsApi = retrofit.create(ContactsApi.class);
            Call call = contactsApi.deleteContact(id);
            Boolean isDeleted = false;
            try {
                call.execute();
                isDeleted = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return isDeleted;
        });
        return deleteContact;
    }


    public FutureTask<Contacts> getById(FutureTask<Integer> id) {
        return null;
    }


    public FutureTask<ArrayList<Contacts>> getAll() {/*
        FutureTask<ArrayList<Contacts>> getContactsFromServer = new FutureTask<ArrayList<Contacts>>(() -> {
            ContactsApi contactsApi = retrofit.create(ContactsApi.class);
            Call<ArrayList<Contacts>> call = contactsApi.getAll();
            ArrayList<Contacts> contacts = new ArrayList<>();

            call.enqueue(new Callback<ArrayList<Contacts>>() {
                @Override
                public void onResponse(Call<ArrayList<Contacts>> call, Response<ArrayList<Contacts>> response) {
                    if(response.isSuccessful()) {
                        contacts.addAll(response.body());
                        Log.d(TAG, "JsonContactsManager GETALL onResponse: success.");
                    } else {
                        Log.d(TAG, "JsonContactsManager GETALL onResponse: fail.");
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Contacts>> call, Throwable throwable) {
                    Log.d(TAG, "JsonContactsManager GETALL onResponse: fail.");
                }
            });

            return contacts;
        });*/


        FutureTask<ArrayList<Contacts>> getContactsFromServer = new FutureTask<>(() -> {
            ContactsApi contactsApi = retrofit.create(ContactsApi.class);
            Call<ArrayList<Contacts>> call = contactsApi.getAll();
            ArrayList<Contacts> contacts = new ArrayList<>();

            contacts.addAll(call.execute().body());
            return contacts;
        });

        return getContactsFromServer;
    }


    public FutureTask<ArrayList<Contacts>> getNew(Date date) {
        return null;
    }





   /*

    public void postContact(int id, String name, String subname, String avatar) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ContactsApi loginApi = retrofit.create(ContactsApi.class);

                Call<Contacts> call = loginApi.postContact(id, name, subname, avatar);

                call.enqueue(new Callback<Contacts>() {
                    @Override
                    public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                        if (response.isSuccessful()) {
                            cancel();
                            Log.d(TAG, "onResponse: success.");
                        } else {
                            Log.d(TAG, "onResponse: fail.");
                        }
                    }

                    @Override
                    public void onFailure(Call<Contacts> call, Throwable throwable) {
                        Log.d(TAG, "onFailure: fail " + throwable);
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 20000);
    }*/
}
