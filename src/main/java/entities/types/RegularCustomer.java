package entities.types;

import entities.Customer;
import drinks.Drink;

public class RegularCustomer extends Customer {
    private static final String[] QUOTES = {
            "Wow, this breeze is great, right?",
            "Some weather we're having, huh?",
            "Sometimes I have really deep thoughts about life and stuff."
    };
    public RegularCustomer(String name) {
        this.name = name;
//        this.patience = 90;

        int index = random.nextInt(QUOTES.length);
        this.dialogue = QUOTES[index];
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

    public int reactToDrink(Drink drink) { return 0; }
}