package form;

import CoffeeShop.Order;
import UI.BackgroundPanel;
import drinks.Drink;
import entities.Customer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ReceiptGui extends JFrame {
    private final Color wood = new Color(0x6F4F28);
    private final Color parchment = new Color(0xF8F1E0);
    private final Color teal = new Color(0x2E6F73);

    private final JButton serveNextButton = primaryButton("Serve Next");

    public ReceiptGui(Order order) {
        setTitle("Receipt");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        buildUi(order);
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUi(Order order) {
        BackgroundPanel root = new BackgroundPanel("src/Images/Menu.png");
        root.setLayout(new BorderLayout(0, 12));

        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);
        overlay.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.add(overlay, BorderLayout.CENTER);

        JPanel card = new JPanel();
        card.setOpaque(true);
        card.setBackground(new Color(parchment.getRed(), parchment.getGreen(), parchment.getBlue(), 220));
        card.setBorder(new CompoundBorder(new LineBorder(wood, 3, true), new EmptyBorder(16, 16, 16, 16)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        overlay.add(card, BorderLayout.CENTER);

        Drink drink = order.getServedDrink();

        String wanted = order.getDrinkName();
        String got = drink.getName();
        Customer cust = order.getCustomer();

        String reaction = wanted.equalsIgnoreCase(got) ? cust.getHappyReaction() : cust.getSadReaction();
        int tip = cust.reactToDrink(drink, wanted);

        card.add(accentLabel("Order ID: " + order.getID()));
        card.add(accentLabel("Customer: " + cust.getName()));
        card.add(accentLabel("Item: " + drink.getFullName()));
        card.add(accentLabel(String.format("Price: %.2f", order.getPrice())));
        card.add(accentLabel("Calories: " + drink.getCalorie()));
        card.add(accentLabel(tip > 0 ? "Tip: " + tip : "No tip received"));
        card.add(accentLabel(reaction));
        card.add(Box.createVerticalStrut(12));
        serveNextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(serveNextButton);

        setContentPane(root);
    }

    private JLabel accentLabel(String text) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        l.setForeground(new Color(0x2E3138));
        l.setBorder(new EmptyBorder(2, 0, 2, 0));
        return l;
    }

    private JButton baseButton(String text, Color bg, Color border, Color fg) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setOpaque(true);
        b.setBorder(new CompoundBorder(new LineBorder(border, 2, true), new EmptyBorder(6, 12, 6, 12)));
        b.setFont(new Font("Bahnschrift", Font.BOLD, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton primaryButton(String text) {
        return baseButton(text, teal, new Color(0x214E52), Color.WHITE);
    }

    public JButton getServeNextButton() {
        return serveNextButton;
    }
}
