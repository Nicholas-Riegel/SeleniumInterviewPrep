package com.sel_int_prep;

public class Rectangle implements Shapes {

    private double length;
    private double width;

    public Rectangle(double length, double width){
        this.length = length;
        this.width = width;
    }

    public double area(){
        return length * width;
    }
}