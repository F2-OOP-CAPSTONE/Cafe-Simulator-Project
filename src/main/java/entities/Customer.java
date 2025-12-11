package entities;

import drinks.Drink;
import drinks.DrinkSize;
import java.util.Random;

public abstract class Customer {
    protected String name;
    protected String dialogue;
    protected int patience;
    protected int maxPatience;
    protected DrinkSize preferredSize;

    protected Random random = new Random();

    public abstract Drink orderDrink();
    public abstract int reactToDrink(Drink drink, String wanted);
    public abstract String getHappyReaction();
    public abstract String getSadReaction();

    public void reducePatience(int amount) {
        this.patience -= amount;
        if(this.patience < 0) this.patience = 0;
    }

    public boolean isPatienceZero() {
        return patience <= 0;
    }

    public int getPatience() { return patience; }
    public DrinkSize getPreferredSize() { return preferredSize; }
    public String getName() { return name; }
    public String getDialogue() { return dialogue; }
}
