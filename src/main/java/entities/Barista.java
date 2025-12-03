package entities;

import CoffeeShop.Order;
import drinks.Drink;

public class Barista {
    private String name;
    private double totalTips;

    public Barista(String name) {
        this.name = name;
        this.totalTips = 0;
    }

    public void serveOrder(Order order, Drink drink) {
        order.completeOrder(drink);
        // Check if customer is happy
        String wanted = order.getDrinkName();
        String got = drink.getName();
        int tip  = 0;

        if(wanted.equalsIgnoreCase(got)) {
            tip = order.getCustomer().reactToDrink(drink);
        }

        if (tip > 0) {
            totalTips += tip;
        } else if (tip < 0) {
//            rep lost
        } else {
//            massive rep lost
        }
    }

    public double getTotalTips() { return totalTips; }
}