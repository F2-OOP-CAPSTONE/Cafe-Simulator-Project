package form;

import javax.swing.*;
import java.awt.*;

public final class DayStartOverlay {
    private DayStartOverlay() {}

    public static void show(Component anchor, int day, Runnable onFinish) {
        SwingUtilities.invokeLater(() -> {
            Window owner = anchor == null ? null : SwingUtilities.getWindowAncestor(anchor);
            JWindow overlay = new JWindow(owner);
            overlay.setBackground(new Color(0, 0, 0, 180));
            overlay.setLayout(new GridBagLayout());

            JLabel label = new JLabel("Starting Day " + day);
            label.setFont(new Font("Bahnschrift", Font.BOLD, 36));
            label.setForeground(Color.WHITE);
            label.setOpaque(false);

            overlay.add(label, new GridBagConstraints());

            Dimension size = anchor != null && anchor.getSize().width > 0 && anchor.getSize().height > 0
                    ? anchor.getSize()
                    : new Dimension(960, 540);
            overlay.setSize(size);
            overlay.setLocationRelativeTo(owner);

            overlay.setAlwaysOnTop(true);
            overlay.setVisible(true);

            new Timer(1500, e -> {
                overlay.setVisible(false);
                overlay.dispose();
                if (onFinish != null) {
                    onFinish.run();
                }
            }) {{
                setRepeats(false);
            }}.start();
        });
    }
}
