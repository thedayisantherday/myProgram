package com.example.zxg.myprogram.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * description ：leetcode 17——电话号码的字母组合
 * Created by zhuxiaoguang at 15:23 on 2019/9/5
 */
public class PhoneLetterCombination {

    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) return new ArrayList<>();

        List<String> result = new ArrayList<>();

        int length = digits.length();
        int[] index = new int[length];
        while (!letterNum(index[0], digits.charAt(0))) {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                switch (digits.charAt(i)) {
                    case '2':
                        switch (index[i]) {
                            case 0:
                                strBuilder.append('a');
                                break;
                            case 1:
                                strBuilder.append('b');
                                break;
                            case 2:
                                strBuilder.append('c');
                                break;
                        }
                        break;
                    case '3':
                        switch (index[i]) {
                            case 0:
                                strBuilder.append('d');
                                break;
                            case 1:
                                strBuilder.append('e');
                                break;
                            case 2:
                                strBuilder.append('f');
                                break;
                        }
                        break;
                    case '4':
                        switch (index[i]) {
                            case 0:
                                strBuilder.append('g');
                                break;
                            case 1:
                                strBuilder.append('h');
                                break;
                            case 2:
                                strBuilder.append('i');
                                break;
                        }
                        break;
                    case '5':
                        switch (index[i]) {
                            case 0:
                                strBuilder.append('j');
                                break;
                            case 1:
                                strBuilder.append('k');
                                break;
                            case 2:
                                strBuilder.append('l');
                                break;
                        }
                        break;
                    case '6':
                        switch (index[i]) {
                            case 0:
                                strBuilder.append('m');
                                break;
                            case 1:
                                strBuilder.append('n');
                                break;
                            case 2:
                                strBuilder.append('o');
                                break;
                        }
                        break;
                    case '7':
                        switch (index[i]) {
                            case 0:
                                strBuilder.append('p');
                                break;
                            case 1:
                                strBuilder.append('q');
                                break;
                            case 2:
                                strBuilder.append('r');
                                break;
                            case 3:
                                strBuilder.append('s');
                                break;
                        }
                        break;
                    case '8':
                        switch (index[i]) {
                            case 0:
                                strBuilder.append('t');
                                break;
                            case 1:
                                strBuilder.append('u');
                                break;
                            case 2:
                                strBuilder.append('v');
                                break;
                        }
                        break;
                    case '9':
                        switch (index[i]) {
                            case 0:
                                strBuilder.append('w');
                                break;
                            case 1:
                                strBuilder.append('x');
                                break;
                            case 2:
                                strBuilder.append('y');
                                break;
                            case 3:
                                strBuilder.append('z');
                                break;
                        }
                        break;
                }
            }

            result.add(strBuilder.toString());

            index[length - 1]++;
            if (letterNum(index[length - 1], digits.charAt(length - 1))) {
                for (int j = length - 1; j > 0; j--) {
                    index[j] = 0;
                    index[j-1]++;
                    if (!letterNum(index[j-1], digits.charAt(j-1))) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    public static boolean letterNum(int index, char letter) {
        if (letter == '7' || letter == '9') {
            return index >= 4;
        }
        return index >= 3;
    }
}
