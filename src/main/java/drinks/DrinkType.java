package main.java.drinks;

import java.util.*;

public enum DrinkType {
    LATTE("Latte", 150.00, 135, new HashMap<>(Map.of("Coffee", 3, "Milk", 2,"Water", 0,"Sugar", 0,"Chocolate", 0,"Syrup", 0))),
    AMERICANO("Americano", 135.00, 10, new HashMap<>(Map.of("Coffee", 3, "Milk", 0,"Water", 2,"Sugar", 0,"Chocolate", 0,"Syrup", 0))),
    CAPPUCCINO("Cappuccino", 145.00, 150, new HashMap<>(Map.of("Coffee", 3, "Milk", 3,"Water", 2,"Sugar", 0,"Chocolate", 0,"Syrup", 0))),
    MOCHA("Mocha", 190.00, 349, new HashMap<>(Map.of("Coffee", 3, "Milk", 3,"Water", 0,"Sugar", 3,"Chocolate", 3,"Syrup", 0))),
    COFFEEofSADNESSandGRIEF("Coffee of all Sadness and Grief", 0, 10000000, new HashMap<>(Map.of("Coffee", 0, "Milk", 0,"Water", 0,"Sugar", 0,"Chocolate", 0,"Syrup", 0)));

    private final String name;
    private final double price;
    private final int calories;
    private final HashMap<String, Integer> recipe;

    DrinkType(String name, double price, int calories, HashMap<String, Integer> recipe){
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.recipe = recipe;
    }



    public String getName() { return name; }
    public double getPrice() { return  price; }
    public double getCalories() { return  calories;}
    public HashMap<String,Integer> getRecipe() { return recipe; }
}