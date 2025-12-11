package CoffeeShop;

import drinks.Drink;
import drinks.DrinkSize;
import drinks.DrinkType;
import entities.Customer;

public class Order {
    private final int ID;
    private double price;
    private final Customer customer;
    private final String drinkName;
    private Drink servedDrink;
    private String Status;                  // Pending,Brewing,Done?
    private final DrinkSize requestedSize;

    public Order(int ID, Customer customer){
        this.Status = "Pending";
        this.ID = ID;
        this.customer = customer;
        this.drinkName = customer.orderDrink();
        this.requestedSize = customer.getPreferredSize();
    }

    // ACTIONS
    public void completeOrder(Drink drink) {
        this.servedDrink = drink;
        this.Status = "Completed";
    }

    public Drink getOrderedDrink() {
        for (DrinkType type : DrinkType.values()) {
            if (type.getName().equalsIgnoreCase(drinkName)) {
                return new Drink(type, requestedSize);
            }
        }
        return  null;
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


}
