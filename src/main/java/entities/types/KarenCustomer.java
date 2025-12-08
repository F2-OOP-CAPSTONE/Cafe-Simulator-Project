package entities.types;

import entities.*;
import drinks.*;

import java.util.Random;

public class KarenCustomer extends Customer {
    private static final String[] QUOTES = {
            "This place looks dirty.",
            "I'm in a hurry, don't waste my time.",
            "Why is the music so loud in here?"
    };
    private static final String[] HAPPY_QUOTES = {
            "Well, about time.",
            "Fine, but the ice cubes are cut unevenly.",
            "I suppose this will do."
    };
    private static final String[] SAD_QUOTES = {
            "I WANT TO SPEAK TO YOUR MANAGER!",
            "Unacceptable!",
            "Are you deaf? ThIS IS NOT WHAT I ORDERED!"
    };

    public KarenCustomer(String name) {
        this.name = name;
//        this.patience = 15;

        this.dialogue = QUOTES[random.nextInt(QUOTES.length)];
    }

    public  int reactToDrink(Drink drink, String wanted) {
        if (drink.getName().equalsIgnoreCase(wanted)) {
            Random random = new Random();
            return random.nextInt(2);
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
