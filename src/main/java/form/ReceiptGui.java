package form;

import CoffeeShop.Order;
import UI.BackgroundPanel;
import drinks.Drink;
import entities.Customer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ReceiptGui extends JFrame {
    private final Color paper = new Color(0xF8F1E0);
    private final Color border = new Color(0x6F4F28);
    private final Color ink = new Color(0x2F2A28);
    private final Color accent = new Color(0xB44F3A);
    private final Color shadow = new Color(0, 0, 0, 40);

    private final Font bodyFont = new Font("Bahnschrift", Font.PLAIN, 15);

    private final JButton serveNextButton = primaryButton("Serve Next");

    public ReceiptGui(Order order) {
        setTitle("Receipt");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        buildUi(order);
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUi(Order order) {
        Drink drink = order.getServedDrink();

        String wanted = order.getDrinkName();
        String got = drink.getName();
        Customer cust = order.getCustomer();

        String reaction = wanted.equalsIgnoreCase(got) ? cust.getHappyReaction() : cust.getSadReaction();
        int tip = cust.reactToDrink(drink, wanted);

        double total = order.getPrice() + tip;

        BackgroundPanel root = new BackgroundPanel("src/Images/Menu.png");
        root.setLayout(new GridBagLayout());

        JPanel paperStack = new JPanel(new BorderLayout());
        paperStack.setOpaque(false);
        paperStack.setBorder(new CompoundBorder(new MatteBorder(4, 4, 10, 10, shadow), new EmptyBorder(0, 0, 0, 0)));

        JPanel paperSheet = new JPanel();
        paperSheet.setOpaque(true);
        paperSheet.setBackground(border);
        paperSheet.setLayout(new BoxLayout(paperSheet, BoxLayout.Y_AXIS));
        paperSheet.setBorder(new CompoundBorder(new MatteBorder(4, 4, 4, 4, border), new EmptyBorder(8, 10, 8, 10)));

        paperSheet.add(cutEdge(true));

        JPanel content = new JPanel();
        content.setOpaque(true);
        content.setBackground(paper);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(14, 14, 14, 14));

        JLabel title = new JLabel("RECEIPT");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(new Font("Bahnschrift", Font.BOLD, 22));
        title.setForeground(accent);
        content.add(title);

        JLabel sub = new JLabel("Thank you, " + cust.getName() + "!");
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        sub.setFont(bodyFont);
        sub.setForeground(ink);
        sub.setBorder(new EmptyBorder(2, 0, 10, 0));
        content.add(sub);

        content.add(line("Order #", "#" + order.getID(), true));
        content.add(line("Drink", drink.getFullName(), true));
        content.add(line("Calories", drink.getCalorie() + " cal", false));

        content.add(divider());
        content.add(line("Price", String.format("$%.2f", order.getPrice()), true));
        content.add(line("Tip", tip > 0 ? "$" + tip : "-", false));
        content.add(line("Total", String.format("$%.2f", total), true));

        content.add(divider());
        content.add(reactionLabel(reaction, tip > 0));

        content.add(Box.createVerticalStrut(12));
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnRow.setOpaque(false);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRow.add(serveNextButton);
        content.add(btnRow);
        content.add(Box.createVerticalStrut(6));

        paperSheet.add(content);
        paperSheet.add(cutEdge(false));

        paperStack.add(paperSheet, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        root.add(paperStack, gbc);

        setContentPane(root);
    }

    private JPanel line(String label, String value, boolean bold) {
        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel left = new JLabel(label.toUpperCase());
        left.setForeground(ink);
        left.setFont(bold ? bodyFont.deriveFont(Font.BOLD) : bodyFont);

        JLabel right = new JLabel(value);
        right.setForeground(ink);
        right.setFont(bold ? bodyFont.deriveFont(Font.BOLD) : bodyFont);

        row.add(left);
        row.add(Box.createHorizontalStrut(12));
        row.add(right);
        row.setBorder(new EmptyBorder(4, 0, 4, 0));
        return row;
    }

    private JComponent divider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(border);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        sep.setBorder(new EmptyBorder(6, 0, 6, 0));
        return sep;
    }

    private JLabel reactionLabel(String text, boolean positive) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setForeground(positive ? accent : new Color(0x7A3F3A));
        label.setFont(bodyFont.deriveFont(Font.BOLD));
        label.setBorder(new EmptyBorder(4, 0, 4, 0));
        return label;
    }

    private JComponent cutEdge(boolean top) {
        return new JComponent() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(0, 18);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();
                g2.setColor(border);
                g2.fillRect(0, 0, w, h);
                g2.setColor(paper);
                int inset = 2;
                g2.fillRect(inset, inset, w - inset * 2, h - inset * 2);
                g2.setColor(border);
                g2.fillRect(0, 0, w, h);
                g2.setColor(border);
                int step = 16;
                for (int x = 0; x < w + step; x += step) {
                    int[] xs = {x, x + step / 2, x + step};
                    int[] ys = top ? new int[]{h, 0, h} : new int[]{0, h, 0};
                    g2.setColor(border);
                    g2.fillPolygon(xs, ys, 3);
                    g2.setColor(paper);
                    int innerOffset = 4;
                    int[] innerXs = {x + innerOffset, x + step / 2, x + step - innerOffset};
                    int[] innerYs = top ? new int[]{h - 1, innerOffset, h - 1} : new int[]{1, h - innerOffset, 1};
                    g2.fillPolygon(innerXs, innerYs, 3);
                }
                g2.dispose();
            }
        };
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

    public JButton getServeNextButton() {
        return serveNextButton;
    }
}
