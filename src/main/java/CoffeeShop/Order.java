package CoffeeShop;

import drinks.Drink;
import drinks.DrinkSize;
import entities.Customer;

public class Order {
    private final int ID;
    private double price;
    private final Customer customer;
    private final Drink orderedDrink;
    private final String drinkName;
    private Drink servedDrink;
    private String Status;                  // Pending,Brewing,Done?
    private final DrinkSize requestedSize;
    private double tip;

    public Order(int ID, Customer customer){
        this.Status = "Pending";
        this.ID = ID;
        this.customer = customer;
        this.orderedDrink = customer.orderDrink();
        this.drinkName = orderedDrink.getName();
        this.requestedSize = orderedDrink.getSize();
    }

    // ACTIONS
    public void completeOrder(Drink drink) {
        this.servedDrink = drink;
        this.Status = "Completed";
    }

    public Drink getOrderedDrink() {
        return orderedDrink;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getID() {
        return ID;
    }
    public double getPrice() { return price;}
    public String getStatus() { return Status; }
    public Customer getCustomer() { return customer; }
    public String getDrinkName() { return drinkName; }
    public Drink getServedDrink()  { return servedDrink; }
    public DrinkSize getRequestedSize() { return requestedSize; }
    public double getTip() { return tip; }
    public double getTotalCost() { return price + tip; }

    public void setTip(double tip) { this.tip = tip; }

}
