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
    private int fame = 0;

    // New Variables: Day Cycle
    private int currentDay = 1;
    private int customerServedToday = 0;
    private int CUSTOMER_PER_DAY = 5;

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

    public boolean performAction(int patienceCost) {
        if (!orders.isEmpty()) {
            Order current = orders.getFirst();
            Customer c = current.getCustomer();

            c.reducePatience(patienceCost);

            if (c.isPatienceZero()) {
                System.out.println(">>> CUSTOMER LEFT ANGRY! (Patience ran out");
                fame -= 10;
                manageOrder("Deqeue", current);
                return false;
            }
        }
        return true;
    }

    // -- GAME LOOP --
    public Order spawnCustomer() {
        Customer customer = CustomerGeneration.spawnRandomCustomer();
        Order newOrder = new Order(nextOrderId++, customer);
        manageOrder("Enqueue", newOrder);
        return newOrder;
    }

    public boolean addIngredient(Ingredients ing) {
        if (!performAction(2)) return false;

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

        Ingredients requiredCup = getCupEnum(size);
        if(Inventory.getOrDefault(requiredCup, 0) <= 0){
            System.out.println("NO CUPS! Order Failed.");
            fame -= 20;
            manageOrder("Dequeue", currentOrder);
            return null;
        }
        Inventory.put(requiredCup, Inventory.getOrDefault(requiredCup, -1));

        Drink finalDrink = mixingGlass.finishDrink(size);

        currentOrder.setPrice(finalDrink.getPrice());

        double tipAmount = barista.serveOrder(currentOrder, finalDrink);

        boolean correctDrink = currentOrder.getDrinkName().equals(finalDrink.getName());
        boolean correctSize = currentOrder.getRequestedSize() == size;

        if (correctDrink && correctSize) {
            fame += 5;
        } else {
            fame -= 10;
        }

        double totalTip = tipAmount;
        if (currentOrder.getPrice() > 0) {
            double patienceBonus = currentOrder.getCustomer().getPatience() / 5;
            totalTip += patienceBonus;
        }

        currentOrder.setTip(totalTip);

        String receiptTxt = Receipt.printReceipt(currentOrder);
        System.out.println(receiptTxt);

        if (currentOrder.getStatus().equals("Completed")) {
            double price = currentOrder.getPrice();

            if (price > 0) {
                currentBalance += price;
                currentBalance += tipAmount;
                salesReport.addSale(price);
            }

            salesReport.incrementCustomerServedCount();
            customerServedToday++;

            manageOrder("Dequeue", currentOrder);
        }
        return currentOrder;
    }

    private Ingredients getCupEnum(DrinkSize size) {
        switch (size) {
            case SMALL: return Ingredients.CUP_SMALL;
            case MEDIUM: return Ingredients.CUP_MEDIUM;
            case LARGE: return Ingredients.CUP_LARGE;
            default: return Ingredients.CUP_MEDIUM;
        }
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

        int newLimit = 4 + (fame / 10);
        this.CUSTOMER_PER_DAY = Math.min(newLimit, 15);

        if (this.CUSTOMER_PER_DAY < 1) {
            this.CUSTOMER_PER_DAY = 1;
        }

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
    public int getFame() { return fame; }
    public int getCustomersServedToday() { return customerServedToday; }
    public int getCustomersPerDay() { return CUSTOMER_PER_DAY; }
    public int getCurrentDay() { return currentDay; }
    public double getDailyTax() { return dailyTax; }
    public int getFame() { return fame; }


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
