package main.java;

import main.java.form.TakeOrderGui;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TakeOrderGui GUI = new TakeOrderGui();
                GUI.setVisible(true);

            }
        });
    }
}
