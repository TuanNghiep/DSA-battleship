package battleship_main.ui;

import javax.swing.*;
import java.awt.*;

public class UIStatPanel extends UIJPanelBG{
    private static final long serialVersionUID = 1L;
    JLabel[] optopus = new JLabel[10];

    public UIStatPanel() {
        super(Toolkit.getDefaultToolkit()
                .createImage(FrameManageOctopus.class.getResource("/res/images/battlePaper.png")));
        for (int i = 0; i < optopus.length; i++) {
            optopus[i] = new JLabel();
            this.add(optopus[i]);
        }
        optopus[0].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus4.png")));
        optopus[1].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus3.png")));
        optopus[2].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus3.png")));
        optopus[3].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus2.png")));
        optopus[4].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus2.png")));
        optopus[5].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus2.png")));
        optopus[6].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus1.png")));
        optopus[7].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus1.png")));
        optopus[8].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus1.png")));
        optopus[9].setIcon(new ImageIcon(getClass().getResource("/res/images/octopus1.png")));

        optopus[0].setBounds(25, 5, 150, 50);
        optopus[1].setBounds(215, 5, 150, 50);
        optopus[2].setBounds(375, 5, 150, 50);
        optopus[3].setBounds(25, 60, 100, 50);
        optopus[4].setBounds(110, 60, 100, 50);
        optopus[5].setBounds(195, 60, 100, 50);
        optopus[6].setBounds(275, 70, 60, 50);
        optopus[7].setBounds(332, 70, 60, 50);
        optopus[8].setBounds(389, 70, 60, 50);
        optopus[9].setBounds(446, 70, 60, 50);

    }
}
