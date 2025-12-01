package main.java.form;

import javax.swing.*;

public class OrderSummaryGui extends JFrame {
    private JPanel panel1;
    private JLabel Order;
    private JLabel total;
    private JButton button1;

    public OrderSummaryGui(String order, double totalPrice){
        setContentPane(panel1);
        pack();

        Order.setText(order);
        total.setText(String.format("%.2f", totalPrice));
    }
}
