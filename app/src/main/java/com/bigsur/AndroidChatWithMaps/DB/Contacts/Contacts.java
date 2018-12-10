package com.bigsur.AndroidChatWithMaps.DB.Contacts;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContact;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.Date;

@Entity(tableName = "contacts",
        indices = {@Index(value = {"contact_name", "phone_number"},
                          unique = true)})
public class Contacts implements DataWithIcon {
    @PrimaryKey(autoGenerate = true)public int _id;
    @ColumnInfo(name = "contact_name")
    private String contactName;
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "contact_avatar")
    private int contactAvatar;


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
        return getName();
    }

    public String getPhoneNumber() {
        return getSubname();
    }

    @Override
    public String getName() {
        return contactName;
    }

    @Override
    public String getSubname() {
        return phoneNumber;
    }

    @Override
    public int getAvatar() {
        return 0;
    }

    @Override
    public Date getExtraTitle() {
        return null;
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

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }


    public void setContactAvatar(int contactAvatar) {
        this.contactAvatar = contactAvatar;
    }

}
