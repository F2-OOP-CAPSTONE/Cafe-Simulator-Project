package entities;

import src.drinks.Drink;
import java.util.Random;

public abstract class Customer {
    protected String name;
    protected String dialogue;
    protected int patience;

    protected Random random = new Random();

    public abstract String orderDrink();
    public abstract String reactToDrink(Drink drink);

    public String  getName() { return name; }
    public String getDialogue() { return dialogue; }
}