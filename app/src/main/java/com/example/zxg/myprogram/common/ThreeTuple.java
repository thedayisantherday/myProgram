package com.example.zxg.myprogram.common;

public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
    public final C three;

    public ThreeTuple(A first, B second, C three) {
        super(first, second);
        this.three = three;
    }

    public String toString() {
        return "(" + first + "," + second + "," + three + ")";
    }
}
