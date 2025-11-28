package Drinks;

public class Drink {
    private DrinkType type;
//    private int quality;             maybe add quality for increase fame? quality increase when high ingredients used?

    public Drink(DrinkType type){
        this.type = type;
    }

    public String getName(){ return type.name; }

}
