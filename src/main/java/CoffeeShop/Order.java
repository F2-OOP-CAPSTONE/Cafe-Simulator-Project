package CoffeeShop;

import drinks.*;
import entities.*;

public class Order {
    private final int ID;
    private double price;
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
        this.price = orderedDrink.getPrice();
    }

    // ACTIONS
    public void completeOrder(Drink drink) {
        this.servedDrink = drink;
        this.Status = "Completed";
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getID() {
        return ID;
    }
    public double getPrice() { return price;}
    public String getStatus() {
        return Status;
    }
    public Drink getOrderedDrink() { return orderedDrink; }
    public Customer getCustomer() { return customer; }
    public String getDrinkName() { return drinkName; }
    public Drink getServedDrink()  { return servedDrink; }


}
