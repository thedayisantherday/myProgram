package com.example.zxg.myprogram.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** UI相关工具类
 * Created by zxg on 2016/11/4.
 * QQ:1092885570
 */

public class UIUtils {

    /**
     * 设置EditText编辑框的小数位数（可以先用正则表达式判断是否匹配）
     * @param editText
     * @param decimalDigits 小数位数
     * @author zhuxiaoguang
     */
    public static void setEditTextDecimal(final EditText editText, final int decimalDigits) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //正则表达式匹配
                /*String regEx = "(^10{0,6}(\\.0{1,2})?)|(^[1-9]\\d{0,5}(\\.\\d{1,2})?)";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(s.toString().trim());
                boolean isMatch = matcher.find();*/

                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > decimalDigits) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + decimalDigits + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }
}
