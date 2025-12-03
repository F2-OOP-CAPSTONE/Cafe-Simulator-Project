package entities.types;

import entities.Customer;
import drinks.Drink;

public class KarenCustomer extends Customer {
    private static final String[] QUOTES = {
            "This place looks dirty.",
            "I'm in a hurry, don't waste my time.",
            "Why is the music so loud in here?"
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

    public  int reactToDrink(Drink drink) { return  0;}
}