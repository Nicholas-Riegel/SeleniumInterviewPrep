package com.sel_int_prep;

public class Circle extends Shapes {

    private double radius;

    public Circle(double radius){
        super("Circle");
        this.radius = radius;
    }

    public double area(){
        return Math.PI * radius * radius;
    }
}