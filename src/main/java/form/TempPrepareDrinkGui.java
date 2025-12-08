package form;

import CoffeeShop.CoffeeShop;
import CoffeeShop.Order;
import drinks.DrinkSize;
import drinks.Ingredients;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TempPrepareDrinkGui extends JFrame {
    private final CoffeeShop cafe;
    private Order order;
    private ReceiptGui receiptGui;

    private JLabel customerInfoLabel;
    private JLabel customerMsgLabel;
    private JLabel orderLabel;

    private JLabel coffeeCount;
    private JLabel milkCount;
    private JLabel waterCount;
    private JLabel sugarCount;
    private JLabel chocoCount;
    private JLabel syrupCount;

    private JButton confirmButton;

    private int coffeeAmnt = 0;
    private int milkAmnt = 0;
    private int waterAmnt = 0;
    private int sugarAmnt = 0;
    private int chocoAmnt = 0;
    private int syrupAmnt = 0;

    public TempPrepareDrinkGui(CoffeeShop cafe) {
        this.cafe = cafe;
        buildUi();
        setTitle("Coffee Prep");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        updateContent();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(0, 10));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setPreferredSize(new Dimension(420, 360));
        root.setMinimumSize(new Dimension(380, 320));

        // Customer info (left-aligned, multi-line friendly via HTML)
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        customerInfoLabel = new JLabel("NEW CUSTOMER:");
        customerMsgLabel = new JLabel("");
        orderLabel = new JLabel("ORDER:");
        customerInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        customerMsgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        orderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(customerInfoLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(customerMsgLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(orderLabel);
        root.add(info, BorderLayout.NORTH);

        // Ingredient controls laid out in a stable GridBag to prevent overflow
        coffeeCount = centeredLabel("0");
        milkCount = centeredLabel("0");
        waterCount = centeredLabel("0");
        sugarCount = centeredLabel("0");
        chocoCount = centeredLabel("0");
        syrupCount = centeredLabel("0");

        JPanel grid = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 4, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        addCountAndButton(grid, gbc, 0, 0, coffeeCount, "Coffee", Ingredients.COFFEE, () -> coffeeAmnt++, () -> coffeeAmnt);
        addCountAndButton(grid, gbc, 1, 0, milkCount, "Milk", Ingredients.MILK, () -> milkAmnt++, () -> milkAmnt);
        addCountAndButton(grid, gbc, 2, 0, waterCount, "Water", Ingredients.WATER, () -> waterAmnt++, () -> waterAmnt);

        addCountAndButton(grid, gbc, 0, 2, sugarCount, "Sugar", Ingredients.SUGAR, () -> sugarAmnt++, () -> sugarAmnt);
        addCountAndButton(grid, gbc, 1, 2, chocoCount, "Chocolate", Ingredients.CHOCOLATE, () -> chocoAmnt++, () -> chocoAmnt);
        addCountAndButton(grid, gbc, 2, 2, syrupCount, "Syrup", Ingredients.SYRUP, () -> syrupAmnt++, () -> syrupAmnt);

        // Confirm + Reset row
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this::onConfirm);
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(this::onReset);
        JButton checkInventoryButton = new JButton("Check Inventory");
        checkInventoryButton.addActionListener(this::onCheckInventory);

        gbc.gridy = 4;
        gbc.gridx = 0;
        grid.add(confirmButton, gbc);
        gbc.gridx = 2;
        grid.add(resetButton, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        grid.add(checkInventoryButton, gbc);

        root.add(grid, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JLabel centeredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(60, 16));
        return label;
    }

    private void addCountAndButton(JPanel grid, GridBagConstraints base, int col, int row,
                                   JLabel countLabel, String btnText, Ingredients ing,
                                   Runnable inc, java.util.function.IntSupplier valSupplier) {
        GridBagConstraints gbc = (GridBagConstraints) base.clone();
        gbc.gridx = col;
        gbc.gridy = row;
        grid.add(countLabel, gbc);

        gbc = (GridBagConstraints) base.clone();
        gbc.gridx = col;
        gbc.gridy = row + 1;
        JButton b = new JButton(btnText);
        b.addActionListener(e -> addIngredient(ing, inc, valSupplier, countLabel));
        grid.add(b, gbc);
    }

    private void addIngredient(Ingredients ing, Runnable increment, java.util.function.IntSupplier valueSup, JLabel label) {
        boolean added = cafe.getBarista().addIngredient(ing, cafe.getInventory());
        if (added) {
            increment.run();
            label.setText(Integer.toString(valueSup.getAsInt()));
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot add more " + ing.name() + " (out of stock).", "Inventory", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onConfirm(ActionEvent e) {
        if (order == null) return;
        int totalAdded = coffeeAmnt + milkAmnt + waterAmnt + sugarAmnt + chocoAmnt + syrupAmnt;
        if (totalAdded == 0) {
            JOptionPane.showMessageDialog(this, "Add at least one ingredient before serving.", "No Ingredients", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        cafe.serveDrink(); // uses barista’s built drink
        confirmButton.setEnabled(false);
        receiptGui = new ReceiptGui(order);
        receiptGui.setVisible(true);
        receiptGui.getServeNextButton().addActionListener(evt -> {
            order = cafe.spawnCustomer();
            resetCounts();
            updateContent();
            receiptGui.setVisible(false);
            // re-pack to ensure layout fits after text changes
            pack();
        });
    }

    private void onReset(ActionEvent e) {
        cafe.getBarista().resetIngredients(cafe.getInventory());
        resetCounts();
        updateContent();
        pack();
    }

    private void onCheckInventory(ActionEvent e) {
        JDialog dialog = new JDialog(this, "Inventory", true);
        dialog.setLayout(new BorderLayout(12, 12));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel inventoryLabel = new JLabel(html(cafe.getBarista().checkInventory(cafe.getInventory())));
        inventoryLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dialog.add(inventoryLabel, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton restockButton = new JButton("Restock Inventory");
        restockButton.addActionListener(evt -> {
            String report = cafe.getBarista().restockInventory(cafe.getInventory());
            inventoryLabel.setText(html(report));
            updateContent();
            dialog.pack();
        });

        JButton backButton = new JButton("Back to Customer");
        backButton.addActionListener(evt -> dialog.dispose());

        buttons.add(restockButton);
        buttons.add(backButton);
        dialog.add(buttons, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void resetCounts() {
        coffeeAmnt = milkAmnt = waterAmnt = sugarAmnt = chocoAmnt = syrupAmnt = 0;
    }

    void updateContent() {
        if (order != null) {
            customerInfoLabel.setText("NEW CUSTOMER: " + order.getCustomer().getName());
            customerMsgLabel.setText(html(order.getCustomer().getDialogue()));
            orderLabel.setText("ORDER: " + order.getDrinkName());
        } else {
            customerInfoLabel.setText("Waiting for customer...");
            customerMsgLabel.setText("");
            orderLabel.setText("");
        }
        if (confirmButton != null) {
            confirmButton.setEnabled(order != null);
        }
        coffeeCount.setText(Integer.toString(coffeeAmnt));
        milkCount.setText(Integer.toString(milkAmnt));
        waterCount.setText(Integer.toString(waterAmnt));
        sugarCount.setText(Integer.toString(sugarAmnt));
        chocoCount.setText(Integer.toString(chocoAmnt));
        syrupCount.setText(Integer.toString(syrupAmnt));
        pack();
        repaint();
    }

    private String html(String text) {
        return "<html>" + text.replace("\n", "<br>") + "</html>";
    }

    public void SetOrder(Order order) {
        this.order = order;
        updateContent();
    }
}
