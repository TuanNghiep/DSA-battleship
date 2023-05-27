package battleship_main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;


public class FrameManageOctopus extends JFrame implements ActionListener, KeyListener{
    private static final long serialVersionUID = 2923975805665801740L;
    private static final int NUM_OCT = 10;
    LinkedList<int[]> playerOctopus;// contains the inserted optopus, is for
    // build the frameBattle
    boolean finish = false;
    int insertOct = 0;
    int[] counterOct = { 1, 2, 3, 4 };
    Mappa mappa;
    UIManagePanel choosePan;
    UIMapPanel mapPanel;

    public FrameManageOctopus() {
        super("Octopus Battleship");
        mappa = new Mappa();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        //Icon of the game
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/icon.png")));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        //Background of the game
        UIJPanelBG container = new UIJPanelBG(
                Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/ocean.png")));
        mapPanel = new UIMapPanel("manage");
        container.add(mapPanel);
        choosePan = new UIManagePanel();
        container.add(choosePan);
        mapPanel.setBounds((screenSize.width - 600) / 2, (screenSize.height - 700) / 2, 535, 592);
        choosePan.setBounds(mapPanel.getX() + mapPanel.getWidth() + 50, 0, 350, 820);
        // Internal panel containing the octopus to be placed.
        this.add(container);
        for (int i = 0; i < mapPanel.button.length; i++) {
            for (int j = 0; j < mapPanel.button[i].length; j++) {
                mapPanel.button[i][j].addActionListener(this);
                mapPanel.button[i][j].setActionCommand("" + i + " " + j);
            }
        }
        choosePan.random.addActionListener(this);
        choosePan.reset.addActionListener(this);
        choosePan.fight.addActionListener(this);
        playerOctopus = new LinkedList<int[]>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String testo = source.getText();
        // RESET
        if (testo.equals("reset")) {
            reset();
        }
        // RANDOM
        else if (testo.equals("random")) {
            random();
        }
        // GIOCA
        else if (testo.equals("fight")) {
            gioca();

        } else {
            if (finish) {
                return;
            }
            StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int nave = -1;
            int dim = 0;
            int dir;
            for (int i = 0; i < choosePan.octopus.length; i++) {
                if (choosePan.octopus[i].isSelected())
                    nave = i;
            }
            switch (nave) {
                case 0:
                    dim = 4;
                    break;
                case 1:
                    dim = 3;
                    break;
                case 2:
                    dim = 2;
                    break;
                case 3:
                    dim = 1;
                    break;
            }
            if (choosePan.direction[0].isSelected())// 0=horizontal 1=vertical
                dir = 0;
            else
                dir = 1;
            boolean inserito = mappa.insertOct(x, y, dim, dir);
            if (inserito) {
                
                // increment the number of inserted optopus
                insertOct++;
                // decrease the number of inserted optopus
                counterOct[nave]--;
                choosePan.counterLabel[nave].setText("" + counterOct[nave]);
                
                // disable octopus if all are entered
                if (choosePan.counterLabel[nave].getText().equals("0")) {
                    choosePan.octopus[nave].setEnabled(false);
                    for (int i = 0; i < choosePan.octopus.length; i++) {
                        if (choosePan.octopus[i].isEnabled() && !choosePan.octopus[i].isSelected()) {
                            choosePan.octopus[i].setSelected(true);
                            break;
                        }
                    }
                }
                // check if we have entered all optopus (10)
                if (insertOct == NUM_OCT) {
                    finish = true;
                    choosePan.direction[0].setEnabled(false);
                    choosePan.direction[1].setEnabled(false);
                    choosePan.fight.setEnabled(true);
                }
                int[] dati = { x, y, dim, dir };
                playerOctopus.add(dati);
                mapPanel.drawOct(dati);
            }
        }
        this.requestFocusInWindow();
    }

    private void random() {
        if (insertOct == NUM_OCT) {
            reset();
        }
        Random r = new Random();
        int[] dati = new int[4];
        for (int i = 0; i < counterOct.length; i++) {
            for (int j = 0; j < counterOct[i]; j++) {
                dati = mappa.insertOctRandom(r, counterOct.length - i);
                playerOctopus.add(dati);
                mapPanel.drawOct(dati);
            }
        }
        insertOct = NUM_OCT;
        finish = true;
        choosePan.fight.setEnabled(true);
        for (int i = 0; i < choosePan.octopus.length; i++) {
            choosePan.octopus[i].setEnabled(false);
        }
        choosePan.direction[0].setEnabled(false);
        choosePan.direction[1].setEnabled(false);
        for (int i = 0; i < counterOct.length; i++) {
            counterOct[i] = 0;
            choosePan.counterLabel[i].setText("0");
        }
        choosePan.octopus[0].setSelected(true);

    }

    private void reset() {
        mappa = new Mappa();
        playerOctopus = new LinkedList<int[]>();
        for (int i = 0; i < Mappa.DIM_MAPPA; i++) {
            for (int j = 0; j < Mappa.DIM_MAPPA; j++) {
                mapPanel.button[i][j].setEnabled(true);
            }
        }
        finish = false;
        choosePan.fight.setEnabled(false);
        for (int i = 0; i < choosePan.octopus.length; i++) {
            choosePan.octopus[i].setEnabled(true);
        }
        choosePan.direction[0].setEnabled(true);
        choosePan.direction[1].setEnabled(true);
        for (int i = 0; i < counterOct.length; i++) {
            counterOct[i] = i + 1;
            choosePan.counterLabel[i].setText("" + (i + 1));
        }
        choosePan.octopus[0].setSelected(true);
        insertOct = 0;
    }

    private void gioca() {
        FrameBattle battle = new FrameBattle(playerOctopus, mappa);
        battle.frame.setVisible(true);
        this.setVisible(false);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        char s = Character.toLowerCase(arg0.getKeyChar());
        int tasto = arg0.getKeyCode();
        if (s == 'g') {

            random();
            gioca();
        } else {
            if (s == 'r') {
                random();
            } else {
                if (tasto == KeyEvent.VK_DELETE || tasto == KeyEvent.VK_BACK_SPACE) {
                    reset();
                } else {
                    if (tasto == KeyEvent.VK_ESCAPE) {
                        System.exit(0);
                    }
                }
                if (tasto == KeyEvent.VK_ENTER) {
                    if (finish) {
                        gioca();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
}
