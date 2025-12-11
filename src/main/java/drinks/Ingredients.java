package drinks;

import java.util.ArrayList;

public enum Ingredients {
    COFFEE("Coffee"), MILK("Milk"), WATER("Water"),
    SUGAR("Sugar"), CHOCOLATE("Chocolate"), SYRUP("Syrup"),
    CARAMEL("Caramel"),

    CUP_SMALL("Small Cup"),
    CUP_MEDIUM("Medium Cup"),
    CUP_LARGE("Large Cup");

    private final String name;
    Ingredients(String name) { this.name = name; }

    public String getName() { return name; }
}

