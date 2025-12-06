import CoffeeShop.*;
import drinks.*;
import form.TempPrepareDrinkGui;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CoffeeShop myCafe = new CoffeeShop("Java Jolt");

        TempPrepareDrinkGui GUI = new TempPrepareDrinkGui(myCafe);

        javax.swing.SwingUtilities.invokeLater(() -> GUI.setVisible(true));


        boolean gameRunning = true;

        System.out.println("Welcome to Cafe Simulator!");
        System.out.println("------------------------------");

        while (gameRunning) {
            Order order = myCafe.spawnCustomer();
            GUI.SetOrder(order);

            System.out.println("\n>>> NEW CUSTOMER: " + order.getCustomer().getName());
            System.out.println("// " + order.getCustomer().getDialogue());
            System.out.println("ORDER: " + order.getDrinkName());
            System.out.println("----------------------------------");

            boolean makingDrink = true;

            while (makingDrink) {
                System.out.println("\n[1] Coffee        [2] Milk        [3] Water");
                System.out.println("[4] Sugar         [5] Chocolate   [6] Syrup");
                System.out.println("[9] SERVE");
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
                        myCafe.addIngredient(Ingredients.COFFEE);
                        break;
                    case 2:
                        myCafe.addIngredient(Ingredients.MILK);
                        break;
                    case 3:
                        myCafe.addIngredient(Ingredients.WATER);
                        break;
                    case 4:
                        myCafe.addIngredient(Ingredients.SUGAR);
                        break;
                    case 5:
                        myCafe.addIngredient(Ingredients.CHOCOLATE);
                        break;
                    case 6:
                        myCafe.addIngredient(Ingredients.SYRUP);
                        break;
                    case 9:
                        myCafe.serveDrink(DrinkSize.MEDIUM);
                        makingDrink = false;
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }

            System.out.print("\n Next Customer? (y/n): ");
            String playAgain = sc.nextLine();
            if (playAgain.equalsIgnoreCase("n")) {
                gameRunning = false;
            }
        }

        myCafe.printEndOfDayReport();
        sc.close();
    }
}