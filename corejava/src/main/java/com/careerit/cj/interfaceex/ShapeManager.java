package com.careerit.cj.interfaceex;

public class ShapeManager {

    public static void main(String[] args) {

        Shape shape = new Circle(10);
        System.out.println(shape.calculateArea());
        System.out.println(shape.calculatePerimeter());
    }
}
