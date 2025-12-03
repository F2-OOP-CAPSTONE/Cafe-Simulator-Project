package main.java.CoffeeShop;
import java.util.*;

public class CoffeeShop {
    public final String name;
    private ArrayList<String> menu;
    private ArrayList<Order> orders;

    public CoffeeShop(String name, ArrayList<String> menu, ArrayList<Order> orders) {
        this.name = name;
        this.menu = menu;
        this.orders = orders;
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
