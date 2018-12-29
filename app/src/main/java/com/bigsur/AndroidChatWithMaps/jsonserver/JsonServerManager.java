package com.bigsur.AndroidChatWithMaps.jsonserver;


import android.util.Log;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.jsonserver.api.ContactsApi;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JsonServerManager {
    Retrofit retrofit;
    private static String TAG = "!!!!!LOG!!!!!";

    public JsonServerManager() {
        retrofit = RetrofitBuilder.getRetrofitInstance();
    }

    public void tryLogin() {

    }

    public ArrayList<Contacts> getContacts() {
        ContactsApi loginApi = retrofit.create(ContactsApi.class);
        Call<ArrayList<Contacts>> contactList = loginApi.getAll();
        ArrayList<Contacts> contacts = new ArrayList<>();

        contactList.enqueue(new Callback<ArrayList<Contacts>>() {
            @Override
            public void onResponse(Call<ArrayList<Contacts>> call, Response<ArrayList<Contacts>> response) {
                if (response.isSuccessful()) {
                    contacts.addAll(response.body());
                    Log.d(TAG, "onResponse: success.");
                } else {
                    Log.d(TAG, "onResponse: fail.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Contacts>> call, Throwable throwable) {
                Log.d(TAG, "onFailure.");
            }
        });
        return contacts;
    }

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
      /*  if (isPosted) {
            timerTask.cancel();
        }*/
    }
}
