package main.java.entities;

import main.java.CoffeeShop.*;
import main.java.drinks.*;
import java.util.Random;

public abstract class Customer {
    protected String name;
    protected String dialogue;
    protected int patience;

    protected Random random = new Random();

    public Drink orderDrink(){
            DrinkType type;
            DrinkSize size;

            int forType = random.nextInt(100);
            if(forType < 50){
                type = DrinkType.LATTE;
            } else if (forType < 75){
                type = DrinkType.MOCHA;
            } else if (forType < 90){
                type = DrinkType.AMERICANO;
            } else type = DrinkType.CAPPUCCINO;

            int forSize = random.nextInt(100);

            if(forType > 75){
                size = DrinkSize.LARGE;
            } else if (forType < 75){
                size = DrinkSize.MEDIUM;
            } else size = DrinkSize.SMALL;


            return new Drink(type,size);
    }
    public abstract int reactToDrink(Drink drink, String wanted);

    public abstract String getHappyReaction();
    public abstract String getSadReaction();


    public String getName() { return name; }
    public String getDialogue() { return dialogue; }
}