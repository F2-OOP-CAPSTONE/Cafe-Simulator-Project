package form;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Recipe extends JFrame {
    public Recipe() {
        setTitle("Recipes");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));
        panel.add(new JLabel("Add recipe info / art here."));
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
