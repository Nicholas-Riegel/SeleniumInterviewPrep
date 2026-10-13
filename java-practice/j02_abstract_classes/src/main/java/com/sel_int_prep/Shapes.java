package com.sel_int_prep;

public abstract class Shapes {

    protected String name;

    public Shapes(String name){
        this.name = name;
    }

    // this still has to be implemented like interface
    public abstract double area();

    public String describe(){
        return name + " with area " + String.format("%.2f", area());
    }
}