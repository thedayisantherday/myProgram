package com.example.zxg.myprogram.test;

/**
 * description ：
 * Created by zhuxiaoguang at 10:05 on 2019/8/29
 */
public class AutoUnpackAndPack {


    public static void main(String[] args) throws Exception {
        new AutoUnpackAndPack();
    }

    public AutoUnpackAndPack() {

        // jdk 1.5之前
        Integer integer1 = new Integer(1);
        Integer integer2 = Integer.valueOf(1);
        int int1 = integer1.intValue();

        // jdk 1.5之后
        Integer integer11 = 1;
        int int11 = integer11;

        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 200;
        Integer i4 = 200;
        System.out.println(i1==i2); //true
        System.out.println(i3==i4); //false
        System.out.println(i3==(i1+i2)); //false
    }
}
