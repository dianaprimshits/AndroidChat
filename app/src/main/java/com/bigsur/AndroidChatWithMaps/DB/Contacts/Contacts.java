package com.bigsur.AndroidChatWithMaps.DB.Contacts;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContact;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contacts",
        indices = {@Index(value = {"contact_name", "phone_number"},
                          unique = true)})
public class Contacts {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @SerializedName("name")
    @ColumnInfo(name = "contact_name")
    private String contactName;

    @SerializedName("phone")
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @SerializedName("avatar")
    @ColumnInfo(name = "contact_avatar")
    private String contactAvatar;


    public Contacts(String contactName, String phoneNumber) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public Contacts(DataWithIcon dataWithIcon) {
        if (dataWithIcon instanceof ViewableContact) {
            this.contactName =  dataWithIcon.getName();
            this.phoneNumber = dataWithIcon.getSubname();
        } else {
            throw new ClassCastException();
        }
    }


    public int getId() {
       return _id;
    }


    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContactAvatar() {
        return contactAvatar;
    }

    public String toString(){
        return String.format("Contacts id: %d \n name: %s \n phone number: %s \n avatar: %s",
                             getId(), contactName, phoneNumber, contactAvatar);
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactAvatar(String contactAvatar) {
        this.contactAvatar = contactAvatar;
    }
}
