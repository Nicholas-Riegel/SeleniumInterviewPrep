package com.sel_int_prep;

public class Rectangle extends Shapes {

    private double length;
    private double width;

    public Rectangle(double length, double width){
        super("Rectangle");
        this.length = length;
        this.width = width;
    }

    public double area(){
        return length * width;
    }
}