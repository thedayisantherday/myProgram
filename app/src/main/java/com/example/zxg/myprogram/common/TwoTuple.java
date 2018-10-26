package com.example.zxg.myprogram.common;

public class TwoTuple<A, B> {
    public final A first; // final确保只能在构造函数中赋值，但可以随心所欲的使用
    public final B second;

    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return "(" + first + "," + second + ")";
    }
}
