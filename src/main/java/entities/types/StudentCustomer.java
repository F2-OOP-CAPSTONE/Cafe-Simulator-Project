package src.entities.types;

import src.entities.Customer;
import src.drinks.Drink;

public class StudentCustomer extends Customer {
    private static final String[] QUOTES = {
            "Yo.",
            "Hey",
            "Wazza"
    };
    public StudentCustomer(String name) {
        this.name = name + " (Student)";
        this.patience = 90;

        int index = random.nextInt(QUOTES.length);
        this.dialogue = QUOTES[index];
    }

    public String orderDrink(){
        int index = random.nextInt(100);
        if(index < 50){
            return "Americano";
        } else if (index < 75){}
        return ""
    }
}