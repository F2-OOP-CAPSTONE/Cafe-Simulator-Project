package form;

import CoffeeShop.*;
import drinks.DrinkSize;
import drinks.Ingredients;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TempPrepareDrinkGui extends JFrame{
    private JPanel RootPane;
    private JPanel CustomerInfoPane;
    private JButton coffeeButton;
    private JPanel IngredientsPane;
    private JButton milkButton;
    private JButton waterButton;
    private JButton sugarButton;
    private JButton chocolateButton;
    private JButton syrupButton;
    private JButton confirmButton;
    private JButton ingredientsButton;
    private JLabel CoffeeAmntLabel;
    private JLabel MilkAmntLabel;
    private JLabel WaterAmntLabel;
    private JLabel SugarAmntLabel;
    private JLabel ChocoAmntLabel;
    private JLabel SyrupAmntLabel;
    private JLabel CustomerInfoLabel;
    private JLabel CustomerMsgLabel;
    private JLabel OrderLable;

    Order order;
    ReceiptGui receiptGui;
    Recipe recipe;

    public TempPrepareDrinkGui(CoffeeShop cafe){
        setContentPane(RootPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        updateContent();

        coffeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coffeeAmnt++;
                cafe.addIngredient(Ingredients.COFFEE);
                updateContent();
            }
        });

        syrupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                syrupAmnt++;
                cafe.addIngredient(Ingredients.SYRUP);
                updateContent();
            }
        });

        milkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                milkAmnt++;
                cafe.addIngredient(Ingredients.MILK);
                updateContent();
            }
        });

        waterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                waterAmnt++;
                cafe.addIngredient(Ingredients.WATER);
                updateContent();
            }
        });

        sugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sugarAmnt++;
                cafe.addIngredient(Ingredients.SUGAR);
                updateContent();
            }
        });

        chocolateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chocoAmnt++;
                cafe.addIngredient(Ingredients.CHOCOLATE);
                updateContent();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cafe.serveDrink(DrinkSize.MEDIUM);
                receiptGui = new ReceiptGui(order);
                receiptGui.setVisible(true);
                receiptGui.repaint();
                receiptGui.getServeNextButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        order = cafe.spawnCustomer();
                        receiptGui.setVisible(false);
                        coffeeAmnt = 0;
                        milkAmnt = 0;
                        waterAmnt = 0;
                        sugarAmnt = 0;
                        chocoAmnt = 0;
                        syrupAmnt = 0;
                        updateContent();
                    }
                });
            }
        });

        ingredientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipe = new Recipe();
                recipe.setVisible(true);
            }
        });
    }

    int coffeeAmnt = 0;
    int milkAmnt = 0;
    int waterAmnt = 0;
    int sugarAmnt = 0;
    int chocoAmnt = 0;
    int syrupAmnt = 0;

    void updateContent(){
        if(order != null) {
            CustomerInfoLabel.setText("NEW CUSTOMER: " + order.getCustomer().getName());
            CustomerMsgLabel.setText(order.getCustomer().getDialogue());
            OrderLable.setText("ORDER: " + order.getDrinkName());
        }

        CoffeeAmntLabel.setText(Integer.toString(coffeeAmnt));
        MilkAmntLabel.setText(Integer.toString(milkAmnt));
        WaterAmntLabel.setText(Integer.toString(waterAmnt));
        SugarAmntLabel.setText(Integer.toString(sugarAmnt));
        ChocoAmntLabel.setText(Integer.toString(chocoAmnt));
        SyrupAmntLabel.setText(Integer.toString(syrupAmnt));

        repaint();
    }
    public void SetOrder(Order order){
        this.order = order;
        updateContent();
    }
}
