package CoffeeShop;

import drinks.*;
import mechanics.MixingGlass;
import entities.Barista;
import entities.Customer;
import entities.CustomerGeneration;
import java.util.*;

public class CoffeeShop {
    public final String name;

    // Core Systems
    private Barista barista;
    private LinkedList<Order> orders;
    private SalesReport salesReport;
    private MixingGlass mixingGlass;
    private int nextOrderId = 1;
    private ArrayList<String> menu = new ArrayList<>(Arrays.asList("Latte", "Americano", "Cappuccino", "Mocha", "Coffee of all Sadness and Grief"));
    private ArrayList<String> INGS = new ArrayList<>(Arrays.asList("COFFEE", "MILK", "WATER", "SUGAR", "CHOCOLATE", "SYRUP", "CARAMEL"));
    private HashMap<Ingredients, Integer> Inventory;

    // Economy and Inventory
    private double currentBalance = 100.00;
    private double dailyTax = 10.00;
    private final double TAX_INCREASE = 5.00;

    // New Variables: Day Cycle
    private int currentDay = 1;
    private int customerServedToday = 0;
    private final int CUSTOMER_PER_DAY = 5;

    public CoffeeShop(String name) {
        this.name = name;
        this.orders = new LinkedList<>();
        this.salesReport = new SalesReport();

        this.barista = new Barista(setBarista());
        this.mixingGlass = new MixingGlass();

        this.Inventory = new HashMap<>();
        for (Ingredients ing : Ingredients.values()) {
            Inventory.put(ing, 20);
        }
    }

    // -- GAME LOOP --
    public Order spawnCustomer() {
        Customer customer = CustomerGeneration.spawnRandomCustomer();
        Order newOrder = new Order(nextOrderId++, customer);
        manageOrder("Enqueue", newOrder);
        return newOrder;
    }

    public boolean addIngredient(Ingredients ing) {
        int currentStock = Inventory.getOrDefault(ing, 0);

        if (currentStock > 0) {
            Inventory.put(ing, currentStock - 1);

            mixingGlass.addIngredient(ing);
            System.out.println("Added " + ing + ". Stock remaining: " + (currentStock - 1));
            return true;
        } else {
            System.out.println("!!! OUT OF STOCK: " + ing + " !!!");
            return false;
        }
    }

    public void resetMixingGlass() {
        this.mixingGlass = new MixingGlass();
        System.out.println("mixing Glass Emptied (Ingredients Wasted)");
    }

    // Convenience overload for existing callers
    public Order serveDrink() {
        return serveDrink(DrinkSize.MEDIUM);
    }

    public Order serveDrink(DrinkSize size) {
        if (orders.isEmpty()) { return null; }

        Order currentOrder = orders.getFirst();

        Drink finalDrink = mixingGlass.finishDrink(size);
        barista.serveOrder(currentOrder, finalDrink);

        String receiptTxt = Receipt.printReceipt(currentOrder);
        System.out.println(receiptTxt);

        if (currentOrder.getStatus().equals("Completed")) {
            double price = currentOrder.getPrice();

            if (price > 0) {
                currentBalance += price;
                salesReport.addSale(price);
            }

            salesReport.incrementCustomerServedCount();
            customerServedToday++;

            manageOrder("Dequeue", currentOrder);
        }
        return currentOrder;
    }

    public String restockInventory() {
        double cost = 5.00;
        int amount = 10;

        if (currentBalance >= cost) {
            currentBalance -= cost;
            for (Map.Entry<Ingredients, Integer> entry : Inventory.entrySet()) {
                entry.setValue(entry.getValue() + amount);
            }
            return "Restocked all items. Paid $" + String.format("%.2f", cost);
        } else {
            return "Insufficient funds! Need $" + String.format("%.2f", cost);
        }
    }

    // -------------- New Day Cycle Method ------------------
    public boolean isDayFinished() {
        return customerServedToday >= CUSTOMER_PER_DAY;
    }

    public void startNextDay() {
        System.out.println("\n>>> END OF DAY " + currentDay);
        System.out.println(("Tax Man collects: $" + dailyTax));

        currentBalance -= dailyTax;
        dailyTax += TAX_INCREASE;

        currentDay++;
        customerServedToday = 0;
        System.out.println(">>Starting Day " + currentDay);
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public String getDaySummary() {
        String status = (currentBalance < 0) ? "BANKRUPT" : "SOLVENT";
        return "<html><center><h2>Day " + currentDay + " Complete!</h2>" +
                "Customers Served Today: " + customerServedToday + "<br>" +
                "Tax Paid: $" + String.format("%.2f", (dailyTax)) + "<br>" +
                "Total Revenue: $" + String.format("%.2f", salesReport.getTotalSales()) + "<br>" +
                "Barista Tips: $" + String.format("%.2f", barista.getTotalTips()) + "<br>" +
                "Current Balance: $" + String.format("%.2f", currentBalance) + "<br>" +
                "Financial Status: <b>" + status + "</b></center></html>";
    }

    // --------------------------------------------------------

    public String getInventoryString(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Ingredients, Integer> entry : Inventory.entrySet()) {
            sb.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

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

    public boolean isBankrupt() {
        return currentBalance < 0;
    }


    public Barista getBarista() {return barista;}
    public LinkedList<Order> getOrders() {return orders;}
    public HashMap<Ingredients, Integer> getInventory() { return Inventory; }
    public double getCurrentBalance() { return currentBalance; }

    private String setBarista(){
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

        return randomName;
    }
}
