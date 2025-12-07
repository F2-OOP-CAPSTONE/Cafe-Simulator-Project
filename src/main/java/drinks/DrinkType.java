package main.java.drinks;

import java.util.ArrayList;
import java.util.Arrays;

public enum DrinkType {
    LATTE("Latte", 150.00, 135),
    AMERICANO("Americano", 135.00, 10),
    CAPPUCCINO("Cappuccino", 145.00, 150),
    MOCHA("Mocha", 190.00, 349),
    COFFEEofSADNESSandGRIEF("Coffee of all Sadness and Grief", 0, 10000000);

    private final String name;
    private final double price;
    private final int calories;

    DrinkType(String name, double price, int calories){
        this.name = name;
        this.price = price;
        this.calories = calories;
    }



    public String getName() { return name; }
    public double getPrice() { return  price; }
    public  double getCalories() { return  calories;}
}