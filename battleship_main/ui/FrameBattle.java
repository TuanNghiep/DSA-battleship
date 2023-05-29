package battleship_main.ui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

//Battle screen
public class FrameBattle implements ActionListener, KeyListener, Serializable {
    UIMapPanel playerPanel = new UIMapPanel("player");
    UIMapPanel cpuPanel = new UIMapPanel("cpu");
    JFrame frame = new JFrame("Battleship Map");
    JPanel comandPanel = new JPanel();
    Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    UIJPanelBG panel = new UIJPanelBG(
            Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/ocean.png")));
    Report rep;
    Computer cpu;
    static Mappa cpuMap;
    static Mappa playerMap;
    int numOctPlayer = 10;
    int numOctCPU = 10;
    StringBuilder sb = new StringBuilder();
    boolean b = true;

    UIStatPanel statPlayer;
    UIStatPanel statCPU;
    JPanel targetPanel = new JPanel(null);
    UIJPanelBG target = new UIJPanelBG(
            Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/target.png")));
    ImageIcon wreck = new ImageIcon(getClass().getResource("/res/images/wreck.gif"));
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    Timer timer;
    boolean defeatCPU;

    int offsetX;
    static LinkedList<int[]> playerOctopus;// contains the inserted optopus, is for
    
    public FrameBattle(LinkedList<int[]> playerOctopus, Mappa mappa) {
        // Add this at the beginning of the constructor
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/res/images/back.png"));
        JLabel backLabel = new JLabel(backIcon);
        ImageIcon SaveIcon = new ImageIcon(getClass().getResource("/res/images/back.png"));
        JLabel SaveLabel = new JLabel(SaveIcon);
        


        backLabel.setBounds(10, 10, backIcon.getIconWidth(), backIcon.getIconHeight());
        SaveLabel.setBounds(10, 200, SaveIcon.getIconWidth(), SaveIcon.getIconHeight());
        //panel.add(SaveLabel);
        panel.add(backLabel);

        
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleBackClick();
            }
        });

        SaveLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleSaveClick(playerMap,cpuMap);
            }
        });


        playerMap = mappa;
        cpu = new Computer(mappa);
        cpuMap = new Mappa();
        cpuMap.initializeRandomMap();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        offsetX = (frame.getWidth() - (2 * UIMapPanel.X + 30)) / 2;

        frame.setTitle("Octopus Battle");
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.addKeyListener(this);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/icon.png")));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Panel containing the octopus to delete
        statPlayer = new UIStatPanel();
        statCPU = new UIStatPanel();

        statPlayer.setBounds(30 + offsetX, 670, 520, 150);
        statCPU.setBounds(600 + offsetX, 670, 520, 150);

        panel.add(statPlayer);
        panel.add(statCPU);

        // Target Panel
        targetPanel.setBounds(0, 0, 500, 500);
        targetPanel.setOpaque(false);
        playerPanel.sea.add(targetPanel);

        panel.add(playerPanel);
        playerPanel.setBounds(offsetX + 15, 50, 535, 592);
        playerPanel.setOpaque(false);

        panel.add(cpuPanel);
        cpuPanel.setBounds(590 + offsetX, 50, 535, 592);
        panel.add(comandPanel);

        frame.add(panel);
        frame.setResizable(false);
        timer = new Timer(2000, new TimeController());
        defeatCPU = false;

        for (int i = 0; i < cpuPanel.button.length; i++) {
            for (int j = 0; j < cpuPanel.button[i].length; j++) {
                cpuPanel.button[i][j].addActionListener(this);
                cpuPanel.button[i][j].setActionCommand("" + i + " " + j);
            }
        }
        for (int[] v : playerOctopus) {
            playerPanel.drawOct(v);
        }
        
    }

    void setAttack(Report rep, boolean player) {
        int x = rep.getP().getCoordX();
        int y = rep.getP().getCoordY();
        ImageIcon fire = new ImageIcon(getClass().getResource("/res/images/fireButton.gif"));
        ImageIcon water = new ImageIcon(getClass().getResource("/res/images/grayButton.gif"));
        String target;
        if (rep.isHit())
            target = "X";
        else
            target = "A";
        UIMapPanel mappanel;
        if (!player) {
            mappanel = playerPanel;
        } else {
            mappanel = cpuPanel;
        }
        if (target == "X") {
            mappanel.button[x][y].setIcon(fire);
            mappanel.button[x][y].setEnabled(false);
            mappanel.button[x][y].setDisabledIcon(fire);
            mappanel.button[x][y].setCursor(cursorDefault);
        } else {
            mappanel.button[x][y].setIcon(water);
            mappanel.button[x][y].setEnabled(false);
            mappanel.button[x][y].setDisabledIcon(water);
            mappanel.button[x][y].setCursor(cursorDefault);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (defeatCPU)
            return;
        JButton source = (JButton) e.getSource();
        StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        Position newP = new Position(x, y);
        boolean attack = cpuMap.hitt(newP);
        Report rep = new Report(newP, attack, false);
        this.setAttack(rep, true);
        if (attack) { // Player continue to play
            OctPos deadSquid = cpuMap.sunk(newP);
            if (deadSquid != null) {
                numOctCPU--;
                setDeadSquid(deadSquid);
                if (numOctCPU == 0) {
                    Object[] options = {"New Game", "Escape"};
                    int n = JOptionPane.showOptionDialog(frame, (new JLabel("You win!", JLabel.CENTER)),
                            "End game", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                            options[1]);
                    if (n == 0) {
                        FrameManageOctopus restart = new FrameManageOctopus();
                        restart.setVisible(true);
                        this.frame.setVisible(false);
                    } else {
                        System.exit(0);
                    }
                }
            }
        } else {

            if (b) {
                timer.start();
                defeatCPU = true;
            }
        }
        frame.requestFocusInWindow();
    }

    private void setDeadSquid(Position p) {
        LinkedList<String> possibility = new LinkedList<String>();
        if (p.getCoordX() != 0) { // Coordinates
            possibility.add("N");
        }
        if (p.getCoordX() != Mappa.DIM_MAPPA - 1) {
            possibility.add("S");
        }
        if (p.getCoordY() != 0) {
            possibility.add("O");
        }
        if (p.getCoordY() != Mappa.DIM_MAPPA - 1) {
            possibility.add("E");
        }
        String direction;
        boolean findSquid = false;
        Position currentPos;
        do {
            currentPos = new Position(p);
            if (possibility.isEmpty()) {
                deleteSquid(1, statPlayer);
                playerPanel.button[currentPos.getCoordX()][currentPos.getCoordY()].setIcon(wreck);
                playerPanel.button[currentPos.getCoordX()][currentPos.getCoordY()].setEnabled(false);
                playerPanel.button[currentPos.getCoordX()][currentPos.getCoordY()].setDisabledIcon(wreck);
                playerPanel.button[currentPos.getCoordX()][currentPos.getCoordY()].setCursor(cursorDefault);
                return;
            }
            direction = possibility.removeFirst();
            currentPos.move(direction.charAt(0));
            if (playerMap.hit(currentPos)) {
                findSquid = true;
            }
        } while (!findSquid);
        int dim = 0;
        currentPos = new Position(p);
        do {

            playerPanel.button[currentPos.getCoordX()][currentPos.getCoordY()].setIcon(wreck);
            playerPanel.button[currentPos.getCoordX()][currentPos.getCoordY()].setEnabled(false);
            playerPanel.button[currentPos.getCoordX()][currentPos.getCoordY()].setDisabledIcon(wreck);
            playerPanel.button[currentPos.getCoordX()][currentPos.getCoordY()].setCursor(cursorDefault);
            currentPos.move(direction.charAt(0));

            dim++;
        } while (currentPos.getCoordX() >= 0 && currentPos.getCoordX() <= 9 && currentPos.getCoordY() >= 0
                && currentPos.getCoordY() <= 9 && !playerMap.acqua(currentPos));

        deleteSquid(dim, statPlayer);
    }

    private void setDeadSquid(OctPos deadOctopus) {
        int dim = 0;
        for (int i = deadOctopus.getXin(); i <= deadOctopus.getXfin(); i++) {
            for (int j = deadOctopus.getYin(); j <= deadOctopus.getYfin(); j++) {
                cpuPanel.button[i][j].setIcon(wreck);
                cpuPanel.button[i][j].setEnabled(false);
                cpuPanel.button[i][j].setDisabledIcon(wreck);
                cpuPanel.button[i][j].setCursor(cursorDefault);
                dim++;
            }
        }
        deleteSquid(dim, statCPU);
    }

    private void deleteSquid(int dim, UIStatPanel panel) {
        switch (dim) {
            case 4:
                panel.optopus[0].setEnabled(false);
                break;
            case 3:
                if (!panel.optopus[1].isEnabled())
                    panel.optopus[2].setEnabled(false);
                else
                    panel.optopus[1].setEnabled(false);
                break;
            case 2:
                if (!panel.optopus[3].isEnabled())
                    if (!panel.optopus[4].isEnabled())
                        panel.optopus[5].setEnabled(false);
                    else
                        panel.optopus[4].setEnabled(false);
                else
                    panel.optopus[3].setEnabled(false);
                break;
            case 1:
                if (!panel.optopus[6].isEnabled())
                    if (!panel.optopus[7].isEnabled())
                        if (!panel.optopus[8].isEnabled())
                            panel.optopus[9].setEnabled(false);
                        else
                            panel.optopus[8].setEnabled(false);
                    else
                        panel.optopus[7].setEnabled(false);
                else
                    panel.optopus[6].setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        int tasto = arg0.getKeyCode();
        if (tasto == KeyEvent.VK_ESCAPE) {
            FrameManageOctopus manage = new FrameManageOctopus();
            manage.setVisible(true);
            frame.setVisible(false);
        }

        sb.append(arg0.getKeyChar());
        if (sb.length() == 4) {
            int z = sb.toString().hashCode();
            if (z == 3194657) {
                sb = new StringBuilder();
                b = !b;
            } else {
                String s = sb.substring(1, 4);
                sb = new StringBuilder(s);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    public class TimeController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            timer.stop();
            boolean flag;

            Report report = cpu.nextTurn();
            drawTarget(report.getP().getCoordX() * 50, report.getP().getCoordY() * 50);
            flag = report.isHit();
            setAttack(report, false);
            if (report.isDead()) {
                numOctPlayer--;
                setDeadSquid(report.getP());
                if (numOctPlayer == 0) {
                    Object[] options = {"New game", "Escape"};
                    int n = JOptionPane.showOptionDialog(frame, (new JLabel("You Lost!", JLabel.CENTER)),
                            "End game", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                            options[1]);
                    if (n == 0) {
                        FrameManageOctopus restart = new FrameManageOctopus();
                        restart.setVisible(true);
                        frame.setVisible(false);
                    } else {
                        System.exit(0);
                    }
                }
            }

            defeatCPU = false;
            if (flag) {
                timer.start();
                defeatCPU = true;
            }
            frame.requestFocusInWindow();
        }

    }

    public void drawTarget(int i, int j) {
        target.setBounds(j, i, 50, 50);
        target.setVisible(true);
        targetPanel.add(target);
        targetPanel.repaint();
    }

    private void handleSaveClick(Mappa playerMappa,Mappa cpuMap) {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to save the game?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("playerOctopus.dat"));
                oos.writeObject(playerMap);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mappa.dat"));
                oos.writeObject(cpuMap);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    void drawLoad(){
        ImageIcon fire = new ImageIcon(getClass().getResource("/res/images/fireButton.gif"));
        ImageIcon water = new ImageIcon(getClass().getResource("/res/images/grayButton.gif"));
		for (int i = 0; i < Mappa.DIM_MAPPA; i++) {
			for (int j = 0; j < Mappa.DIM_MAPPA; j++) {
				
			}
		}

    }
    static void load(){
        //Load the game from mappa.dat and playerOctopus.dat
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("playerOctopus.dat"));
            playerMap = (Mappa) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("mappa.dat"));
            cpuMap = (Mappa) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        

    }
    private void handleBackClick() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to stop the game?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            frame.dispose();
            // Open FrameManageOctopus here
        }
    }
}
