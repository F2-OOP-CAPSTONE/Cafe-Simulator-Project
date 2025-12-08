package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends JFrame {
    private final Runnable startAction;
    private final Runnable exitAction;

    private JPanel MainMenu;
    private JLabel titleLabel;
    private JButton startButton;
    private JButton loadButton;
    private JButton exitButton;

    public UI() {
        this(null, null);
    }

    public UI(Runnable startAction, Runnable exitAction) {
        this.startAction = startAction;
        this.exitAction = exitAction;

        buildUi();
        setTitle("Cafe Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setContentPane(MainMenu);
        setLocationRelativeTo(null);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStart();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Load Game selected");
                JOptionPane.showMessageDialog(null, "Loading Save File...");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });

        setVisible(true);
    }

    private void handleStart() {
        System.out.println("Start Game selected");
        if (startAction != null) {
            dispose();
            startAction.run();
        } else {
            JOptionPane.showMessageDialog(null, "Starting Game...");
        }
    }

    private void handleExit() {
        System.out.println("Exiting...");
        dispose();
        if (exitAction != null) {
            exitAction.run();
        } else {
            System.exit(0);
        }
    }

    private void buildUi() {
        MainMenu = new BackgroundPanel("src/Images/Menu.png");
        MainMenu.setLayout(new GridBagLayout());
        MainMenu.setPreferredSize(new Dimension(960, 540));

        titleLabel = new ScalableLabel("src/Images/Title.png");
        startButton = new ScalableButton("src/Images/Start.png");
        loadButton = new ScalableButton("src/Images/Load.png");
        exitButton = new ScalableButton("src/Images/Exit.png");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        MainMenu.add(titleLabel, gbc);
        gbc.gridy = 1;
        MainMenu.add(startButton, gbc);
        gbc.gridy = 2;
        MainMenu.add(loadButton, gbc);
        gbc.gridy = 3;
        MainMenu.add(exitButton, gbc);
    }

    public static void main(String[] args) {
        new UI();
    }
}
