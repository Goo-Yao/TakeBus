package com.rdc.takebus.model.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 梦涵 on 2016/5/22.
 */
public class PhotoUtil {
    //获取用户头像
    public static Bitmap getBitmap(String imgUri){
        Bitmap bitmap = null;
        try {
            URL url = new URL(imgUri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            InputStream in = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

}
