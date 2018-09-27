package com.bigsur.AndroidChatWithMaps.DBManager.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts",
        indices = {@Index(value = {"contact_name", "phone_number"},
                          unique = true)})
public class Contacts implements DataWithIcon {
    @PrimaryKey(autoGenerate = true)public int _id;

    public String getContactName() {
        return contactName;
    }

    public String getPhone_number() {
        return phone_number;
    }

    @ColumnInfo(name = "contact_name")
    private String contactName;
    private String phone_number;
    @ColumnInfo(name = "contact_avatar")
    private int contactAvatar;


    public Contacts(String contactName, String phone_number) {
        this.contactName = contactName;
        this.phone_number = phone_number;
    }


    public int getId() {
       return _id;
    }

    @Override
    public String getName() {
        return contactName;
    }

    @Override
    public String getSubname() {
        return phone_number;
    }

    @Override
    public int getAvatar() {
        return 0;
    }

    @Override
    public int getDate() {
        return 0;
    }



    public int getContactAvatar() {
        return contactAvatar;
    }

    public String toString(){
        return String.format("Contacts id: %d \n name: %s \n phone number: %s.",
                             getId(), getName(), getSubname());
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public void setContactAvatar(int contactAvatar) {
        this.contactAvatar = contactAvatar;
    }
}
