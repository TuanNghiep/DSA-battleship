package battleship_main.ui;

import ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UIJPanelBG extends JPanel{
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

    public static ImageIcon createImageIcon(final String path) {
        InputStream is = ImageLoader.class.getResourceAsStream(path);
        int length;
        try {
            length = is.available();
            byte[] data = new byte[length];
            is.read(data);
            is.close();
            ImageIcon ii = new ImageIcon(data);
            return ii;
        } catch (IOException e) {
        }
        return null;
    }
}
