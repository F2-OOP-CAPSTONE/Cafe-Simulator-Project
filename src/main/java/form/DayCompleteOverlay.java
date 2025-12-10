package form;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public final class DayCompleteOverlay {
    private DayCompleteOverlay() {}

    public static void show(Component anchor, String summaryHtml, Runnable onFinish) {
        SwingUtilities.invokeLater(() -> {
            Window owner = anchor == null ? null : SwingUtilities.getWindowAncestor(anchor);
            JWindow overlay = new JWindow(owner);
            overlay.setBackground(new Color(0, 0, 0, 180));
            overlay.setLayout(new GridBagLayout());

            Color wood = new Color(0x6F4F28);
            Color parchment = new Color(0xF8F1E0);

            JPanel card = new JPanel();
            card.setOpaque(true);
            card.setBackground(new Color(parchment.getRed(), parchment.getGreen(), parchment.getBlue(), 230));
            card.setBorder(new CompoundBorder(new LineBorder(wood, 3, true), new EmptyBorder(18, 18, 18, 18)));
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

            JLabel title = new JLabel("Day Complete");
            title.setFont(new Font("Bahnschrift", Font.BOLD, 32));
            title.setForeground(new Color(0x2E3138));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel summary = new JLabel(summaryHtml);
            summary.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
            summary.setForeground(new Color(0x2E3138));
            summary.setAlignmentX(Component.CENTER_ALIGNMENT);
            summary.setBorder(new EmptyBorder(12, 0, 0, 0));

            card.add(title);
            card.add(summary);

            overlay.add(card, new GridBagConstraints());

            Dimension size = anchor != null && anchor.getSize().width > 0 && anchor.getSize().height > 0
                    ? anchor.getSize()
                    : new Dimension(960, 540);
            overlay.setSize(size);
            overlay.setLocationRelativeTo(owner);

            overlay.setAlwaysOnTop(true);
            overlay.setVisible(true);

            new Timer(1800, e -> {
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
