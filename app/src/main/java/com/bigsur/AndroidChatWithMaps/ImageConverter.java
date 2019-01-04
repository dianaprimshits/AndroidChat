package com.bigsur.AndroidChatWithMaps;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageConverter {
    public static Bitmap convertToBitmap(String stringImage) {
        try {
            byte[] encodeByte = Base64.decode(stringImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
