package main.java.drinks;

import java.util.ArrayList;

public enum Ingredients {
    COFFEE("Coffee"), MILK("Milk"), WATER("Water"),
    SUGAR("Sugar"), CHOCOLATE("Chocolate"), SYRUP("Syrup"),
    CARAMEL("Caramel");

    private final String name;
    Ingredients(String name) { this.name = name; }

    public String getName() { return name; }
}

