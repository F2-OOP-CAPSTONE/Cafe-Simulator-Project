package main.java.entities.types;

import main.java.entities.*;
import main.java.drinks.*;

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
//        this.patience = 35;

        this.dialogue = QUOTES[random.nextInt(QUOTES.length)];
    }

    public  int reactToDrink(Drink drink, String wanted) {
        if (drink.getName().equalsIgnoreCase(wanted)) {
            Random random = new Random();
            return random.nextInt(((100 - 50) + 1) + 50);
        } else {
            return 0;
        }
    }

    public String getHappyReaction() {
        return HAPPY_QUOTES[random.nextInt(HAPPY_QUOTES.length)];
    }

    public String getSadReaction() {
        return SAD_QUOTES[random.nextInt(SAD_QUOTES.length)];
    }
}