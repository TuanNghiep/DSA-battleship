package battleship_main.ui;

import javax.swing.*;
import java.awt.*;

public class UIMapPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    static int X = 570;
    static int Y = 630;
    int numC = 10;
    int dimC = 48;
    int oroff = 1;
    int veroff = 1;
    int c1Off = 0;
    int c2Off = 0;
    JButton[][] button;
    JLabel[] COr;
    JLabel[] CVer;
    protected JLabel label;
    UIJPanelBG sea;
    Cursor cursorHand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

    public UIMapPanel(String etichetta) {

        this.setSize(X, Y);
        this.setLayout(null);
        this.setOpaque(false);
        // Label
        label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource(("/res/images/" + etichetta + ".png"))));
        this.add(label);
        label.setBounds(50, -20, 550, 80);
        // Panel containing boxes
        sea = new UIJPanelBG(
                Toolkit.getDefaultToolkit().createImage(FrameManageship.class.getResource("/res/images/sea.png")));
        sea.setBounds(34, 45, 550, 600);
        button = new JButton[numC][numC];
        ImageIcon gray = new ImageIcon(getClass().getResource("/res/images/grayButtonOpaque.png"));
        for (int i = 0; i < numC; i++) {
            for (int j = 0; j < numC; j++) {
                button[i][j] = new JButton(gray);
                button[i][j].setSize(dimC, dimC);
                sea.add(button[i][j]);
                button[i][j].setCursor(cursorHand);
                button[i][j].setBorder(null);
                button[i][j].setOpaque(false);
                button[i][j].setBorderPainted(false);
                button[i][j].setContentAreaFilled(false);
                button[i][j].setBounds(oroff, veroff, dimC, dimC);
                if (etichetta.equals("player")) {
                    button[i][j].setCursor(cursorDefault);
                    button[i][j].setDisabledIcon(gray);
                    button[i][j].setEnabled(false);
                } else {
                    button[i][j].setCursor(cursorHand);
                }
                oroff += dimC + 2;
            }
            veroff += dimC + 2;
            oroff = 1;
        }
        oroff = 40;
        veroff = 0;
        JPanel grid = new JPanel(null);
        grid.setOpaque(false);
        grid.add(sea);
        COr = new JLabel[10];
        CVer = new JLabel[10];
        // For that load the images of the coordinates
        for (int i = 0; i < 10; i++) {
            COr[i] = new JLabel();
            CVer[i] = new JLabel();
            grid.add(COr[i]);
            grid.add(CVer[i]);
            CVer[i].setIcon(new ImageIcon(getClass().getResource((("/res/images/coord/" + (i + 1) + ".png")))));
            CVer[i].setBounds(veroff-10, oroff+10, dimC+20, dimC);
            COr[i].setIcon(new ImageIcon(getClass().getResource((("/res/images/coord/" + (i + 11) + ".png")))));
            COr[i].setBounds(oroff-5, veroff-5, dimC+20, dimC+10);
            oroff += 50;
        }

        this.add(grid);
        grid.setBounds(0, 45, 550, 660);

    }

    void disegnaShip(int[] dati) {
        int x = dati[0];
        int y = dati[1];
        int dim = dati[2];
        int dir = dati[3];
        ImageIcon shipDim1orizz = new ImageIcon(
                getClass().getResource("/res/images/shipDim1orizz.png"));
        ImageIcon shipDim1vert = new ImageIcon(getClass().getResource("/res/images/shipDim1vert.png"));
        if (dim == 1) {
            button[x][y].setEnabled(false);
            if (dir == 0)
                button[x][y].setDisabledIcon(shipDim1orizz);
            else if (dir == 1)
                button[x][y].setDisabledIcon(shipDim1vert);
        } else if (dim == 2) {
            ImageIcon shipHeadLeft2 = new ImageIcon(getClass().getResource("/res/images/shipHeadLeft.png"));
            ImageIcon shipFootLeft2 = new ImageIcon(getClass().getResource("/res/images/shipFootLeft.png"));
            ImageIcon shipHeadTop2 = new ImageIcon(getClass().getResource("/res/images/shipHeadTop.png"));
            ImageIcon shipFootTop2 = new ImageIcon(getClass().getResource("/res/images/shipFootTop.png"));
            if (dir == 0) {
                // direzione orizzontale
                // Ship Head
                button[x][y].setDisabledIcon(shipFootLeft2);
                button[x][y].setEnabled(false);
                // Ship Body

                // Ship Foot
                button[x][y + dim - 1].setDisabledIcon(shipHeadLeft2);
                button[x][y + dim - 1].setEnabled(false);
            } else {
                // direzione verticale
                // Ship Head
                button[x][y].setDisabledIcon(shipFootTop2);
                button[x][y].setEnabled(false);

                // Ship Foot
                button[x + dim - 1][y].setDisabledIcon(shipHeadTop2);
                button[x + dim - 1][y].setEnabled(false);
            }


        } else if (dim == 3) {
            ImageIcon shipHeadLeft = new ImageIcon(getClass().getResource("/res/images/shipHeadLeft3.png"));
            ImageIcon shipBodyLeft = new ImageIcon(getClass().getResource("/res/images/shipBodyLeft3.png"));
            ImageIcon shipFootLeft = new ImageIcon(getClass().getResource("/res/images/shipFootLeft3.png"));
            ImageIcon shipHeadTop = new ImageIcon(getClass().getResource("/res/images/shipHeadTop3.png"));
            ImageIcon shipBodyTop = new ImageIcon(getClass().getResource("/res/images/shipBodyTop3.png"));
            ImageIcon shipFootTop = new ImageIcon(getClass().getResource("/res/images/shipFootTop3.png"));

            if (dir == 0) {// direzione orizzontale
                // Ship Head
                button[x][y].setDisabledIcon(shipHeadLeft);
                button[x][y].setEnabled(false);
                // Ship Body
                button[x][y + 1].setDisabledIcon(shipBodyLeft);
                button[x][y + 1].setEnabled(false);

                // Ship Foot
                button[x][y + dim - 1].setDisabledIcon(shipFootLeft);
                button[x][y + dim - 1].setEnabled(false);
            } else {
                // direzione verticale
                // Ship Head
                button[x][y].setDisabledIcon(shipHeadTop);
                button[x][y].setEnabled(false);
                // Ship Body
                button[x + 1][y].setDisabledIcon(shipBodyTop);
                button[x + 1][y].setEnabled(false);

                // Ship Foot
                button[x + dim - 1][y].setDisabledIcon(shipFootTop);
                button[x + dim - 1][y].setEnabled(false);
            }
        }
        else if (dim == 4) {
            ImageIcon shipHeadLeft4 = new ImageIcon(getClass().getResource("/res/images/shipHeadLeft4.png"));
            ImageIcon shipBodyLeft4a = new ImageIcon(getClass().getResource("/res/images/shipBodyLeft4a.png"));
            ImageIcon shipBodyLeft4b = new ImageIcon(getClass().getResource("/res/images/shipBodyLeft4b.png"));
            ImageIcon shipFootLeft4 = new ImageIcon(getClass().getResource("/res/images/shipFootLeft4.png"));
            ImageIcon shipHeadTop4 = new ImageIcon(getClass().getResource("/res/images/shipHeadTop4.png"));
            ImageIcon shipBodyTop4a = new ImageIcon(getClass().getResource("/res/images/shipBodyTop4a.png"));
            ImageIcon shipBodyTop4b = new ImageIcon(getClass().getResource("/res/images/shipBodyTop4b.png"));
            ImageIcon shipFootTop4 = new ImageIcon(getClass().getResource("/res/images/shipFootTop4.png"));

            if (dir == 0) {// direzione orizzontale
                // Ship Head
                button[x][y].setDisabledIcon(shipHeadLeft4);
                button[x][y].setEnabled(false);
                // Ship Body
                //for (int i = 1; i < dim - 1; i++) {
                button[x][y + 1].setDisabledIcon(shipBodyLeft4a);
                button[x][y + 1].setEnabled(false);
                //}

                button[x][y + 2].setDisabledIcon(shipBodyLeft4b);
                button[x][y + 2].setEnabled(false);
                // Ship Foot
                button[x][y + dim - 1].setDisabledIcon(shipFootLeft4);
                button[x][y + dim - 1].setEnabled(false);
            } else {
                // direzione verticale
                // Ship Head
                button[x][y].setDisabledIcon(shipFootTop4);
                button[x][y].setEnabled(false);
                // Ship Body
                //for (int i = 1; i < dim - 1; i++) {
                button[x + 1][y].setDisabledIcon(shipBodyTop4b);
                button[x + 1][y].setEnabled(false);
                //}

                button[x + 2][y].setDisabledIcon(shipBodyTop4a);
                button[x + 2][y].setEnabled(false);
                // Ship Foot
                button[x + dim - 1][y].setDisabledIcon(shipHeadTop4);
                button[x + dim - 1][y].setEnabled(false);
            }
        }
    }
}
