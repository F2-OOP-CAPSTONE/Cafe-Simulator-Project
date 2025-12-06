package form;

import CoffeeShop.*;
import drinks.Drink;
import entities.Customer;

import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;

public class ReceiptGui extends JFrame{
    private JPanel ReceiptRootPane;
    private JPanel OrderSummaryPane;
    private JLabel ItemLabel;
    private JLabel PriceLabel;
    private JLabel CaloriesLabel;
    private JLabel StatuesLabel;
    private JLabel OrderIDLabel;
    private JLabel CustomerNameLabel;
    private JLabel TipLabel;
    private JLabel CustomerCmntLabel;
    private JLabel ServingLabel;
    private JButton serveNextButton;

    public ReceiptGui(Order order){
        setContentPane(ReceiptRootPane);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        pack();

        Drink drink = order.getServedDrink();
        order.completeOrder(drink);
        // Check if customer is happy
        String wanted = order.getDrinkName();
        String got = drink.getName();
        Customer cust = order.getCustomer();

        if(wanted.equalsIgnoreCase(got)) {
            CustomerCmntLabel.setText(cust.getHappyReaction());
        } else {
            CustomerCmntLabel.setText(cust.getSadReaction());
        }

        int tip = order.getCustomer().reactToDrink(drink, wanted);

        if (tip > 0) {
            TipLabel.setText("Tip: " + tip);
        } else {
            TipLabel.setText("No tip received");
        }

        OrderIDLabel.setText("Order ID: " + order.getID());
        CustomerNameLabel.setText("Customer: " + order.getCustomer().getName());
        ItemLabel.setText(order.getServedDrink().getFullName());
        PriceLabel.setText(String.format("%.2f", drink.getPrice()));
        CaloriesLabel.setText(Integer.toString(drink.getCalorie()));

        repaint();
    }

    public JButton getServeNextButton(){
        return serveNextButton;
    }
}
