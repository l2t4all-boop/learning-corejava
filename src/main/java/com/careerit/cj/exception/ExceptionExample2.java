package com.careerit.cj.exception;

import static java.lang.IO.println;

public class ExceptionExample2 {

    public static void main(String[] args) {
            println("Start of main method");
            try{
                int res = 10 / 0;
                println(res);
            }catch (NumberFormatException e){
                e.printStackTrace();
            } finally{
               println("Finally");
            }
            println("End of main method");
    }
}
