package main.java.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TakeOrderGui extends JFrame{
    private JPanel rootMenuPanel;
    private JPanel DrinksPanel;
    private JRadioButton americanoRadioButton;
    private JRadioButton latteRadioButton;
    private JRadioButton cappucinoRadioButton;
    private JRadioButton smallRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton largeRadioButton;
    private JPanel SizePanel;
    private JCheckBox extraShotCheckBox;
    private JCheckBox syrupCheckBox;
    private JButton button1;
    private ButtonGroup SizeBtnGroup;
    private ButtonGroup DrinksGroup;

    public TakeOrderGui(){
        setContentPane(rootMenuPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(300, 400));
        pack();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double price = 0;

                if(DrinksGroup.getSelection() != null && SizeBtnGroup.getSelection() != null){
                    String drink = DrinksGroup.getSelection().getActionCommand();
                    String size = SizeBtnGroup.getSelection().getActionCommand();
                    switch (drink) {
                        case "americano":
                            price += 90;
                            break;
                        case "latte":
                            price += 120;
                            break;
                        default:
                            price += 130;
                            break;
                    }
                    System.out.print("1x " + size + " " + drink);
                    if(extraShotCheckBox.isSelected()){
                        System.out.print(" + Extra Shot");
                        price += 20;
                    }
                    if(syrupCheckBox.isSelected()){
                        System.out.print(" + Syrup");
                        price += 15;
                    }
                    System.out.println(" ");
                    System.out.println("Total: " + price);
                }else{
                    System.out.println("Please pick drink and size");
                }
            }
        });
    }
}
