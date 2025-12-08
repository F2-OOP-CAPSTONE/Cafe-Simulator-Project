package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends JFrame {
    private JPanel MainMenu;
    private JLabel titleLabel;
    private JButton startButton;
    private JButton loadButton;
    private JButton exitButton;

    public UI() {
        setTitle("Cafe Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setContentPane(MainMenu);
        setLocationRelativeTo(null);


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Game selected");
                JOptionPane.showMessageDialog(null, "Starting Game...");
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Load Game selected");
                JOptionPane.showMessageDialog(null, "Loading Save File...");
            }
        });

        // 3. Exit Button Logic
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting...");
                System.exit(0);
            }
        });

        setVisible(true);
    }
    private void createUIComponents() {
        MainMenu = new BackgroundPanel("src/Images/Menu.png");

        titleLabel = new ScalableLabel("src/Images/Title.png");

        startButton = new ScalableButton("src/Images/Start.png");
        loadButton  = new ScalableButton("src/Images/Load.png");
        exitButton  = new ScalableButton("src/Images/Exit.png");
    }

    public static void main(String[] args) {
        new UI();
    }
}