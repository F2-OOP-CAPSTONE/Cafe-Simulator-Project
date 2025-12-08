package CoffeeShop;

import entities.*;
import drinks.*;
import mechanics.*;

import java.util.*;

public class CoffeeShop {
    public final String name;
    private Barista barista;
    private LinkedList<Order> orders;
    private SalesReport salesReport;
    private ArrayList<String> menu = new ArrayList<>(Arrays.asList("Latte", "Americano", "Cappuccino", "Mocha", "Coffee of all Sadness and Grief"));
    private ArrayList<String> INGS = new ArrayList<>(Arrays.asList("COFFEE", "MILK", "WATER", "SUGAR", "CHOCOLATE", "SYRUP", "CARAMEL"));
    private HashMap<String, Integer> Inventory;

    public CoffeeShop(String name) {
        this.name = name;
        this.orders = new LinkedList<>();
        this.salesReport = new SalesReport();
        setBarista();
        setInventory(INGS);
    }
    // -- GAME LOOP --

    public Order spawnCustomer() {
        Customer customer = CustomerGeneration.spawnRandomCustomer();
        Order newOrder = new Order(orders.size() + 1, customer);
        manageOrder("Enqueue", newOrder);
        return newOrder;
    }

    public void serveDrink() {
        if (orders.isEmpty()) {
            System.out.println("No orders to serve.");
            return;
        }

        Order currentOrder = orders.getFirst();

        HashMap<String, Integer> res = barista.serveOrder(currentOrder);
        evaluateServedDrink(currentOrder, res, currentOrder.getOrderedDrink().getType().getRecipe());

        System.out.printf("Price: %.2f", currentOrder.getPrice());
        String receiptTxt = Receipt.printReceipt(currentOrder);
        System.out.println(receiptTxt);

        if (currentOrder.getStatus().equals("Completed")) {
            salesReport.addSale(currentOrder.getPrice());
            manageOrder("Dequeue", null);
        }
    }

    public void checkInventory(){
        for (Map.Entry<String, Integer> entry : Inventory.entrySet()) {
            String DrinkName = entry.getKey();
            Integer Amount = entry.getValue();
            System.out.println(DrinkName + ": " + Amount);
        }
    }

    public boolean restockInventory() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> s = new ArrayList<>(Arrays.asList("first", "second", "third"));
        ArrayList<Ingredients> e = new ArrayList<>();
        ArrayList<Integer> n = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            System.out.println("\nEnter " + s.get(i) + " ingredient to restock");
            System.out.println("[1] Coffee      [2] Milk               [3] Water");
            System.out.println("[4] Sugar       [5] Chocolate          [6] Syrup");
            int opt = 0;

            while (opt < 1 || opt > 6) {
                System.out.print("Select Ingredient: ");
                opt = sc.nextInt();
                switch (opt) {
                    case 1:
                        e.add(Ingredients.COFFEE);
                        break;
                    case 2:
                        e.add(Ingredients.MILK);
                        break;
                    case 3:
                        e.add(Ingredients.WATER);
                        break;
                    case 4:
                        e.add(Ingredients.SUGAR);
                        break;
                    case 5:
                        e.add(Ingredients.CHOCOLATE);
                        break;
                    case 6:
                        e.add(Ingredients.SYRUP);
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }

            int amount = 0;
            while (amount < 1 || amount > 10){
                System.out.print("Enter amount: ");
                amount = sc.nextInt();
                if(amount < 1 || amount > 10){
                    System.out.println("Amount must not be less than 1 or greater than 10!");
                    continue;
                }
                n.add(amount);
                sc.nextLine();
            }
        }

        for (Map.Entry<String, Integer> entry : Inventory.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(e.get(0).name())) {
                entry.setValue(entry.getValue() + n.get(0));
            }
            if (entry.getKey().equalsIgnoreCase(e.get(1).name())) {
                entry.setValue(entry.getValue() + n.get(1));
            }
            if (entry.getKey().equalsIgnoreCase(e.get(2).name())) {
                entry.setValue(entry.getValue() + n.get(2));
            }
        }
        return true;
    }




    public void peekQueue(){
        for(Order o : orders){
            System.out.println(o.getCustomer().getName() + " ordered " + o.getDrinkName());
        }
    }



//    public void startNextTurn() {
//        // Spawn customer
//        Customer customer = CustomerGeneration.spawnRandomCustomer();
////        System.out.println("Customer says: \"" + customer.getDialogue() + "\"");
//
//        // Create order
//        Order newOrder = new Order(orders.size() + 1, customer);
//        orders.add(newOrder);
//
//        // Player action (SIMULATION PROTOTYPE)
//        Drink preparedDrink = simulateMakingDrink(newOrder.getDrinkName());
//
//        barista.serveOrder(newOrder, preparedDrink);
//
//        // generate recepit and update salesreport
//        String receiptTxt = Receipt.printReceipt(newOrder);
//        System.out.println(receiptTxt);
//
//        if(newOrder.getStatus().equals("Completed")) {
//            salesReport.addSale(newOrder.getServedDrink().getPrice());
//        }
//    }
//
//    // simulate the mixing process
//    private Drink simulateMakingDrink(String requestedName) {
//        MixingGlass glass = new MixingGlass();
//
//        if(requestedName.equals("Latte")) {
//            glass.addIngredient(Ingredients.COFFEE);
//            glass.addIngredient(Ingredients.MILK);
//        } else if(requestedName.equals("Americano")) {
//            glass.addIngredient(Ingredients.COFFEE);
//            glass.addIngredient(Ingredients.WATER);
//        } else if(requestedName.equals("Cappuccino")) {
//            glass.addIngredient(Ingredients.COFFEE);
//            glass.addIngredient(Ingredients.MILK);
//            glass.addIngredient(Ingredients.WATER);
//        } else if(requestedName.equals("Mocha")) {
//            glass.addIngredient(Ingredients.COFFEE);
//            glass.addIngredient(Ingredients.MILK);
//            glass.addIngredient(Ingredients.SUGAR);
//            glass.addIngredient(Ingredients.CHOCOLATE);
//        } else {
//            glass.addIngredient(Ingredients.WATER);
//            glass.addIngredient(Ingredients.SUGAR);
//        }
//
//        return glass.finishDrink(DrinkSize.MEDIUM);
//    }

    public void manageOrder(String action, Order o){ // Order Queue Action
        if(action == null){
            System.out.println("Invalid Order");
            return;
        }

        if(action.equalsIgnoreCase("DEQUEUE")){
            if(!orders.isEmpty()) {
                orders.removeFirst();
            }
        }
        else if(action.equalsIgnoreCase("ENQUEUE")){
            if(o != null) {
                orders.add(o);
            }
        }
        else System.out.println("Invalid Action");
    }

    public void printEndOfDayReport() {
        System.out.println("\n=== END OF DAY REPORT ===");
        System.out.println("Total Customers Served: " + orders.size());
        System.out.println("Total Revenue: $" + String.format("%.2f", salesReport.getTotalSales()));
        System.out.println("Total Tips:    $" + String.format("%.2f", barista.getTotalTips()));
    }



    public Barista getBarista() {return barista;}
    public ArrayList<String> getMenu() {return menu;}
    public LinkedList<Order> getOrders() {return orders;}
    public HashMap<String, Integer> getInventory() { return Inventory; }

    //
//    public void manageMenu(String item, char action){
//        if(action == 'r'){
//            if(menu.contains(item)) menu.remove(item);
//            else System.out.println("Item not found");
//        }
//        else if(action == 'a'){
//            menu.add(item);
//        }
//        else System.out.println("Invalid Action");
//    }
//

    private void setBarista(){
        String[] NAMES = {
                "Kenji", "Julius", "Kharl", "Patrick",
                "Isaiah", "Alexis", "Kylle", "Earl",
                "Francis", "Jay", "Vince", "Jonel",
                "Adrian", "Kyle", "Renz", "Jairo", "Liam",
                "Rafael", "Justine", "Noel", "Aldrin", "Mark",
                "Jared", "Nathan", "Dylan", "Cedrick", "Harvey",
                "Jasper", "Sean", "Rei", "Louis", "Andre",
                "Gabriel", "Miguel", "Leo", "Zach", "Ronnie",
                "Jomar", "Aljon", "Bryle", "Jeric", "Reiven",
                "Rommel", "Arvin", "Benjo", "Jerson", "Lorenz",
                "Kennard", "Jethro", "Aldous", "Raffy", "Tonio",
                "Migs", "Joemar", "Eljon", "Gelo", "Jundi",
        };

        Random random = new Random();
        String randomName = NAMES[random.nextInt(NAMES.length)];

        this.barista = new Barista(randomName);
    }

    private void setInventory(ArrayList<String> INGS){
        Inventory = new HashMap<>();
        for(String s : INGS){
            Inventory.put(s, 50);
        }
    }

    private void evaluateServedDrink(Order o, HashMap<String, Integer> ServedDrink, HashMap<String, Integer> OrderedDrink){
        if (ServedDrink == null || OrderedDrink == null) {
            return;
        }

        for (Map.Entry<String, Integer> entry1 : ServedDrink.entrySet()) {
            String key = entry1.getKey();
            int value1 = entry1.getValue();
            int value2 = OrderedDrink.getOrDefault(key.toUpperCase(), OrderedDrink.getOrDefault(key, 0));
            if(value1 != value2)  o.setPrice(o.getPrice() - 1.5);
        }
    }



}
