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

        if(drink != null && order.getPrice() > 0) {
            sb.append("Item:        ").append(drink.getFullName()).append("\n");
            sb.append(String.format("Price:       Php%.2f\n", order.getPrice()));
            sb.append(String.format("Tip:         Php%.2f\n", order.getTip())); // Shows Tip
            sb.append("-----------------------------\n");
            sb.append(String.format("TOTAL:       Php%.2f\n", order.getTotalCost())); // Shows Total
            sb.append("Calories:    ").append(drink.getCalorie()).append(" kcal\n");
        } else {
            // Failed Order
            sb.append("Item:        ").append(drink != null ? drink.getFullName() : "[Unknown]").append("\n");
            sb.append("Status:      [REFUSED TO PAY]\n");
            sb.append("Total:       Php0.00\n");
        }

        sb.append("============================\n");

        return sb.toString();
    }
}
