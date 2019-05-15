package com.example.zxg.myprogram.test;

import java.util.concurrent.ArrayBlockingQueue;

public class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int capacity) {
        super(capacity);
    }

    public String toString() {
        if (this.size() == 0) {
            return "[empty]";
        }

        StringBuilder result = new StringBuilder();
        for(Customer customer : this) {
            result.append(customer);
        }
        return result.toString();
    }
}
