package main.java.CoffeeShop;

import main.java.drinks.*;
import java.util.Random;

public class Order {
    private final int ID;
    private String Status;                  // Pending,Brewing,Done?
    private String Rating;                  // Customer Only Function
    private final Drink drink;

    public Order(int ID, Drink drink){
        this.Status = "Pending";
        this.ID = ID;
        this.drink = drink;
    }

    public int getID() {
        return ID;
    }

    public String getStatus() {
        return Status;
    }

    public String getRating() {
        return Rating;
    }

    public Drink getDrink() {
        return drink;
    }

    public void updateStatus(String status) {
        Status = status;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

}
