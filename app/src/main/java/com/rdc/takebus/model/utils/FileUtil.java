package com.rdc.takebus.model.utils;

import android.content.Context;

import java.io.File;

/**
 * 文件处理工具类
 */
public class FileUtil {


    /**
     * 获取缓存文件对象
     */
    public String getCacheFileObject(Context context) {
        File dir = context.getCacheDir();
        return new File(dir,"cacheImage.png").toString();
    }

}
