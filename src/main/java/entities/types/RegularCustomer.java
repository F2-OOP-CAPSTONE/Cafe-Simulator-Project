package entities.types;

import entities.Customer;
import drinks.Drink;
import drinks.DrinkSize;
import drinks.DrinkType;
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
        this.maxPatience = 45;
        this.patience = this.maxPatience;

        int roll = random.nextInt(100);
        if (roll < 33) this.preferredSize = DrinkSize.SMALL;
        else if (roll < 66) this.preferredSize = DrinkSize.MEDIUM;
        else this.preferredSize = DrinkSize.LARGE;

        int index = random.nextInt(QUOTES.length);
        this.dialogue = QUOTES[index];
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

    public int reactToDrink(Drink drink, String wanted) {
        if (drink.getName().equalsIgnoreCase(wanted)) {
            return 10;
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
