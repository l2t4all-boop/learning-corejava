package com.careerit.cj.abs;

public class MethodOverLoading {


    public int add(int a, int b) {
        return a + b;
    }

    public long add(long a, int b){
        return a + b;
    }

    public long add(int a, long b){
        return a + b;
    }
    public int add(int a, int b,int c) {
        return a + b + c;
    }

    public long add(long a, long b) {
        return a + b;
    }

    public String add(String a, String b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }


}
