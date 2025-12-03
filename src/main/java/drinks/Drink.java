package drinks;

public class Drink {
    private final DrinkType type;
    private final DrinkSize size;
//    private int quality;             maybe add quality for increase fame? quality increase when high ingredients used?

    public Drink(DrinkType type, DrinkSize size){
        this.type = type;
        this.size = size;
    }

    public double getPrice() {
        double basePrice = type.getPrice();
        double multiplier = size.getPriceMultiplier();
        return basePrice * multiplier;
    }

    public int getCalorie() {
        double baseCalorie = type.getCalories();
        double multiplier = size.getCalorieMultiplier();
        return (int) (baseCalorie * multiplier);
    }


}