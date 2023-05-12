package battleship_main.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UIJPanelBG extends JPanel {
    private static final long serialVersionUID = 1L;
    Image immagine;

    public UIJPanelBG(String immagine) {
        this(UIJPanelBG.createImageIcon(immagine).getImage());
    }

    public UIJPanelBG(Image img) {
        this.immagine = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(immagine, 0, 0, null);
    }

    public static ImageIcon createImageIcon(String imagePath) {
        try (InputStream is = UIJPanelBG.class.getResourceAsStream(imagePath)) {
            BufferedImage image = ImageIO.read(is);
            return new ImageIcon(image);
        } catch (IOException e) {
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
