package battleship_main.ui;

import javax.swing.*;
import java.awt.*;

public class UIStatPanel extends UIJPanelBG{
    private static final long serialVersionUID = 1L;
    JLabel[] ships = new JLabel[10];

    public UIStatPanel() {
        super(Toolkit.getDefaultToolkit()
                .createImage(FrameManageship.class.getResource("/res/images/battlePaper.png")));
        for (int i = 0; i < ships.length; i++) {
            ships[i] = new JLabel();
            this.add(ships[i]);
        }
        ships[0].setIcon(new ImageIcon(getClass().getResource("/res/images/ship4.png")));
        ships[1].setIcon(new ImageIcon(getClass().getResource("/res/images/ship3.png")));
        ships[2].setIcon(new ImageIcon(getClass().getResource("/res/images/ship3.png")));
        ships[3].setIcon(new ImageIcon(getClass().getResource("/res/images/ship2.png")));
        ships[4].setIcon(new ImageIcon(getClass().getResource("/res/images/ship2.png")));
        ships[5].setIcon(new ImageIcon(getClass().getResource("/res/images/ship2.png")));
        ships[6].setIcon(new ImageIcon(getClass().getResource("/res/images/ship1.png")));
        ships[7].setIcon(new ImageIcon(getClass().getResource("/res/images/ship1.png")));
        ships[8].setIcon(new ImageIcon(getClass().getResource("/res/images/ship1.png")));
        ships[9].setIcon(new ImageIcon(getClass().getResource("/res/images/ship1.png")));

        ships[0].setBounds(25, 5, 150, 50);
        ships[1].setBounds(215, 5, 150, 50);
        ships[2].setBounds(375, 5, 150, 50);
        ships[3].setBounds(25, 60, 100, 50);
        ships[4].setBounds(110, 60, 100, 50);
        ships[5].setBounds(195, 60, 100, 50);
        ships[6].setBounds(275, 70, 60, 50);
        ships[7].setBounds(332, 70, 60, 50);
        ships[8].setBounds(389, 70, 60, 50);
        ships[9].setBounds(446, 70, 60, 50);

    }
}
