package entities.types;

import entities.Customer;
import drinks.Drink;
import drinks.DrinkSize;
import drinks.DrinkType;
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
        this.maxPatience = 60;
        this.patience = this.maxPatience;

        int roll = random.nextInt(100);
        if (roll < 50) {
            this.preferredSize = DrinkSize.SMALL;
        } else if (roll < 80) {
            this.preferredSize = DrinkSize.MEDIUM;
        } else {
            this.preferredSize = DrinkSize.LARGE;
        }

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

    public Drink orderDrink() {
        int index = random.nextInt(100);
        DrinkType type;
        if(index < 50){
            type = DrinkType.LATTE;
        } else if (index < 75){
            type = DrinkType.MOCHA;
        } else if (index < 90){
            type = DrinkType.AMERICANO;
        } else {
            type = DrinkType.CAPPUCCINO;
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
