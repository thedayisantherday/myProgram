package com.example.zxg.myprogram.algorithm;

import java.util.HashMap;

/**
 * description ：
 * Created by zhuxiaoguang at 11:18 on 2019/11/1
 */
public class StringAlgorithm {

    public static int maxLengthSubstring(String parent) {
        int lastRepeat = -1, result = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < parent.length(); i++) {
            Character tempC = parent.charAt(i);
            Integer tempI = map.get(tempC);
            if (tempI != null) {
                if (i - Math.max(tempI, lastRepeat) - 1 > result) {
                    result = i - Math.max(tempI, lastRepeat) - 1;
                }
                lastRepeat = Math.max(tempI, lastRepeat);
            } else {
                result = i - lastRepeat;
            }
        }
        return result;
    }

}
