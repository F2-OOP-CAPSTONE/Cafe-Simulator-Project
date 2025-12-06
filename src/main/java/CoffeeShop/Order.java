package main.java.CoffeeShop;

import main.java.drinks.*;
import main.java.entities.*;

public class Order {
    private final int ID;
    private final Customer customer;
    private final Drink orderedDrink;
    private final String drinkName;
    private Drink servedDrink;
    private String Status;                  // Pending,Brewing,Done?

    public Order(int ID, Customer customer){
        this.Status = "Pending";
        this.ID = ID;
        this.customer = customer;
        this.orderedDrink = customer.orderDrink();
        this.drinkName = orderedDrink.getName();
    }

    // ACTIONS
    public void completeOrder(Drink drink) {
        this.servedDrink = drink;
        this.Status = "Completed";
    }



    public int getID() {
        return ID;
    }
    public String getStatus() {
        return Status;
    }
    public Drink getOrderedDrink() { return orderedDrink; }
    public Customer getCustomer() { return customer; }
    public String getDrinkName() { return drinkName; }
    public Drink getServedDrink()  { return servedDrink; }
}
