import CoffeeShop.CoffeeShop;
import CoffeeShop.Order;
import UI.UI;
import form.TempPrepareDrinkGui;
import form.DayStartOverlay;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::showMainMenu);
    }

    private static void showMainMenu() {
        UI[] menuHolder = new UI[1];
        UI menu = new UI(() -> {
            UI m = menuHolder[0];
            if (m != null) {
                m.dispose();
            }
            launchGame();
        }, () -> {
            UI m = menuHolder[0];
            if (m != null) {
                m.dispose();
            }
            System.exit(0);
        });
        menuHolder[0] = menu;
    }

    private static void launchGame() {
        CoffeeShop cafe = new CoffeeShop("Java Jolt");

        TempPrepareDrinkGui gui = new TempPrepareDrinkGui(cafe, () -> {
            SwingUtilities.invokeLater(Main::showMainMenu);
        });

        gui.setVisible(true);
        DayStartOverlay.show(gui, cafe.getCurrentDay(), null);
    }
}
