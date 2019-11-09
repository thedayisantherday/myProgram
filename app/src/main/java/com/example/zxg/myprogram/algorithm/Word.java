package com.example.zxg.myprogram.algorithm;

import java.util.HashMap;
import java.util.List;

/**
 * description ：
 * Created by zhuxiaoguang at 15:14 on 2019/9/20
 */
public class Word {

    /*public static boolean wordBreak(String s, List<String> wordDict) {
        if (wordDict == null || wordDict.size() == 0) return false;

        return doWorkBreak(s, wordDict, 0, new Boolean[s.length()]);
    }

    private static boolean doWorkBreak(String s, List<String> wordDict, int start, Boolean[] memo) {

        if (start >= s.length()) return true;

        if (memo[start] != null) return memo[start];

        for (int i = start + 1; i <= s.length(); i++) {
            if (wordDict.contains(s.substring(start, i)) && doWorkBreak(s, wordDict, i, memo))
                return memo[start] = true;
        }

        return  memo[start] = false;
    }*/

    public static boolean wordBreak(String s, List<String> wordDict) {
        return doWorkBreak(s, wordDict, new HashMap<String, Boolean>());
    }

    private static boolean doWorkBreak(String s, List<String> wordDict, HashMap<String, Boolean> hash) {

        if (hash.get(s) != null) return hash.get(s);

        for (int i = wordDict.size() - 1; i >= 0; i--) {
            int index = s.indexOf(wordDict.get(i));
            if (index != -1) {
                String subStr2 = s.substring(index + wordDict.get(i).length());
                if ((index == 0 || doWorkBreak(s.substring(0, index), wordDict, hash))
                        && (subStr2.length() == 0 || doWorkBreak(subStr2, wordDict, hash))) {
                    hash.put(s, true);
                    return true;
                }
            }
        }
        hash.put(s, false);
        return false;
    }
}
