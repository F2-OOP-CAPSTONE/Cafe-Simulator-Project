package Drinks;

public enum DrinkType {
    LATTE("Latte"), AMERICANO("Americano");

    final String name;

    DrinkType(String name){
        this.name = name;
    }
}
