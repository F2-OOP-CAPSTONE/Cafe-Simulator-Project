package entities.types;

import entities.Customer;
import drinks.Drink;
import drinks.DrinkSize;
import drinks.DrinkType;
import java.util.Random;

public class RichCustomer extends Customer {
    private static final String[] QUOTES = {
            "This place seems adequate for my interview later.",
            "I'm finding this decor dreadfully inefficient.",
            "Good heavens, does nobody in this city work? It is far too crowded in here for a suit of this quality."
    };
    private static final String[] HAPPY_QUOTES = {
            "Acceptable. Keep the change.",
            "Finally. Some decent service",
            "Exquisite. I might become a regular here."
    };
    private static final String[] SAD_QUOTES = {
            "Do you call this swill a drink?",
            "I wouldn't even feed this to my dog.",
            "I wouldn't even keep you as a slave in my empire."
    };


    public RichCustomer(String name) {
        this.name = name + " (VIP)";
        this.maxPatience = 30;
        this.patience = maxPatience;

        int roll = random.nextInt(100);
        if (roll < 80) this.preferredSize = DrinkSize.LARGE;
        else this.preferredSize = DrinkSize.MEDIUM;

        this.dialogue = QUOTES[random.nextInt(QUOTES.length)];
    }

    public  int reactToDrink(Drink drink, String wanted) {
        if (drink.getName().equalsIgnoreCase(wanted)) {
            return 50;
        } else {
            return 0;
        }
    }

    public Drink orderDrink() {
        int index = random.nextInt(100);
        DrinkType type;
        if(index < 50){
            type = DrinkType.CAPPUCCINO;
        } else if (index < 75){
            type = DrinkType.AMERICANO;
        } else if (index < 90){
            type = DrinkType.LATTE;
        } else {
            type = DrinkType.MOCHA;
        }
        return new Drink(type, preferredSize);
    }

    public String getHappyReaction() {
        return HAPPY_QUOTES[random.nextInt(HAPPY_QUOTES.length)];
    }

    public String getSadReaction() {
        return SAD_QUOTES[random.nextInt(SAD_QUOTES.length)];
    }
}
