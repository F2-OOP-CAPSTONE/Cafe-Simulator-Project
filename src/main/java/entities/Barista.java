package main.java.entities;

import main.java.CoffeeShop.*;
import main.java.drinks.*;
import main.java.mechanics.MixingGlass;

import java.util.*;

public class Barista {
    private String name;
    private MixingGlass mixingGlass;
    private double totalTips;

    public Barista(String name) {
        this.name = name;
        this.mixingGlass = new MixingGlass();
        this.totalTips = 0;
    }

    public void serveOrder(Order order) {
        Drink drink = mixingGlass.finishDrink(order.getOrderedDrink().getSize());

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

    public void addIngredient(Ingredients ing, HashMap<String,Integer> Inventory) { // to add inventory manipulation
        for (Map.Entry<String, Integer> entry : Inventory.entrySet()) {
            if(entry.getKey().equals(ing.name())) {
                if(entry.getValue() == 0){
                    System.out.println("Ran out of " + entry.getKey());
                    return;
                }
                entry.setValue(entry.getValue()- 1);
            }
        }

        System.out.println("Added " + ing);
        mixingGlass.addIngredient(ing);
    }

    public double getTotalTips() { return totalTips; }
}