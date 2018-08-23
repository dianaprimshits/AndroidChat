package com.bigsur.AndroidChatWithMaps.DBManager;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name", "phone_number"}, unique = true)})
public class Contacts {
    @PrimaryKey(autoGenerate = true)public int id;
    private String name;
    private String phone_number;


    public Contacts(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String toString(){
        return String.format("Contacts id: %d \n name: %s \n phone number: %s.",
                             getId(), getName(), getPhone_number());
    }
}
