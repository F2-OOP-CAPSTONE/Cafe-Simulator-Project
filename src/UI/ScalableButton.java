package UI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ScalableButton extends JButton {
    private Image image;
    private int imgWidth;
    private int imgHeight;

    public ScalableButton(String imagePath) {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        try {
            File imgFile = new File(imagePath);
            this.image = ImageIO.read(imgFile);
            this.imgWidth = this.image.getWidth(null);
            this.imgHeight = this.image.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
            setText("Image Not Found");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (image != null) {
            return new Dimension(imgWidth, imgHeight);
        }
        return super.getPreferredSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Only try to draw if the image loaded successfully
        if (image != null && imgWidth > 0 && imgHeight > 0) {
            Graphics2D g2d = (Graphics2D) g.create();

            // 1. Set rendering hint for crisp pixel art scaling
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            // 2. Get current button dimensions
            int buttonWidth = getWidth();
            int buttonHeight = getHeight();

            // 3. Calculate the aspect ratios
            double buttonAspectRatio = (double) buttonWidth / buttonHeight;
            double imageAspectRatio = (double) imgWidth / imgHeight;

            int targetWidth, targetHeight;

            // 4. Determine dimensions to preserve aspect ratio
            if (buttonAspectRatio > imageAspectRatio) {
                // Button is wider than the image; constrain by height
                targetHeight = buttonHeight;
                targetWidth = (int) (buttonHeight * imageAspectRatio);
            } else {
                // Button is taller than the image; constrain by width
                targetWidth = buttonWidth;
                targetHeight = (int) (buttonWidth / imageAspectRatio);
            }

            // 5. Calculate positions to center the image
            int x = (buttonWidth - targetWidth) / 2;
            int y = (buttonHeight - targetHeight) / 2;

            // 6. Draw the image with the calculated proportional dimensions and centered position
            g2d.drawImage(image, x, y, targetWidth, targetHeight, this);

            g2d.dispose(); // Clean up graphics object
        }
        // Do not call super.paintComponent(g) to avoid default button look
    }
}