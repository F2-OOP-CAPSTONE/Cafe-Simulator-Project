package entities.types;

import entities.Customer;
import drinks.Drink;
import drinks.DrinkSize;
import drinks.DrinkType;
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
        this.maxPatience = 15;
        this.patience = maxPatience;

        int roll = random.nextInt(100);
        if (roll < 33) this.preferredSize = DrinkSize.SMALL;
        else if (roll < 66) this.preferredSize = DrinkSize.MEDIUM;
        else this.preferredSize = DrinkSize.LARGE;

        this.dialogue = QUOTES[random.nextInt(QUOTES.length)];
    }

    public  int reactToDrink(Drink drink, String wanted) {
        if (drink.getName().equalsIgnoreCase(wanted)) {
            if (random.nextBoolean()) return 0;
            return 2;
        }
        return 0;
    }

    public Drink orderDrink(){
        int index = random.nextInt(100);
        DrinkType type;
        if(index < 50){
            type = DrinkType.LATTE;
        } else if (index < 75){
            type = DrinkType.CAPPUCCINO;
        } else if (index < 90){
            type = DrinkType.AMERICANO;
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
