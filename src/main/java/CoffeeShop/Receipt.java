package CoffeeShop;

import drinks.*;

public class Receipt {
    public static String printReceipt(Order order) {
        StringBuilder sb = new StringBuilder();
        Drink drink = order.getOrderedDrink();

        sb.append("========== RECEPIT ==========\n");
        sb.append("Order ID: #").append(order.getID()).append("\n");
        sb.append("Customer: ").append(order.getCustomer().getName()).append("\n");
        sb.append("-----------------------------\n");

        if(drink != null) {
            sb.append("Item:        ").append(drink.getFullName()).append("\n");
            sb.append("Price:       Php").append(String.format("%.2f", order.getPrice())).append("\n");
            sb.append("Calories:    ").append(drink.getCalorie()).append(" kcal\n");
        } else {
            sb.append("Item:        [Order Failed]\n");
            sb.append("Price:       Php0.00\n");
        }

        sb.append("----------------------------\n");
        sb.append("Status:      ").append(order.getStatus()).append("\n");
        sb.append("============================\n");

        return sb.toString();
    }
}