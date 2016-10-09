package com.rdc.takebus.model.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.rdc.takebus.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ZZH on 2016/5/26.
 */
public class BitmapUtil {
    // 该方法用来存储图片，当用户选择好图片，并截图确认后调用该方法进去存储
    // 以便实现同步
    public static void saveBitmap(Bitmap bitmap) {
        // 打开手机根目录
        File fileDir = new File(Environment.getExternalStorageDirectory()
                + "/RdcBus/");
        System.out.println(fileDir.exists() + "??");
        // 如果该文件不存在，则自己创建一个
        if (!fileDir.exists())
            fileDir.mkdir();
        try {
            // 在该目录下创建文件
            File file = new File(fileDir.getCanonicalPath() + "person_head.png");
            FileOutputStream fos = new FileOutputStream(file);
            // 将Bitmap输出到OutputStream上进行存储
            // 第一个参数是图片格式，第二个是图片质量，100为原图，第三个是输出流
            bitmap.compress(Bitmap.CompressFormat.PNG, 75, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("出错啦？？");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readBitmap() {
        File fileDir = new File(Environment.getExternalStorageDirectory()
                + "/RdcBus/");
        System.out.println(fileDir.exists() + "??");
        if (fileDir.exists()) {
            try {
                File file = new File(fileDir.getCanonicalPath()
                        + "person_head.png");
                FileInputStream fis = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                fis.close();
                return (bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 返回文件的Uri形式
    public static File getImageHeadFile() {
        File fileDir = new File(Environment.getExternalStorageDirectory()
                + "/RdcBus/");
        System.out.println(fileDir.exists() + "??");
        if (fileDir.exists()) {
            try {
                File file = new File(fileDir.getCanonicalPath()
                        + "person_head.png");
                return file;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
