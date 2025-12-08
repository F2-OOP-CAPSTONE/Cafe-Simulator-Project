package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
        if (image != null && imgWidth > 0 && imgHeight > 0) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            int buttonWidth = getWidth();
            int buttonHeight = getHeight();

            double buttonAspectRatio = (double) buttonWidth / buttonHeight;
            double imageAspectRatio = (double) imgWidth / imgHeight;

            int targetWidth;
            int targetHeight;

            if (buttonAspectRatio > imageAspectRatio) {
                targetHeight = buttonHeight;
                targetWidth = (int) (buttonHeight * imageAspectRatio);
            } else {
                targetWidth = buttonWidth;
                targetHeight = (int) (buttonWidth / imageAspectRatio);
            }

            int x = (buttonWidth - targetWidth) / 2;
            int y = (buttonHeight - targetHeight) / 2;

            g2d.drawImage(image, x, y, targetWidth, targetHeight, this);
            g2d.dispose();
        }
    }
}
