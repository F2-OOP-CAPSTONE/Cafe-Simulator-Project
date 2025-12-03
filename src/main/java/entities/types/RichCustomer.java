package entities.types;

import entities.Customer;
import drinks.Drink;

public class RichCustomer extends Customer {
    private static final String[] QUOTES = {
            "This place seems adequate for my interview later.",
            "I'm finding this decor dreadfully inefficient.",
            "Good heavens, does nobody in this city work? It is far too crowded in here for a suit of this quality."
    };

    public RichCustomer(String name) {
        this.name = name + " (VIP)";
//        this.patience = 35;

        this.dialogue = QUOTES[random.nextInt(QUOTES.length)];
    }

    public String orderDrink(){
        int index = random.nextInt(100);
        if(index < 50){
            return "Cappuccino";
        } else if (index < 75){
            return "Americano";
        } else if (index < 90){
            return "Latte";
        } else {
            return "Mocha";
        }
    }

    public  int reactToDrink(Drink drink) { return  0;}
}