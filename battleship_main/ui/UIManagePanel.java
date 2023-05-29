package battleship_main.ui;

import javax.swing.*;
import java.awt.*;

public class UIManagePanel extends UIJPanelBG{
    private static final long serialVersionUID = 1L;
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    JRadioButtonMenuItem[] octopus;
    JLabel[] counterLabel = new JLabel[4];
    JLabel[] xLabel = new JLabel[4];
    ButtonGroup radioButtonOct;
    JRadioButton[] direction;
    JButton random;
    JButton reset;
    JButton fight;
    JButton load;
    public UIManagePanel() {

        super(Toolkit.getDefaultToolkit()
                .createImage(FrameManageOctopus.class.getResource("/res/images/managePanel.png")));
        this.setLayout(null);
        this.setOpaque(false);
        // Panel to hold the setting octopus
        JLabel managePanelLabel = new JLabel();
        managePanelLabel.setIcon(new ImageIcon(getClass().getResource("/res/images/managePanelLabel.png")));
        managePanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        managePanelLabel.setBounds(25, 30, 280, 35);
        // RadioButton to choose octopus
        octopus = new JRadioButtonMenuItem[4];
        radioButtonOct = new ButtonGroup();
        JPanel octSelect = new JPanel(null);
        octSelect.setOpaque(false);
        octSelect.setBounds(30, 90, 200, 300);
        ImageIcon oct1 = new ImageIcon(getClass().getResource("/res/images/octopus1.png"));
        ImageIcon oct2 = new ImageIcon(getClass().getResource("/res/images/octopus2.png"));
        ImageIcon oct3 = new ImageIcon(getClass().getResource("/res/images/octopus3.png"));
        ImageIcon oct4 = new ImageIcon(getClass().getResource("/res/images/octopus4.png"));
        octopus[0] = new JRadioButtonMenuItem(oct4);
        octopus[1] = new JRadioButtonMenuItem(oct3);
        octopus[2] = new JRadioButtonMenuItem(oct2);
        octopus[3] = new JRadioButtonMenuItem(oct1);
        counterLabel[0] = new JLabel("1");
        counterLabel[1] = new JLabel("2");
        counterLabel[2] = new JLabel("3");
        counterLabel[3] = new JLabel("4");
        for (int i = 0; i < octopus.length; i++) {
            octopus[i].setBounds(0+20, 10 + (i * 60), 170, 60);
            radioButtonOct.add(octopus[i]);
            octSelect.add(octopus[i]);
            octopus[i].setOpaque(false);
            counterLabel[i].setBounds(230+20, 120 + (i * 60), 23, 25);
            counterLabel[i].setOpaque(false);
            this.add(counterLabel[i]);
            xLabel[i] = new JLabel("x");
            xLabel[i].setBounds(230, 120 + (i * 60), 23, 25);
            xLabel[i].setOpaque(false);
            this.add(xLabel[i]);
        }
        octopus[0].setSelected(true);
        // RadioButton for direction selection
        // Vertical/Horizontal
        direction = new JRadioButton[2];
        ButtonGroup radioButtonDirection = new ButtonGroup();
        direction[0] = new JRadioButton("Horizontal");
        direction[0].setBounds(40, 260, 105, 20);
        radioButtonDirection.add(direction[0]);
        direction[0].setSelected(true);
        direction[0].setOpaque(false);
        octSelect.add(direction[0]);
        direction[1] = new JRadioButton("Vertical");
        direction[1].setBounds(130, 260, 105, 20);
        direction[1].setOpaque(false);
        radioButtonDirection.add(direction[1]);
        octSelect.add(direction[1]);
        // Button Random
        ImageIcon randomImg = new ImageIcon(getClass().getResource("/res/images/random.png"));
        ImageIcon randomImgOver = new ImageIcon(getClass().getResource("/res/images/randomOver.png"));
        random = new JButton(randomImg);
        random.setRolloverIcon(randomImgOver);
        random.setBorder(null);
        random.setOpaque(false);
        random.setBorderPainted(false);
        random.setContentAreaFilled(false);
        random.setBounds(60, 380, 200, 40);
        random.setCursor(cursor);
        random.setText("random");

        
        // Button Reset
        ImageIcon resetImg = new ImageIcon(getClass().getResource("/res/images/reset.png"));
        ImageIcon resetImgOver = new ImageIcon(getClass().getResource("/res/images/resetOver.png"));
        reset = new JButton(resetImg);
        reset.setRolloverIcon(resetImgOver);
        reset.setBorder(null);
        reset.setOpaque(false);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.setBounds(40, 500, 137, 102);
        reset.setCursor(cursor);
        reset.setText("reset");

        //Button Load
        ImageIcon loadImg = new ImageIcon(getClass().getResource("/res/images/load.png"));
        ImageIcon loadImgOver = new ImageIcon(getClass().getResource("/res/images/loadOver.png"));
        load = new JButton(loadImg);
        load.setRolloverIcon(loadImgOver);
        load.setBorder(null);
        load.setOpaque(false);
        load.setBorderPainted(false);
        load.setContentAreaFilled(false);
        load.setBounds(80, 600, 137, 102);
        load.setCursor(cursor);
        load.setText("load");
        load.setEnabled(true);
        
        // Button Gioca
        ImageIcon giocaImg = new ImageIcon(getClass().getResource("/res/images/gioca.png"));
        ImageIcon giocaImgOver = new ImageIcon(getClass().getResource("/res/images/giocaOver.png"));
        fight = new JButton(giocaImg);
        fight.setRolloverIcon(giocaImgOver);
        fight.setBorder(null);
        fight.setOpaque(false);
        fight.setBorderPainted(false);
        fight.setContentAreaFilled(false);
        fight.setBounds(200, 500, 137, 102);
        fight.setCursor(cursor);
        fight.setText("fight");
        fight.setEnabled(false);

        this.add(managePanelLabel);
        this.add(octSelect);
        this.add(random);
        //this.add(load);
        this.add(fight);
        this.add(reset);

    }
}
