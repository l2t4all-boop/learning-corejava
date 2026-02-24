package com.careerit.cj.day2;

import java.util.Scanner;

public class SimpleIfExample {

    void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IO.println("Enter the age :");
        int age = sc.nextInt();

        if (age < 0) {
            age = -age;
        }

        System.out.println("Entered age is :" + age);
    }
}
