package CoffeeShop;

import drinks.Drink;
import entities.Customer;

public class Order {
    private final int ID;
    private final Customer customer;
    private final String drinkName;
    private Drink servedDrink;
    private String Status;                  // Pending,Brewing,Done?

    public Order(int ID, Customer customer){
        this.Status = "Pending";
        this.ID = ID;
        this.customer = customer;
        this.drinkName = customer.orderDrink();
    }

    // ACTIONS
    public void completeOrder(Drink drink) {
        this.servedDrink = drink;
        this.Status = "Complete";
    }

    public int getID() {
        return ID;
    }
    public String getStatus() {
        return Status;
    }
    public Customer getCustomer() { return customer; }
    public String getDrinkName() { return drinkName; }
    public Drink getServedDrink()  { return servedDrink; }
    p
}
