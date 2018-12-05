package com.bigsur.AndroidChatWithMaps.DB.Messages;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DB.DateConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "messages",
        foreignKeys = {@ForeignKey(entity = ChatRooms.class,
                         parentColumns = "chat_room_id",
                         childColumns = "message_to",
                         onDelete = CASCADE)},
        indices = {@Index(value = "message_id",
                          unique = true)
        })

public class Messages {
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "message_id")
    public int messageId;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private Date date;
    @ColumnInfo(name = "message_from")
    private int messageFrom;
    //here will be chatRoomId
    @ColumnInfo(name = "message_to")
    private int messageTo;


    public Messages(String message, Date date, int messageFrom, int messageTo) {
        this.message = message;
        this.date = date;
        this.messageFrom = messageFrom;
        this.messageTo = messageTo;
    }

    public int getId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public int getMessageFrom() {
        return messageFrom;
    }

    public int getMessageTo() {
        return messageTo;
    }

}
