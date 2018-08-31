package com.bigsur.AndroidChatWithMaps.DBManager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Contacts.class, parentColumns = "id", childColumns = "contactId", onDelete = CASCADE),
        indices = { @Index(value = "id")})
public class Messages {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String message;
    private SimpleDateFormat date;
    @ColumnInfo(name = "contact_id")
    private int contactId;

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public int getContactId() {
        return contactId;
    }


    public Messages(String message, SimpleDateFormat date, int contactId) {
        this.message = message;
        this.date = date;
        this.contactId = contactId;
    }


    public String toString(){
        return String.format("\n Message id: %d \n Message text: %s \n Message date: %t \n Contact id: %d.",
                getId(), getMessage(), getDate(), getContactId());
    }
}
