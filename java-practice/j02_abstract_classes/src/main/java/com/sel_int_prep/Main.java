package com.sel_int_prep;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        Circle circle = new Circle(2);
        Rectangle rect = new Rectangle(5, 3);
        List<Shapes> shapeList = new ArrayList<>();
        shapeList.add(rect);
        shapeList.add(circle);

        for (Shapes shape : shapeList){
            System.out.println(shape.describe());
        }
    }
}