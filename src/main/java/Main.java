package main.java;

import main.java.CoffeeShop.*;
import main.java.drinks.*;
import main.java.mechanics.*;
import main.java.entities.*;

import java.sql.SQLOutput;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CoffeeShop myCafe = new CoffeeShop("Java Jolt");

        Random random = new Random();
        boolean gameRunning = true;
        boolean round = true;

        System.out.println("Welcome to Cafe Simulator!");
        System.out.println("------------------------------");

        while (gameRunning) {
            int numberOfCustomers = random.nextInt(1,15);

            for(int i=0;i<numberOfCustomers;i++){
                myCafe.spawnCustomer();
            }

            boolean hasRestocked = false;

            while(!myCafe.getOrders().isEmpty()){
                System.out.println("\n>>> NEW CUSTOMER: " + myCafe.getOrders().getFirst().getCustomer().getName());
                System.out.println("// " + myCafe.getOrders().getFirst().getCustomer().getDialogue());
                System.out.println("ORDER: " + myCafe.getOrders().getFirst().getDrinkName());
                System.out.println("----------------------------------");

                boolean makingDrink = true;

                    while (makingDrink) {
                        System.out.println("\n[1] Coffee      [2] Milk               [3] Water");
                        System.out.println("[4] Sugar       [5] Chocolate          [6] Syrup");
                        System.out.println("[7] Queue       [8] SERVE              [9] CHECK INVENTORY");
                        System.out.println("                [0] RESTOCK");
                        System.out.print("Select Ingredient: ");

                        int choice = 0;
                        try {
                            choice = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            choice = 0;
                        }

                        // THIS SWITCH-CASE WILL BECOME THE BUTTON LISTENER
                        switch (choice) {
                            case 1:
                                myCafe.getBarista().addIngredient(Ingredients.COFFEE, myCafe.getInventory());
                                break;
                            case 2:
                                myCafe.getBarista().addIngredient(Ingredients.MILK, myCafe.getInventory());
                                break;
                            case 3:
                                myCafe.getBarista().addIngredient(Ingredients.WATER, myCafe.getInventory());
                                break;
                            case 4:
                                myCafe.getBarista().addIngredient(Ingredients.SUGAR, myCafe.getInventory());
                                break;
                            case 5:
                                myCafe.getBarista().addIngredient(Ingredients.CHOCOLATE, myCafe.getInventory());
                                break;
                            case 6:
                                myCafe.getBarista().addIngredient(Ingredients.SYRUP, myCafe.getInventory());
                                break;
                            case 7:
                                myCafe.peekQueue();
                                break;
                            case 8:
                                myCafe.serveDrink(DrinkSize.MEDIUM);
                                makingDrink = false;
                                break;
                            case 9:
                                myCafe.checkInventory();
                                break;
                            case 0:
                                if(!hasRestocked) hasRestocked = myCafe.restockInventory();
                                else System.out.println("You already have restocked for that day!");
                                break;
                            default:
                                System.out.println("Invalid Choice!");
                        }
                    }
                }

            myCafe.printEndOfDayReport();

            System.out.print("\n Proceed with another day? (y/n): ");
            String playAgain = sc.nextLine();
            if (playAgain.equalsIgnoreCase("n")) gameRunning = false;

        }

        sc.close();
    }
}