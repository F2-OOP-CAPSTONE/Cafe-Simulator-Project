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

    public void addSale(Order o){
        ArrayList<String> menu = coffeeShop.getMenu();
        for(int i = 0; i < menu.size(); i++){
            if(o.getDrink().getType().getName())
        }
    }

}
