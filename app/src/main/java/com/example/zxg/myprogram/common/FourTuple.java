package com.example.zxg.myprogram.common;

public class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
    public final D four;

    public FourTuple(A first, B second, C three, D four) {
        super(first, second, three);
        this.four = four;
    }

    public String toString() {
        return "(" + first + "," + second + "," + three + "," + four + ")";
    }
}
