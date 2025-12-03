package entities.types;

import entities.Customer;
import drinks.Drink;

import java.util.Random;

public class KarenCustomer extends Customer {
    private static final String[] QUOTES = {
            "This place looks dirty.",
            "I'm in a hurry, don't waste my time.",
            "Why is the music so loud in here?"
    };
    private static final String[] HAPPY_QUOTES = {
            "Well, about time.",
            "Fine, bit the ice cubes are cut unevenly.",
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

    public String orderDrink(){
        int index = random.nextInt(100);
        if(index < 50){
            return "Latte";
        } else if (index < 75){
            return "Cappuccino";
        } else if (index < 90){
            return "Americano";
        } else {
            return "Mocha";
        }
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