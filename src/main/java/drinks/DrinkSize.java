package drinks;

public enum DrinkSize {
    SMALL("Small", 0.80, 0.75),
    MEDIUM("Medium", 1, 1),
    LARGE("Large", 1.50, 1.50);

    private final String label;
    private final double priceMultiplier;
    private final double calorieMultiplier;

    DrinkSize(String label, double priceMultiplier, double calorieMultiplier){
        this.label = label;
        this.priceMultiplier = priceMultiplier;
        this.calorieMultiplier = calorieMultiplier;
    }

    public double getPriceMultiplier() { return priceMultiplier; }
    public double getCalorieMultiplier() { return  calorieMultiplier; }
    public String toString() { return label; }
}