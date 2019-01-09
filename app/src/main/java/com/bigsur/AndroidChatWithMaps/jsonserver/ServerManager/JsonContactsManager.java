package com.bigsur.AndroidChatWithMaps.jsonserver.ServerManager;


import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.jsonserver.RetrofitBuilder;
import com.bigsur.AndroidChatWithMaps.jsonserver.api.ContactsApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JsonContactsManager implements ServerManager {
    private static JsonContactsManager instance;
    Retrofit retrofit;

    public JsonContactsManager() {
        this.retrofit = RetrofitBuilder.getRetrofitInstance();
    }

    public static JsonContactsManager getInstance() {
        if (instance == null) {
            instance = new JsonContactsManager();
        }
        return instance;
    }


    @Override
    public FutureTask create(DataWithIcon data) {
        return null;
    }

    @Override
    public FutureTask<Boolean> update(DataWithIcon data) {
        return null;
    }

    @Override
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

    @Override
    public FutureTask<DataWithIcon> getById(FutureTask<Integer> id) {
        return null;
    }

    @Override
    public FutureTask<ArrayList<DataWithIcon>> getAll() {
        FutureTask<ArrayList<DataWithIcon>> getContactsFromServer = new FutureTask<>(() -> {
            ContactsApi contactsApi = retrofit.create(ContactsApi.class);
            Call<ArrayList<Contacts>> call = contactsApi.getAll();
            ArrayList<DataWithIcon> contacts = new ArrayList<>();

            try {
                Response<ArrayList<Contacts>> response = call.execute();
                contacts.addAll(response.body().stream()
                        .map(i -> (DataWithIcon) i)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contacts;
        });

        return getContactsFromServer;
    }

    @Override
    public FutureTask<ArrayList<DataWithIcon>> getNew(Date date) {
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
