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

    public double serveOrder(Order order, Drink drink) {
        order.completeOrder(drink);

        String wanted = order.getDrinkName();
        String got = drink.getName();
        Customer cust = order.getCustomer();

        System.out.println("Serving " + got + " to " + cust.getName() + "...");

        if(wanted.equalsIgnoreCase(got)) {
            System.out.println("// " + cust.getHappyReaction());
        } else {
            System.out.println("// " + cust.getSadReaction());
            order.setPrice(0.0);
        }

        int tip = cust.reactToDrink(drink, wanted);
            if(tip > 0) {
                System.out.println("Received Tip: Php" + tip);
                totalTips += tip;
                return tip;
            } else {
                System.out.println("No tip received");
                return 0;
        }
    }

    public double getTotalTips() { return  totalTips; }
    public String getName() { return name; }

}
