package main.java.entities.types;

import main.java.entities.*;
import main.java.drinks.*;

import java.util.Random;

public class RegularCustomer extends Customer {
    private static final String[] QUOTES = {
            "Wow, this breeze is great, right?",
            "Some weather we're having, huh?",
            "Sometimes I have really deep thoughts about life and stuff."
    };
    private static final String[] HAPPY_QUOTES = {
            "Me-wow, is this the latest Felinor fashion?",
            "You ever been to a Canor restaurant? The food's pretty howlright.",
            "So, what's keeping you busy these days?"
    };
    private static final String[] SAD_QUOTES = {
            "Hey hivekin, can I bug you for a moment?",
            "So, how's work?",
            "I think you need some coffee."
    };

    public RegularCustomer(String name) {
        this.name = name;
//        this.patience = 90;

        int index = random.nextInt(QUOTES.length);
        this.dialogue = QUOTES[index];
    }


    public int reactToDrink(Drink drink, String wanted) {
        if (drink.getName().equalsIgnoreCase(wanted)) {
            Random random = new Random();
            return random.nextInt(10);
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