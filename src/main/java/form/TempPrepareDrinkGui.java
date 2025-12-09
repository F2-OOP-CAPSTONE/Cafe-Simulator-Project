package form;

import CoffeeShop.CoffeeShop;
import CoffeeShop.Order;
import UI.BackgroundPanel;
import form.DayStartOverlay;
import form.DayCompleteOverlay;
import drinks.Ingredients;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TempPrepareDrinkGui extends JFrame {
    private final CoffeeShop cafe;
    private Order order;
    private ReceiptGui receiptGui;

    private final Color wood = new Color(0x6F4F28);
    private final Color parchment = new Color(0xF8F1E0);

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

    private JButton recipeButton;

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
        BackgroundPanel root = new BackgroundPanel("src/Images/Menu.png");
        root.setLayout(new BorderLayout(0, 14));
        root.setPreferredSize(new Dimension(960, 540));

        JPanel overlay = new JPanel(new BorderLayout(0, 14));
        overlay.setOpaque(false);
        overlay.setBorder(new EmptyBorder(18, 18, 18, 18));
        root.add(overlay, BorderLayout.CENTER);

        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setOpaque(true);
        card.setBackground(new Color(parchment.getRed(), parchment.getGreen(), parchment.getBlue(), 210));
        card.setBorder(new CompoundBorder(
                new LineBorder(wood, 3, true),
                new EmptyBorder(18, 18, 18, 18)
        ));
        overlay.add(card, BorderLayout.CENTER);

        // Customer info (left-aligned, multi-line friendly via HTML)
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        customerInfoLabel = accentLabel("NEW CUSTOMER:");
        customerMsgLabel = accentLabel("");
        orderLabel = accentLabel("ORDER:");
        customerInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        customerMsgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        orderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(customerInfoLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(customerMsgLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(orderLabel);
        card.add(info, BorderLayout.NORTH);

        // Ingredient controls laid out in a stable GridBag to prevent overflow
        coffeeCount = centeredLabel("0");
        milkCount = centeredLabel("0");
        waterCount = centeredLabel("0");
        sugarCount = centeredLabel("0");
        chocoCount = centeredLabel("0");
        syrupCount = centeredLabel("0");

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        addCountAndButton(grid, gbc, 0, 0, coffeeCount, "Coffee", Ingredients.COFFEE, () -> coffeeAmnt++, () -> coffeeAmnt);
        addCountAndButton(grid, gbc, 1, 0, milkCount, "Milk", Ingredients.MILK, () -> milkAmnt++, () -> milkAmnt);
        addCountAndButton(grid, gbc, 2, 0, waterCount, "Water", Ingredients.WATER, () -> waterAmnt++, () -> waterAmnt);

        addCountAndButton(grid, gbc, 0, 2, sugarCount, "Sugar", Ingredients.SUGAR, () -> sugarAmnt++, () -> sugarAmnt);
        addCountAndButton(grid, gbc, 1, 2, chocoCount, "Chocolate", Ingredients.CHOCOLATE, () -> chocoAmnt++, () -> chocoAmnt);
        addCountAndButton(grid, gbc, 2, 2, syrupCount, "Syrup", Ingredients.SYRUP, () -> syrupAmnt++, () -> syrupAmnt);

        // Confirm + Reset row
        confirmButton = primaryButton("Serve Drink");
        confirmButton.addActionListener(this::onConfirm);
        JButton resetButton = secondaryButton("Reset");
        resetButton.addActionListener(this::onReset);
        JButton checkInventoryButton = secondaryButton("Check Inventory");
        checkInventoryButton.addActionListener(this::onCheckInventory);

        recipeButton = secondaryButton("Recipes");
        recipeButton.addActionListener(e -> {
            new Recipe().setVisible(true);
        });

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setOpaque(false);
        buttons.add(checkInventoryButton);
        buttons.add(resetButton);
        buttons.add(recipeButton);
        buttons.add(confirmButton);
        grid.add(buttons, gbc);

        card.add(grid, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JLabel centeredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(68, 20));
        label.setFont(new Font("Bahnschrift", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(new Color(0xF1E5C8));
        label.setForeground(new Color(0x1E4BBE));
        label.setBorder(new LineBorder(new Color(0x6F4F28), 1, true));
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
        JButton b = secondaryButton(btnText);
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

        //-- Updated Day Cycle Logic --
        receiptGui.getServeNextButton().addActionListener(evt -> {
            receiptGui.setVisible(false);
            receiptGui.dispose();
            if (cafe.isDayFinished()) {
                DayCompleteOverlay.show(this, cafe.getDaySummary(), () -> {
                    cafe.startNextDay();
                    showDayIntroAndSpawn();
                });
            } else {
                order = cafe.spawnCustomer();
                resetCounts();
                updateContent();
                // re-pack to ensure layout fits after text changes
                pack();
            }
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

        BackgroundPanel bg = new BackgroundPanel("src/Images/Menu.png");
        bg.setLayout(new BorderLayout(0, 14));

        JPanel overlay = new JPanel(new BorderLayout(0, 12));
        overlay.setOpaque(false);
        overlay.setBorder(new EmptyBorder(14, 14, 14, 14));
        bg.add(overlay, BorderLayout.CENTER);

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setOpaque(true);
        card.setBackground(new Color(parchment.getRed(), parchment.getGreen(), parchment.getBlue(), 220));
        card.setBorder(new CompoundBorder(new LineBorder(wood, 2, true), new EmptyBorder(12, 12, 12, 12)));
        overlay.add(card, BorderLayout.CENTER);

        JLabel inventoryLabel = new JLabel(html(cafe.getBarista().checkInventory(cafe.getInventory())));
        inventoryLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        inventoryLabel.setForeground(new Color(0x2E3138));
        inventoryLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
        card.add(inventoryLabel, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        JButton restockButton = primaryButton("Restock Inventory");
        restockButton.addActionListener(evt -> {
            String report = cafe.getBarista().restockInventory(cafe.getInventory());
            inventoryLabel.setText(html(report));
            updateContent();
            dialog.pack();
        });

        JButton backButton = secondaryButton("Back to Customer");
        backButton.addActionListener(evt -> dialog.dispose());

        buttons.add(restockButton);
        buttons.add(backButton);
        card.add(buttons, BorderLayout.SOUTH);

        dialog.setContentPane(bg);
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

    private void showDayIntroAndSpawn() {
        DayStartOverlay.show(this, cafe.getCurrentDay(), () -> {
            order = cafe.spawnCustomer();
            resetCounts();
            updateContent();
            pack();
        });
    }

    private JLabel accentLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        label.setForeground(new Color(0x2E3138));
        return label;
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
        return baseButton(text, new Color(0x2E6F73), new Color(0x214E52), Color.WHITE);
    }

    private JButton secondaryButton(String text) {
        return baseButton(text, new Color(0xF1E5C8), new Color(0x6F4F28), new Color(0x3B2F2F));
    }

    private String html(String text) {
        return "<html>" + text.replace("\n", "<br>") + "</html>";
    }

    public void SetOrder(Order order) {
        this.order = order;
        updateContent();
    }
}
