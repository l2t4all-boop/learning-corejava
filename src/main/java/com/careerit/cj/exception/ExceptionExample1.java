package com.careerit.cj.exception;

import java.util.Scanner;

public class ExceptionExample1 {

    public static void main(String[] args) {
        
        IO.println("Start of main method");
        showResult();
        IO.println("End of main method");
    }

    private static void showResult() {
        Scanner sc = new Scanner(System.in);
        IO.println("Enter the num1 :");
        int num1 = sc.nextInt();
        IO.println("Enter the num2: ");
        int num2 = sc.nextInt();
        calculate(num1, num2);
    }

    private static void calculate(int num1, int num2) {
        try {
            int res = num1 / num2;
            IO.println("Result is :" + res);
        }catch (ArithmeticException e){
            IO.println(e);
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
