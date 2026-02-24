package com.careerit.cj.day2;

import java.util.Scanner;


// > 10000 => 20
// > 5000  => 10
// Otherwise 5
public class DiscountCalc {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the bill amount :");
        double billAmount = sc.nextDouble();
        double discount = 0;
        double netAmount = billAmount;
        if(billAmount >= 10000){
            discount = billAmount * 0.2;
        }else if(billAmount > 5000){
            discount = billAmount * 0.1;
        }else{
            discount = billAmount * 0.05;
        }
        netAmount = billAmount - discount;
        System.out.println("Bill amount :"+billAmount);
        System.out.println("Discount    :"+discount);
        System.out.println("Net amount  :"+netAmount);
    }
}
