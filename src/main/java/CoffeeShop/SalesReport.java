package main.java.CoffeeShop;

import java.util.ArrayList;

public class SalesReport {
    private CoffeeShop coffeeShop;
    private double totalSales;
    private ArrayList<Integer> drinkCount;

    public SalesReport(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
        totalSales = 0;
    }
}
