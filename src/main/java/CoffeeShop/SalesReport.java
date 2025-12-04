package CoffeeShop;

public class SalesReport {
    private double totalSales;

    public SalesReport() {
        this.totalSales = 0.0;
    }

    public void addSale(double amount) { this.totalSales += amount; }
    public double getTotalSales() { return totalSales; }
}
