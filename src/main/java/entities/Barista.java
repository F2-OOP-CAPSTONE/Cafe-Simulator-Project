package entities;

import CoffeeShop.Order;
import drinks.Drink;
import drinks.Ingredients;
import mechanics.MixingGlass;

import java.util.HashMap;
import java.util.Map;

public class Barista {
    private String name;
    private double totalTips;
    private MixingGlass mixingGlass;
    private final Map<Ingredients, Integer> pendingIngredientUsage = new HashMap<>();

    public Barista(String name) {
        this.name = name;
        this.totalTips = 0;
    }

    public void setMixingGlass(MixingGlass mixingGlass) {
        this.mixingGlass = mixingGlass;
    }

    public boolean addIngredient(Ingredients ing, Map<?, Integer> inventory) {
        if (inventory == null) {
            return false;
        }
        if (inventory.containsKey(ing)) {
            return consumeIngredient(ing, inventory, ing);
        }
        if (inventory.containsKey(ing.name())) {
            return consumeIngredient(ing, inventory, ing.name());
        }
        return false;
    }

    public void resetIngredients(Map<?, Integer> inventory) {
        if (inventory == null) {
            return;
        }
        for (Map.Entry<Ingredients, Integer> entry : pendingIngredientUsage.entrySet()) {
            Object key = inventory.containsKey(entry.getKey()) ? entry.getKey() : entry.getKey().name();
            @SuppressWarnings("unchecked")
            Map<Object, Integer> typedInventory = (Map<Object, Integer>) inventory;
            int current = typedInventory.getOrDefault(key, 0);
            typedInventory.put(key, current + entry.getValue());
        }
        pendingIngredientUsage.clear();
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

        double tip = cust.reactToDrink(drink, wanted);

        if (order.getPrice() >= 0) {
            int patienceBonus = cust.getPatience() / 5;
            tip += patienceBonus;
        }

        if(tip > 0) {
            System.out.println("Received Tip: Php" + String.format("%.2f", tip));
            totalTips += tip;
            return tip;
        } else {
            System.out.println("No tip received");
            return 0.0;
        }
    }

    private <K> boolean consumeIngredient(Ingredients ing, Map<?, Integer> inventory, K key) {
        @SuppressWarnings("unchecked")
        Map<K, Integer> typedInventory = (Map<K, Integer>) inventory;

        Integer currentStock = typedInventory.get(key);
        if (currentStock == null || currentStock <= 0) {
            return false;
        }

        typedInventory.put(key, currentStock - 1);
        pendingIngredientUsage.put(ing, pendingIngredientUsage.getOrDefault(ing, 0) + 1);

        if (mixingGlass != null) {
            mixingGlass.addIngredient(ing);
        }
        return true;
    }

    public double getTotalTips() { return  totalTips; }
    public String getName() { return name; }

    /**
     * Returns a newline-separated inventory summary for UI display.
     */
    public String checkInventory(HashMap<Ingredients, Integer> inventory) {
        if (inventory == null || inventory.isEmpty()) {
            return "Inventory unavailable.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Current Inventory:\n");
        for (Ingredients ing : Ingredients.values()) {
            int amount = inventory.getOrDefault(ing, 0);
            sb.append(ing.getName()).append(": ").append(amount).append("\n");
        }
        return sb.toString();
    }
}
