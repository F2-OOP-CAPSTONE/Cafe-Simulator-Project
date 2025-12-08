package CoffeeShop;

import java.util.*;

public class SalesReport {
    private double totalSales;
    private int customerServedCount;
    private HashMap<String, Integer> servedDrinkCounts;


    public SalesReport() {
        totalSales = 0;
        customerServedCount = 0;
    }

    public void updateSalesReport(Order o) {
        if(o == null) return;

        this.totalSales += o.getPrice();
    }

    public void addSale(double amount) { this.totalSales += amount; }
    public double getTotalSales() { return totalSales; }
    public void incrementCustomerServedCount() { this.customerServedCount++; }
    public int getCustomerServedCount() { return customerServedCount; }
}
