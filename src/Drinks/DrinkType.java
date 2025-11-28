package Drinks;

public enum DrinkType {
    LATTE("Latte", 150.00, 135.00),
    AMERICANO("Americano", 135.00, 10.00),
    CAPPUCCINO("Cappuccino", 145.00, 150.00),
    MOCHA("Mocha", 190.00, 349.00);

    private final String name;
    private final double price;
    private final double calories;

    DrinkType(String name, Double price, Double calories){
        this.name = name;
        this.price = price;
        this.calories = calories;
    }

    public String getName() { return name; }
    public double getPrice() { return  price; }
    public  double getCalories() { return  calories; }
}
