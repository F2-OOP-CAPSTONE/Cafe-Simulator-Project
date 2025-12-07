import CoffeeShop.*;
import drinks.*;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        CoffeeShop myCafe = new CoffeeShop("Java Jolt");

        System.out.println("Welcome to Cafe Simulator!");
        System.out.println("------------------------------");

        boolean gameRunning = true;
        while (gameRunning) {
            int numberOfCustomers = askForInt("How many customers would you like to serve today? ", 1);
            boolean hasRestocked = false;

            for (int i = 0; i < numberOfCustomers; i++) {
                Order order = myCafe.spawnCustomer();
                System.out.println("\n>>> NEW CUSTOMER: " + order.getCustomer().getName());
                System.out.println("// " + order.getCustomer().getDialogue());
                System.out.println("ORDER: " + order.getDrinkName());
                System.out.println("----------------------------------");

                boolean makingDrink = true;
                while (makingDrink) {
                    printMenu();
                    int choice = askForInt("Select Ingredient: ", 0);

                    switch (choice) {
                        case 1 -> myCafe.getBarista().addIngredient(Ingredients.COFFEE, myCafe.getInventory());
                        case 2 -> myCafe.getBarista().addIngredient(Ingredients.MILK, myCafe.getInventory());
                        case 3 -> myCafe.getBarista().addIngredient(Ingredients.WATER, myCafe.getInventory());
                        case 4 -> myCafe.getBarista().addIngredient(Ingredients.SUGAR, myCafe.getInventory());
                        case 5 -> myCafe.getBarista().addIngredient(Ingredients.CHOCOLATE, myCafe.getInventory());
                        case 6 -> myCafe.getBarista().addIngredient(Ingredients.SYRUP, myCafe.getInventory());
                        case 7 -> myCafe.peekQueue();
                        case 8 -> {
                            myCafe.serveDrink();
                            makingDrink = false;
                        }
                        case 9 -> myCafe.checkInventory();
                        case 67 -> myCafe.getBarista().resetIngredients(myCafe.getInventory());
                        case 0 -> {
                            if(!hasRestocked) hasRestocked = myCafe.restockInventory();
                            else System.out.println("You already have restocked for that day!");
                        }
                        default -> System.out.println("Invalid Choice!");
                    }
                }
            }

            myCafe.printEndOfDayReport();
            System.out.print("\nProceed with another day? (y/n): ");
            String playAgain = sc.nextLine();
            if (playAgain.equalsIgnoreCase("n")) gameRunning = false;
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n[1] Coffee      [2] Milk               [3] Water");
        System.out.println("[4] Sugar       [5] Chocolate          [6] Syrup");
        System.out.println("[7] Queue       [8] SERVE              [9] CHECK INVENTORY");
        System.out.println("[67] Reset Inventory      [0] RESTOCK");
    }

    private static int askForInt(String prompt, int defaultValue) {
        System.out.print(prompt);
        String input = sc.nextLine();
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
