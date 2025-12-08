package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ScalableLabel extends JLabel {
    private Image image;
    private int imgWidth;
    private int imgHeight;

    public ScalableLabel(String imagePath) {
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

            int labelWidth = getWidth();
            int labelHeight = getHeight();

            double labelAspectRatio = (double) labelWidth / labelHeight;
            double imageAspectRatio = (double) imgWidth / imgHeight;

            int targetWidth;
            int targetHeight;

            if (labelAspectRatio > imageAspectRatio) {
                targetHeight = labelHeight;
                targetWidth = (int) (labelHeight * imageAspectRatio);
            } else {
                targetWidth = labelWidth;
                targetHeight = (int) (labelWidth / imageAspectRatio);
            }

            int x = (labelWidth - targetWidth) / 2;
            int y = (labelHeight - targetHeight) / 2;

            g2d.drawImage(image, x, y, targetWidth, targetHeight, this);
            g2d.dispose();
        }
    }
}
