package form;

import CoffeeShop.Order;
import drinks.Drink;
import entities.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReceiptGui extends JFrame {
    private final JButton serveNextButton = new JButton("Serve Next");

    public ReceiptGui(Order order) {
        setTitle("Receipt");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        buildUi(order);
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUi(Order order) {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));

        Drink drink = order.getServedDrink();

        String wanted = order.getDrinkName();
        String got = drink.getName();
        Customer cust = order.getCustomer();

        String reaction = wanted.equalsIgnoreCase(got) ? cust.getHappyReaction() : cust.getSadReaction();
        int tip = cust.reactToDrink(drink, wanted);

        root.add(label("Order ID: " + order.getID()));
        root.add(label("Customer: " + cust.getName()));
        root.add(label("Item: " + drink.getFullName()));
        root.add(label(String.format("Price: %.2f", order.getPrice())));
        root.add(label("Calories: " + drink.getCalorie()));
        root.add(label(tip > 0 ? "Tip: " + tip : "No tip received"));
        root.add(label(reaction));
        root.add(Box.createVerticalStrut(10));
        root.add(serveNextButton);

        setContentPane(root);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    public JButton getServeNextButton() {
        return serveNextButton;
    }
}
