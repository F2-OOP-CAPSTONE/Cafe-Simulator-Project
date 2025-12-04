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
        Customer cust = order.getCustomer();

        System.out.println("Serving " + got + " to " + cust.getName() + "...");

        if(wanted.equalsIgnoreCase(got)) {
            System.out.println("// " + cust.getHappyReaction());
        } else {
            System.out.println("// " + cust.getSadReaction());
        }

        int tip = order.getCustomer().reactToDrink(drink, wanted);

        if (tip > 0) {
            System.out.println("Received Tip: Php" + tip);
            totalTips += tip;
//        } else if (tip < 0) {
//            rep lost
        } else {
            System.out.println("No tip received");
//            massive rep lost
        }
    }

    public double getTotalTips() { return totalTips; }
}