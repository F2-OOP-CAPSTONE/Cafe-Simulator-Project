package Drinks;

public class Drink {
    private DrinkType type;
    private DrinkSize size;
//    private int quality;             maybe add quality for increase fame? quality increase when high ingredients used?

    public Drink(DrinkType type, DrinkSize size){
        this.type = type;
        this.size = size;
    }

}
