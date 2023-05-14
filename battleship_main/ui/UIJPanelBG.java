package battleship_main.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UIJPanelBG extends JPanel {
    private static final long serialVersionUID = 1L;
    Image image;

    public UIJPanelBG(String image) {
        this(UIJPanelBG.createImageIcon(image).getImage());
    }

    public UIJPanelBG(Image img) {
        this.image = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0,getWidth(), getHeight(), this);
    }

    public static ImageIcon createImageIcon(String imagePath) {
        try (InputStream is = UIJPanelBG.class.getResourceAsStream(imagePath)) {
            BufferedImage image = ImageIO.read(is);
            return new ImageIcon(image);
        } catch (IOException e) {
                e.printStackTrace();
        }
        return null;
    }
}
