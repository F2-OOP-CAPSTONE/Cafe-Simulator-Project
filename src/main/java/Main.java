import CoffeeShop.CoffeeShop;
import CoffeeShop.Order;
import form.TempPrepareDrinkGui;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CoffeeShop cafe = new CoffeeShop("Java Jolt");
            Order firstOrder = cafe.spawnCustomer();

            TempPrepareDrinkGui gui = new TempPrepareDrinkGui(cafe);
            if (firstOrder != null) {
                gui.SetOrder(firstOrder);
            }
            gui.setVisible(true);
        });
    }
}
