package com.sel_int_prep;

public class Circle implements Shapes {

    private double radius;

    public Circle(double radius){
        this.radius = radius;
    }

    public double area(){
        return Math.PI * radius * radius;
    }
}