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

    public CoffeeShop(String name) {
        this.name = name;
        this.barista = new Barista("Player");
        this.orders = new ArrayList<>();
        this.salesReport = new SalesReport();
    }

    // -- GAME LOOP --

    public void startNextTurn() {
        // Spawn customer
        Customer customer = CustomerGeneration.spawnRandomCustomer();
//        System.out.println("Customer says: \"" + customer.getDialogue() + "\"");

        // Create order
        Order newOrder = new Order(orders.size() + 1, customer);
        orders.add(newOrder);

        // Player action
    }

    public ArrayList<String> getMenu() {
        return menu;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void manageMenu(String item, char action){
        if(action == 'r'){
            if(menu.contains(item)) menu.remove(item);
            else System.out.println("Item not found");
        }
        else if(action == 'a'){
            menu.add(item);
        }
        else System.out.println("Invalid Action");
    }

    public void manageOrder(Order o, char action){
        if(o == null) System.out.println("Invalid Order");

        if(action == 'r'){
            if(orders.contains(o)) orders.remove(o);
            else System.out.println("Order not found");
        }
        else if(action == 'a'){
            orders.add(o);
        }
        else System.out.println("Invalid Action");
    }


}
