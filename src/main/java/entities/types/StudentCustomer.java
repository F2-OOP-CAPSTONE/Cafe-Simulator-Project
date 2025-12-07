package main.java.entities.types;

import main.java.entities.*;
import main.java.drinks.*;
import java.util.Random;

public class StudentCustomer extends Customer {
    private static final String[] QUOTES = {
            "Yo.",
            "Hey.",
            "I need some coffee..."
    };
    private static final String[] HAPPY_QUOTES = {
            "Awesome, thanks!",
            "Thanks dude!",
            "Perfect..."
    };
    private static final String[] SAD_QUOTES = {
            "Uhh, this isn't what I ordered...",
            "I can't afford to buy another one...",
            "Bruh. Really?"
    };

    public StudentCustomer(String name) {
        this.name = name + " (Student)";
//        this.patience = 90;

        int index = random.nextInt(QUOTES.length);
        this.dialogue = QUOTES[index];
    }

    public int reactToDrink(Drink drink, String wanted) {
        if (drink.getName().equalsIgnoreCase(wanted)) {
            Random random = new Random();
            return random.nextInt(5);
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