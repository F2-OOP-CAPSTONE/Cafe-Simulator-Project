package CoffeeShop;

import entities.Barista;
import entities.Customer;
import entities.CustomerGeneration;
import mechanics.MixingGlass;
import drinks.*;
import java.util.ArrayList;

public class CoffeeShop {
    public final String name;
//    private ArrayList<String> menu;
    private ArrayList<Order> orders;
    private Barista barista;
    private SalesReport salesReport;

    private MixingGlass mixingGlass;
    private Order currentOrder;

    public CoffeeShop(String name) {
        this.name = name;
        this.barista = new Barista("Player");
        this.orders = new ArrayList<>();
        this.salesReport = new SalesReport();
        this.mixingGlass = new MixingGlass();
    }

    // -- GAME LOOP --

    public Order spawnCustomer() {
        Customer customer = CustomerGeneration.spawnRandomCustomer();

        this.currentOrder = new Order(orders.size() + 1, customer);
        orders.add(currentOrder);

        return currentOrder;
    }

    public void addIngredient(Ingredients ing) {
        System.out.println("Added " + ing);
        mixingGlass.addIngredient(ing);
    }

    public void serveDrink(DrinkSize size){
        if (currentOrder == null) {
            return;
        }

        Drink finalDrink = mixingGlass.finishDrink(size);
        barista.serveOrder(currentOrder, finalDrink);

        String receiptTxt = Receipt.printReceipt(currentOrder);
        System.out.println(receiptTxt);

        if(currentOrder.getStatus().equals("Completed")) {
            salesReport.addSale(currentOrder.getServedDrink().getPrice());
        }

        this.currentOrder = null;
    }

//    public void startNextTurn() {
//        // Spawn customer
//        Customer customer = CustomerGeneration.spawnRandomCustomer();
////        System.out.println("Customer says: \"" + customer.getDialogue() + "\"");
//
//        // Create order
//        Order newOrder = new Order(orders.size() + 1, customer);
//        orders.add(newOrder);
//
//        // Player action (SIMULATION PROTOTYPE)
//        Drink preparedDrink = simulateMakingDrink(newOrder.getDrinkName());
//
//        barista.serveOrder(newOrder, preparedDrink);
//
//        // generate recepit and update salesreport
//        String receiptTxt = Receipt.printReceipt(newOrder);
//        System.out.println(receiptTxt);
//
//        if(newOrder.getStatus().equals("Completed")) {
//            salesReport.addSale(newOrder.getServedDrink().getPrice());
//        }
//    }
//
//    // simulate the mixing process
//    private Drink simulateMakingDrink(String requestedName) {
//        MixingGlass glass = new MixingGlass();
//
//        if(requestedName.equals("Latte")) {
//            glass.addIngredient(Ingredients.COFFEE);
//            glass.addIngredient(Ingredients.MILK);
//        } else if(requestedName.equals("Americano")) {
//            glass.addIngredient(Ingredients.COFFEE);
//            glass.addIngredient(Ingredients.WATER);
//        } else if(requestedName.equals("Cappuccino")) {
//            glass.addIngredient(Ingredients.COFFEE);
//            glass.addIngredient(Ingredients.MILK);
//            glass.addIngredient(Ingredients.WATER);
//        } else if(requestedName.equals("Mocha")) {
//            glass.addIngredient(Ingredients.COFFEE);
//            glass.addIngredient(Ingredients.MILK);
//            glass.addIngredient(Ingredients.SUGAR);
//            glass.addIngredient(Ingredients.CHOCOLATE);
//        } else {
//            glass.addIngredient(Ingredients.WATER);
//            glass.addIngredient(Ingredients.SUGAR);
//        }
//
//        return glass.finishDrink(DrinkSize.MEDIUM);
//    }

    public void printEndOfDayReport() {
        System.out.println("\n=== END OF DAY REPORT ===");
        System.out.println("Total Customers Served: " + orders.size());
        System.out.println("Total Revenue: $" + String.format("%.2f", salesReport.getTotalSales()));
        System.out.println("Total Tips:    $" + String.format("%.2f", barista.getTotalTips()));
    }

//    public ArrayList<String> getMenu() {
//        return menu;
//    }
//
//    public ArrayList<Order> getOrders() {
//        return orders;
//    }
//
//    public void manageMenu(String item, char action){
//        if(action == 'r'){
//            if(menu.contains(item)) menu.remove(item);
//            else System.out.println("Item not found");
//        }
//        else if(action == 'a'){
//            menu.add(item);
//        }
//        else System.out.println("Invalid Action");
//    }
//
//    public void manageOrder(Order o, char action){
//        if(o == null) System.out.println("Invalid Order");
//
//        if(action == 'r'){
//            if(orders.contains(o)) orders.remove(o);
//            else System.out.println("Order not found");
//        }
//        else if(action == 'a'){
//            orders.add(o);
//        }
//        else System.out.println("Invalid Action");
//    }


}
