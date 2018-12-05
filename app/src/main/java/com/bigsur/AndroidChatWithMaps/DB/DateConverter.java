package com.bigsur.AndroidChatWithMaps.DB;


import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public long fromHobbies(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date toDate(long milliseconds) {
        return new Date(milliseconds);
    }
}
