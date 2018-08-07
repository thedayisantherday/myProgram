package com.example.zxg.myprogram.utils;

import java.util.Calendar;

/**
 * 时间相关工具类
 * Created by zxg on 2016/10/2.
 * QQ:1092885570
 */
public class TimeUtils {

    /**
     * 根据时间获取欢迎语
     * @return
     */
    public static String getWelcome() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(hour>=4 && hour<9) {
            return "早上好,";
        } else if(hour>=9 && hour<11) {
            return "上午好,";
        } else if(hour>=11 && hour<13) {
            return "中午好,";
        } else if(hour>=13 && hour<19) {
            return "下午好,";
        } else if((hour>=19 && hour<24)||(hour>=0 && hour<4)) {
            return "晚上好,";
        }else {
            return "欢迎您,";
        }
    }
}
