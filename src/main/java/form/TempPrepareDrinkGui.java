package form;

import CoffeeShop.CoffeeShop;
import CoffeeShop.Order;
import UI.BackgroundPanel;
import drinks.DrinkSize;
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
    private final GameTransitionListener listener;

    private final Color wood = new Color(0x6F4F28);
    private final Color parchment = new Color(0xF8F1E0);

    // Layout helpers
    private final CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer;

    // Labels
    private JLabel customerInfoLabel;
    private JLabel customerMsgLabel;
    private JLabel orderLabel;
    private JLabel patienceLabel;
    private JLabel fameLabel;

    // Ingredient counters
    private JLabel coffeeCount;
    private JLabel milkCount;
    private JLabel waterCount;
    private JLabel sugarCount;
    private JLabel chocoCount;
    private JLabel syrupCount;

    private DrinkSize selectedSize;
    private int coffeeAmnt = 0, milkAmnt = 0, waterAmnt = 0;
    private int sugarAmnt = 0, chocoAmnt = 0, syrupAmnt = 0;

    public TempPrepareDrinkGui(CoffeeShop cafe, GameTransitionListener listener) {
        this.cafe = cafe;
        this.listener = listener;

        setTitle("Coffee Prep Station");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        buildUi();

        pack();
        setLocationRelativeTo(null);

        checkDayCycle();
    }

    private void buildUi() {
        BackgroundPanel root = new BackgroundPanel("src/Images/CustomerIntro.png");
        root.setLayout(new BorderLayout(0, 14));
        root.setPreferredSize(new Dimension(960, 540));

        JPanel overlay = new JPanel(new BorderLayout(0, 14));
        overlay.setOpaque(false);
        overlay.setBorder(new EmptyBorder(18, 18, 18, 18));
        root.add(overlay, BorderLayout.CENTER);

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setOpaque(true);
        card.setBackground(new Color(parchment.getRed(), parchment.getGreen(), parchment.getBlue(), 210));
        card.setBorder(new CompoundBorder(
                new LineBorder(wood, 3, true),
                new EmptyBorder(12, 12, 12, 12)
        ));
        overlay.add(card, BorderLayout.CENTER);

        card.add(buildTopHeader(), BorderLayout.NORTH);

        mainContainer = new JPanel(cardLayout);
        mainContainer.setOpaque(false);
        mainContainer.add(buildSizeSelectionPanel(), "SIZE_SCREEN");
        mainContainer.add(buildPreparationPanel(), "PREP_SCREEN");
        card.add(mainContainer, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel buildTopHeader() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(0, 4, 8, 4));

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        customerInfoLabel = accentLabel("CUSTOMER:");
        customerMsgLabel = accentLabel("");
        orderLabel = accentLabel("ORDER:");

        info.add(customerInfoLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(customerMsgLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(orderLabel);

        JPanel infoRight = new JPanel();
        infoRight.setOpaque(false);
        infoRight.setLayout(new BoxLayout(infoRight, BoxLayout.Y_AXIS));
        patienceLabel = accentLabel("Patience: -");
        patienceLabel.setForeground(new Color(0xB44F3A));
        fameLabel = accentLabel("Fame: -");
        infoRight.add(patienceLabel);
        infoRight.add(Box.createVerticalStrut(4));
        infoRight.add(fameLabel);

        topPanel.add(info, BorderLayout.CENTER);
        topPanel.add(infoRight, BorderLayout.EAST);
        return topPanel;
    }

    private JPanel buildSizeSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JLabel instruction = accentLabel("Select Cup Size");
        instruction.setFont(instruction.getFont().deriveFont(Font.BOLD, 18f));
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instruction);
        panel.add(Box.createVerticalStrut(16));

        Dimension cupBtnSize = new Dimension(140, 42);

        JPanel sizeRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        sizeRow.setOpaque(false);
        JButton btnSmall = secondaryButton("Small Cup");
        btnSmall.setPreferredSize(cupBtnSize);
        btnSmall.addActionListener(e -> selectSize(DrinkSize.SMALL));
        JButton btnMedium = secondaryButton("Medium Cup");
        btnMedium.setPreferredSize(cupBtnSize);
        btnMedium.addActionListener(e -> selectSize(DrinkSize.MEDIUM));
        JButton btnLarge = secondaryButton("Large Cup");
        btnLarge.setPreferredSize(cupBtnSize);
        btnLarge.addActionListener(e -> selectSize(DrinkSize.LARGE));

        sizeRow.add(btnSmall);
        sizeRow.add(btnMedium);
        sizeRow.add(btnLarge);
        panel.add(sizeRow);

        panel.add(Box.createVerticalStrut(18));
        JPanel utility = buildUtilityPanel();
        utility.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(utility);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel buildPreparationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 12, 6, 12);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        coffeeCount = centeredLabel("0");
        milkCount = centeredLabel("0");
        waterCount = centeredLabel("0");
        sugarCount = centeredLabel("0");
        chocoCount = centeredLabel("0");
        syrupCount = centeredLabel("0");

        addCountAndButton(grid, c, 0, 0, coffeeCount, "Coffee", Ingredients.COFFEE, () -> coffeeAmnt++, () -> coffeeAmnt);
        addCountAndButton(grid, c, 1, 0, milkCount, "Milk", Ingredients.MILK, () -> milkAmnt++, () -> milkAmnt);
        addCountAndButton(grid, c, 2, 0, waterCount, "Water", Ingredients.WATER, () -> waterAmnt++, () -> waterAmnt);

        addCountAndButton(grid, c, 0, 2, sugarCount, "Sugar", Ingredients.SUGAR, () -> sugarAmnt++, () -> sugarAmnt);
        addCountAndButton(grid, c, 1, 2, chocoCount, "Chocolate", Ingredients.CHOCOLATE, () -> chocoAmnt++, () -> chocoAmnt);
        addCountAndButton(grid, c, 2, 2, syrupCount, "Syrup", Ingredients.SYRUP, () -> syrupAmnt++, () -> syrupAmnt);

        panel.add(grid, BorderLayout.CENTER);

        JPanel bottomBox = new JPanel();
        bottomBox.setOpaque(false);
        bottomBox.setLayout(new BoxLayout(bottomBox, BoxLayout.Y_AXIS));
        bottomBox.setBorder(new EmptyBorder(16, 0, 0, 0));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonRow.setOpaque(false);

        JButton checkInventoryButton = secondaryButton("Inventory");
        checkInventoryButton.addActionListener(this::onCheckInventory);

        JButton resetButton = secondaryButton("Reset");
        resetButton.addActionListener(this::onReset);

        JButton recipeButton = secondaryButton("Recipes");
        recipeButton.addActionListener(e -> new Recipe().setVisible(true));

        JButton serveButton = primaryButton("Serve Drink");
        serveButton.addActionListener(e -> onServe());

        buttonRow.add(checkInventoryButton);
        buttonRow.add(resetButton);
        buttonRow.add(recipeButton);
        buttonRow.add(serveButton);

        bottomBox.add(buttonRow);
        bottomBox.add(Box.createVerticalStrut(22));
        bottomBox.add(buildUtilityPanel());

        panel.add(bottomBox, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildUtilityPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        p.setOpaque(false);

        JButton statusBtn = primaryButton("Shop Stats");
        statusBtn.addActionListener(e -> new ShopStatusGui(cafe).setVisible(true));
        statusBtn.setPreferredSize(new Dimension(140, 42));

        p.add(statusBtn);
        return p;
    }

    private void selectSize(DrinkSize size) {
        this.selectedSize = size;
        showScreen("PREP_SCREEN");
    }

    private void onServe() {
        if (order == null || selectedSize == null) return;

        Order resultOrder = cafe.serveDrink(selectedSize);
        if (resultOrder != null) {
            receiptGui = new ReceiptGui(resultOrder);
            receiptGui.setVisible(true);

            receiptGui.getServeNextButton().addActionListener(evt -> {
                receiptGui.dispose();
                checkDayCycle();
            });
        } else {
            checkDayCycle();
        }
    }

    private void checkDayCycle() {
        if (cafe.isDayFinished()) {
            if (cafe.isBankrupt()) {
                showGameOverScreen();
                return;
            }
            DayCompleteOverlay.show(this, cafe.getDaySummary(), () -> {
                cafe.startNextDay();
                startNextCustomer();
            });
        } else {
            startNextCustomer();
        }
    }

    private void startNextCustomer() {
        order = cafe.spawnCustomer();
        resetCounts();
        selectedSize = null;
        showScreen("SIZE_SCREEN");
        updateContent();
    }

    private void addIngredient(Ingredients ing, Runnable increment, java.util.function.IntSupplier valueSup, JLabel label) {
        boolean added = cafe.addIngredient(ing);
        if (added) {
            increment.run();
            label.setText(Integer.toString(valueSup.getAsInt()));
            if (order != null) {
                patienceLabel.setText("Patience: " + order.getCustomer().getPatience());
            }
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot add more " + ing.name() + " (out of stock/Time up).", "Inventory", JOptionPane.WARNING_MESSAGE);
            if (order != null && order.getCustomer().isPatienceZero()) {
                checkDayCycle();
            }
        }
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
        gbc.gridy = row + 1;
        JButton b = secondaryButton(btnText);
        b.addActionListener(e -> addIngredient(ing, inc, valSupplier, countLabel));
        grid.add(b, gbc);
    }

    private void showGameOverScreen() {
        JDialog gameOverDialog = new JDialog(this, "Game Over", true);
        gameOverDialog.setLayout(new BorderLayout(15, 15));
        gameOverDialog.setPreferredSize(new Dimension(380, 220));
        gameOverDialog.getContentPane().setBackground(new Color(parchment.getRed(), parchment.getGreen(), parchment.getBlue(), 230));

        JLabel message = new JLabel("<html><center><h1>GAME OVER</h1>" +
                "You went bankrupt!<br>" +
                "Final Balance: $" + String.format("%.2f", cafe.getCurrentBalance()) +
                "<center></html>", SwingConstants.CENTER);
        message.setForeground(new Color(0x2E3138));
        message.setFont(new Font("Bahnschrift", Font.BOLD, 16));

        JButton backButton = primaryButton("Return to Main Menu");
        backButton.addActionListener(e -> {
            gameOverDialog.dispose();
            listener.returnToMainMenu();
            this.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        gameOverDialog.add(message, BorderLayout.CENTER);
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);

        gameOverDialog.pack();
        gameOverDialog.setLocationRelativeTo(null);
        gameOverDialog.setVisible(true);
    }

    private void onReset(ActionEvent e) {
        cafe.resetMixingGlass();
        resetCounts();
        selectedSize = null;
        showScreen("SIZE_SCREEN");
        updateContent();
    }

    private void onCheckInventory(ActionEvent e) {
        JDialog dialog = new JDialog(this, "Inventory", true);
        dialog.setLayout(new BorderLayout(12, 12));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("src/Images/CustomerIntro.png");
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
            String report = cafe.restockInventory();
            inventoryLabel.setText(html(cafe.getInventoryString()));
            JOptionPane.showMessageDialog(dialog, report);
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

    private void updateContent() {
        if (order != null) {
            customerInfoLabel.setText("CUSTOMER: " + order.getCustomer().getName());
            customerMsgLabel.setText(html(order.getCustomer().getDialogue()));
            orderLabel.setText("ORDER: " + order.getRequestedSize() + " " + order.getDrinkName());

            patienceLabel.setText("Patience: " + order.getCustomer().getPatience());
            fameLabel.setText("Fame: " + cafe.getFame());
        } else {
            customerInfoLabel.setText("Waiting for customer...");
            customerMsgLabel.setText("");
            orderLabel.setText("");
        }

        coffeeCount.setText(Integer.toString(coffeeAmnt));
        milkCount.setText(Integer.toString(milkAmnt));
        waterCount.setText(Integer.toString(waterAmnt));
        sugarCount.setText(Integer.toString(sugarAmnt));
        chocoCount.setText(Integer.toString(chocoAmnt));
        syrupCount.setText(Integer.toString(syrupAmnt));

        repaint();
    }

    private void showScreen(String name) {
        cardLayout.show(mainContainer, name);
        mainContainer.revalidate();
        mainContainer.repaint();
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
