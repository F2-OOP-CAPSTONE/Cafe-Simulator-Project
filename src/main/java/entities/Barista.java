package entities;

import CoffeeShop.*;
import drinks.*;
import entities.types.*;
import mechanics.*;

import java.util.*;

public class Barista {
    private String name;
    private MixingGlass mixingGlass;
    private double totalTips;
    private HashMap<String,Integer> currentOrderIngredients;

    public Barista(String name) {
        this.name = name;
        this.mixingGlass = new MixingGlass();
        this.totalTips = 0;
        // Initialise an empty ingredient tracker so reset calls are safe even before any addIngredient calls.
        setCOI(null);
    }

    public HashMap<String, Integer> serveOrder(Order order) {
        if (currentOrderIngredients == null) {
            setCOI(new HashMap<>());
        }

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
            order.setPrice(0.0);
//            if(order.getCustomer() instanceof KarenCustomer) order.setPrice(0);
//            else if(order.getCustomer() instanceof RichCustomer) order.setPrice(order.getPrice() * 0.75);
//            else order.setPrice(order.getPrice() / 2);
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

        HashMap<String,Integer> toCheck = currentOrderIngredients;
        currentOrderIngredients = null;

        return toCheck;
    }

    public boolean addIngredient(Ingredients ing, HashMap<String,Integer> Inventory) { // to add inventory manipulation
        if (Inventory == null) {
            return false;
        }
        if(currentOrderIngredients == null) setCOI(Inventory);

        Integer currentStock = Inventory.get(ing.name());
        if (currentStock == null) {
            return false;
        }
        if (currentStock == 0) {
            System.out.println("Ran out of " + ing.name());
            return false;
        }

        Inventory.put(ing.name(), currentStock - 1);
        currentOrderIngredients.put(ing.name(), currentOrderIngredients.get(ing.name()) + 1);

        System.out.println("Added " + ing);
        mixingGlass.addIngredient(ing);
        return true;
    }

    public void resetIngredients(HashMap<String,Integer> Inventory){
        if (currentOrderIngredients == null || Inventory == null) {
            return;
        }
        for (Map.Entry<String, Integer> entry1 : Inventory.entrySet()) {
            String key = entry1.getKey();
            int returnVal = currentOrderIngredients.get(key);
            entry1.setValue(entry1.getValue() + returnVal);
            currentOrderIngredients.put(key,0);
        }
    }

    public String checkInventory(HashMap<String, Integer> inventory) {
        if (inventory == null || inventory.isEmpty()) {
            return "No inventory data available.";
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return builder.toString().trim();
    }

    public String restockInventory(HashMap<String, Integer> inventory) {
        if (inventory == null || inventory.isEmpty()) {
            return "No inventory to restock.";
        }

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            entry.setValue(50);
        }
        return "Inventory restocked.";
    }

    public double getTotalTips() { return totalTips; }

    private void setCOI(HashMap<String,Integer> Inv){
        currentOrderIngredients = new HashMap<>();
        if (Inv != null && !Inv.isEmpty()) {
            for (Map.Entry<String, Integer> entry : Inv.entrySet()) {
                currentOrderIngredients.put(entry.getKey(), 0);
            }
        } else {
            for (Ingredients ing : Ingredients.values()) {
                currentOrderIngredients.put(ing.name(), 0);
            }
        }
    }
}
