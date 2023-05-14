package battleship_main.ui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

//Battle screen
public class FrameBattle implements ActionListener, KeyListener {
    UIMapPanel playerPanel = new UIMapPanel("player");
    UIMapPanel cpuPanel = new UIMapPanel("cpu");

    JFrame frame = new JFrame("Battleship Map");
    JPanel comandPanel = new JPanel();
    Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    UIJPanelBG panel = new UIJPanelBG(
            Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/wood.png")));
    Report rep;
    Computer cpu;
    Mappa cpuMap;
    Mappa playerMap;
    int numNaviPlayer = 10;
    int numNaviCPU = 10;
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
    boolean turnoDelCPU;

    int offsetX;

    public FrameBattle(LinkedList<int[]> playerShips, Mappa mappa) {
        // Add this at the beginning of the constructor
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/res/images/back.png"));
        JLabel backLabel = new JLabel(backIcon);
        backLabel.setBounds(10, 10, backIcon.getIconWidth(), backIcon.getIconHeight());
        panel.add(backLabel);

        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleBackClick();
            }
        });

        playerMap = mappa;
        cpu = new Computer(mappa);
        cpuMap = new Mappa();
        cpuMap.riempiMappaRandom();
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


        // Panel containing the ships to delete
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
        timer = new Timer(2000, new GestoreTimer());
        turnoDelCPU = false;

        for (int i = 0; i < cpuPanel.button.length; i++) {
            for (int j = 0; j < cpuPanel.button[i].length; j++) {
                cpuPanel.button[i][j].addActionListener(this);
                cpuPanel.button[i][j].setActionCommand("" + i + " " + j);
            }
        }
        for (int[] v : playerShips) {
            playerPanel.disegnaShip(v);
        }

    }

    void setCasella(Report rep, boolean player) {
        int x = rep.getP().getCoordX();
        int y = rep.getP().getCoordY();
        ImageIcon fire = new ImageIcon(getClass().getResource("/res/images/fireButton.gif"));
        ImageIcon water = new ImageIcon(getClass().getResource("/res/images/grayButton.gif"));
        String cosa;
        if (rep.isHit())
            cosa = "X";
        else
            cosa = "A";
        UIMapPanel mappanel;
        if (!player) {
            mappanel = playerPanel;
        } else {
            mappanel = cpuPanel;
        }
        if (cosa == "X") {
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
        if (turnoDelCPU)
            return;
        JButton source = (JButton) e.getSource();
        StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        Position newP = new Position(x, y);
        boolean colpito = cpuMap.hitt(newP);
        Report rep = new Report(newP, colpito, false);
        this.setCasella(rep, true);
        if (colpito) { // continua a giocare il player
            ShipPos naveAffondata = cpuMap.sunk(newP);
            if (naveAffondata != null) {
                numNaviCPU--;
                setAffondato(naveAffondata);
                if (numNaviCPU == 0) {
                    Object[] options = {"Nuova Partita", "Esci"};
                    int n = JOptionPane.showOptionDialog(frame, (new JLabel("Hai Vinto!", JLabel.CENTER)),
                            "Partita Terminata", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                            options[1]);
                    if (n == 0) {
                        FrameManageship restart = new FrameManageship();
                        restart.setVisible(true);
                        this.frame.setVisible(false);
                    } else {
                        System.exit(0);
                    }
                }
            }
        } else { // tocca al CPU

            if (b) {
                timer.start();
                turnoDelCPU = true;
            }
        }
        frame.requestFocusInWindow();
    }

    private void setAffondato(Position p) {
        LinkedList<String> possibilita = new LinkedList<String>();
        if (p.getCoordX() != 0) {
            possibilita.add("N");
        }
        if (p.getCoordX() != Mappa.DIM_MAPPA - 1) {
            possibilita.add("S");
        }
        if (p.getCoordY() != 0) {
            possibilita.add("O");
        }
        if (p.getCoordY() != Mappa.DIM_MAPPA - 1) {
            possibilita.add("E");
        }
        String direzione;
        boolean trovato = false;
        Position posAttuale;
        do {
            posAttuale = new Position(p);
            if (possibilita.isEmpty()) {
                deleteShip(1, statPlayer);
                playerPanel.button[posAttuale.getCoordX()][posAttuale.getCoordY()].setIcon(wreck);
                playerPanel.button[posAttuale.getCoordX()][posAttuale.getCoordY()].setEnabled(false);
                playerPanel.button[posAttuale.getCoordX()][posAttuale.getCoordY()].setDisabledIcon(wreck);
                playerPanel.button[posAttuale.getCoordX()][posAttuale.getCoordY()].setCursor(cursorDefault);
                return;
            }
            direzione = possibilita.removeFirst();
            posAttuale.sposta(direzione.charAt(0));
            if (playerMap.hitt(posAttuale)) {
                trovato = true;
            }
        } while (!trovato);
        int dim = 0;
        posAttuale = new Position(p);
        do {

            playerPanel.button[posAttuale.getCoordX()][posAttuale.getCoordY()].setIcon(wreck);
            playerPanel.button[posAttuale.getCoordX()][posAttuale.getCoordY()].setEnabled(false);
            playerPanel.button[posAttuale.getCoordX()][posAttuale.getCoordY()].setDisabledIcon(wreck);
            playerPanel.button[posAttuale.getCoordX()][posAttuale.getCoordY()].setCursor(cursorDefault);
            posAttuale.sposta(direzione.charAt(0));

            dim++;
        } while (posAttuale.getCoordX() >= 0 && posAttuale.getCoordX() <= 9 && posAttuale.getCoordY() >= 0
                && posAttuale.getCoordY() <= 9 && !playerMap.acqua(posAttuale));

        deleteShip(dim, statPlayer);
    }

    private void setAffondato(ShipPos naveAffondata) {
        int dim = 0;
        for (int i = naveAffondata.getXin(); i <= naveAffondata.getXfin(); i++) {
            for (int j = naveAffondata.getYin(); j <= naveAffondata.getYfin(); j++) {
                cpuPanel.button[i][j].setIcon(wreck);
                cpuPanel.button[i][j].setEnabled(false);
                cpuPanel.button[i][j].setDisabledIcon(wreck);
                cpuPanel.button[i][j].setCursor(cursorDefault);
                dim++;
            }
        }
        deleteShip(dim, statCPU);
    }

    private void deleteShip(int dim, UIStatPanel panel) {
        switch (dim) {
            case 4:
                panel.ships[0].setEnabled(false);
                break;
            case 3:
                if (!panel.ships[1].isEnabled())
                    panel.ships[2].setEnabled(false);
                else
                    panel.ships[1].setEnabled(false);
                break;
            case 2:
                if (!panel.ships[3].isEnabled())
                    if (!panel.ships[4].isEnabled())
                        panel.ships[5].setEnabled(false);
                    else
                        panel.ships[4].setEnabled(false);
                else
                    panel.ships[3].setEnabled(false);
                break;
            case 1:
                if (!panel.ships[6].isEnabled())
                    if (!panel.ships[7].isEnabled())
                        if (!panel.ships[8].isEnabled())
                            panel.ships[9].setEnabled(false);
                        else
                            panel.ships[8].setEnabled(false);
                    else
                        panel.ships[7].setEnabled(false);
                else
                    panel.ships[6].setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        int tasto = arg0.getKeyCode();
        if (tasto == KeyEvent.VK_ESCAPE) {
            FrameManageship manage = new FrameManageship();
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

    public class GestoreTimer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            timer.stop();
            boolean flag;

            Report report = cpu.mioTurno();
            disegnaTarget(report.getP().getCoordX() * 50, report.getP().getCoordY() * 50);
            flag = report.isHit();
            setCasella(report, false);
            if (report.isSunk()) {
                numNaviPlayer--;
                setAffondato(report.getP());
                if (numNaviPlayer == 0) {
                    Object[] options = {"New game", "Escape"};
                    int n = JOptionPane.showOptionDialog(frame, (new JLabel("You Lost!", JLabel.CENTER)),
                            "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                            options[1]);
                    if (n == 0) {
                        FrameManageship restart = new FrameManageship();
                        restart.setVisible(true);
                        frame.setVisible(false);
                    } else {
                        System.exit(0);
                    }
                }
            }

            turnoDelCPU = false;
            if (flag) {
                timer.start();
                turnoDelCPU = true;
            }
            frame.requestFocusInWindow();
        }

    }

    public void disegnaTarget(int i, int j) {
        target.setBounds(j, i, 50, 50);
        target.setVisible(true);
        targetPanel.add(target);
        targetPanel.repaint();
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
            // Open FrameManageship here
        }
    }
}
