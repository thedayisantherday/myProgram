package com.example.zxg.myprogram.activities;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.AutoTouch;

import java.io.DataOutputStream;

public class AutoClickActivity extends Activity {


    private String[] search = {
            //  "adb shell",
            //    "sleep 3",
            "input keyevent 3",// 返回到主界面，数值与按键的对应关系可查阅KeyEvent
            "sleep 1",// 等待1秒
            "am start -n com.tencent.mm/com.tencent.mm.ui.LauncherUI",// 打开微信的启动界面，am命令的用法可自行百度、Google
            "sleep 3",// 等待3秒
            "am start -n com.tencent.mm/com.tencent.mm.plugin.search.ui.SearchUI",// 打开微信的搜索
            "input text 123",// 像搜索框中输入123，但是input不支持中文，蛋疼，而且这边没做输入法处理，默认会自动弹出输入法
    };
    public static AutoTouch autoTouch = new AutoTouch();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_click);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //传入所在比例
                autoTouch.autoClickRatio(AutoClickActivity.this, 0.4375, 0.537);
                //出入坐标
                autoTouch.autoClickPos(AutoClickActivity.this, 80, 500);

                //如果input text中有中文，可以将中文转成unicode进行input,没有测试，只是觉得这个思路是可行的
                search[5] = chineseToUnicode(search[5]);
                execShell(search);
            }
        }, 5000);
    }

    /**
     执行Shell命令
     @param commands
     要执行的命令数组
     */
    public void execShell(String[] commands) {
        // 获取Runtime对象
        Runtime runtime = Runtime.getRuntime();
        DataOutputStream os = null;
        try {
            // 获取root权限，这里大量申请root权限会导致应用卡死，可以把Runtime和Process放在Application中初始化
            Process process = runtime.exec("sh");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                } // donnot use os.writeBytes(commmand), avoid chinese charset // error
                os.write(command.getBytes());
                os.writeBytes("\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     把中文转成Unicode码
     @param str
     @return
     */
    public String chineseToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }

    /**
     判断是否为中文字符
     @param c
     @return
     */
    public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

}
