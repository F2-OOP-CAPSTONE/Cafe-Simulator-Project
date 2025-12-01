package entities;

import src.drinks.Drink;

public abstract class Customer {
    protected String name;
    protected String dialogue;
    protected int patience;


    public abstract String orderDrink();
    public abstract String reactToDrink();

    public String  getName() { return name; }
    public String getDialogue() { return dialogue; }
}