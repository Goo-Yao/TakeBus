package com.rdc.takebus.model.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理工具类
 */
public class TimeUtil {

    /**
     * 获取现在的时间
     */
    public static final String getCurTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
