package form;

import drinks.DrinkType;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Recipe extends JFrame {
    public Recipe() {
        setTitle("Recipe Book");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel header = new JLabel("Standard  Recipes");
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(header);
        panel.add(Box.createVerticalStrut(10));

        for (DrinkType type : DrinkType.values()) {
            if (type == DrinkType.COFFEEofSADNESSandGRIEF) continue;

            StringBuilder sb = new StringBuilder();
            sb.append("<html><b>").append(type.getName()).append("</b>: ");

            Map <String, Integer> recipeMap = type.getRecipe();
            boolean isFirstItem = true;

            for (Map.Entry<String, Integer> entry : recipeMap.entrySet()) {
                int amount = entry.getValue();
                if(amount > 0) {
                    if(!isFirstItem) sb.append(", ");
                    sb.append(amount).append(" ").append(formatName(entry.getKey()));
                    isFirstItem = false;
                }
            }
            sb.append("</html>");

            JLabel label = new JLabel(sb.toString());
            label.setFont(new Font("SansSerif", Font.PLAIN, 12));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(label);
            panel.add(Box.createVerticalStrut(5));
        }

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private String formatName(String upper) {
        if (upper == null || upper.isEmpty()) return upper;
        return upper.substring(0, 1).toUpperCase() + upper.substring(1).toLowerCase();
    }
}
