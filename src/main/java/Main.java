package main.java;

import main java.CoffeeShop.CoffeeShop;

public class Main {
    public static void main(String[] args) {
        CoffeeShop myCafe = new CoffeeShop("Java Jolt");

        myCafe.startNextTurn();
        myCafe.startNextTurn();
        myCafe.startNextTurn();

        myCafe.printEndOfDayReport();
    }
}