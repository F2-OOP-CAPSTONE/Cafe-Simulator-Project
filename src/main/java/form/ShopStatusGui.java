package form;

import CoffeeShop.CoffeeShop;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ShopStatusGui extends JFrame {
    public ShopStatusGui(CoffeeShop cafe) {
        setTitle("Shop Status: " + cafe.name);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        buildUi(cafe);
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUi(CoffeeShop cafe) {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        root.setPreferredSize(new Dimension(300, 350));

        JLabel title = centeredLabel("CAFE DASHBOARD");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        root.add(title);
        root.add(Box.createVerticalStrut(15));

        String rank = getRankTitle(cafe.getFame());
        root.add(label("Rank: " + rank));
        root.add(label("Fame: " + cafe.getFame()));
        root.add(new JSeparator());

        root.add(label(String.format("Current Balance: $%.2f", cafe.getCurrentBalance())));
        root.add(label(String.format("Daily Tax Rate:  $%.2f", cafe.getDailyTax())));
        root.add(new JSeparator());

        root.add(label("Current Day: " + cafe.getCurrentDay()));
        root.add(label("Today's Progress: " + cafe.getCustomersServedToday() + " / " + cafe.getCustomersPerDay()));

        root.add(Box.createVerticalGlue());
        JButton closeBtn = new JButton("Close");
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(e -> this.dispose());
        root.add(closeBtn);

        setContentPane(root);
    }

    private String getRankTitle(int fame) {
        if (fame < 0) return "Disgraceful";
        if (fame < 20) return "Newbie Cafe";
        if (fame < 50) return "Local Spot";
        if (fame < 100) return "Trending";
        return "Legendary";
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(2, 0, 2, 0));
        return label;
    }

    private JLabel centeredLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
