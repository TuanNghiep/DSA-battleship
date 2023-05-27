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
                Toolkit.getDefaultToolkit().createImage(FrameManageOctopus.class.getResource("/res/images/sea.png")));
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

    void drawOct(int[] dati) {
        int x = dati[0];
        int y = dati[1];
        int dim = dati[2];
        int dir = dati[3];
        ImageIcon octDim1orizz = new ImageIcon(
                getClass().getResource("/res/images/octopusDim1orizz.png"));
        ImageIcon octDim1vert = new ImageIcon(getClass().getResource("/res/images/octopusDim1vert.png"));
        if (dim == 1) {
            button[x][y].setEnabled(false);
            if (dir == 0)
                button[x][y].setDisabledIcon(octDim1orizz);
            else if (dir == 1)
                button[x][y].setDisabledIcon(octDim1vert);
        } else if (dim == 2) {
            ImageIcon octHeadLeft2 = new ImageIcon(getClass().getResource("/res/images/octopusHeadLeft.png"));
            ImageIcon octFootLeft2 = new ImageIcon(getClass().getResource("/res/images/octopusFootLeft.png"));
            ImageIcon octHeadTop2 = new ImageIcon(getClass().getResource("/res/images/octopusHeadTop.png"));
            ImageIcon octFootTop2 = new ImageIcon(getClass().getResource("/res/images/octopusFootTop.png"));
            if (dir == 0) {
                // Horizontal Direction
                // Octopus Head
                button[x][y].setDisabledIcon(octFootLeft2);
                button[x][y].setEnabled(false);
                // Octopus Body

                // Octopus Foot
                button[x][y + dim - 1].setDisabledIcon(octHeadLeft2);
                button[x][y + dim - 1].setEnabled(false);
            } else {
                // Vertical Direction
                // Octopus Head
                button[x][y].setDisabledIcon(octFootTop2);
                button[x][y].setEnabled(false);

                // Octopus Foot
                button[x + dim - 1][y].setDisabledIcon(octHeadTop2);
                button[x + dim - 1][y].setEnabled(false);
            }


        } else if (dim == 3) {
            ImageIcon octHeadLeft = new ImageIcon(getClass().getResource("/res/images/octopusHeadLeft3.png"));
            ImageIcon octBodyLeft = new ImageIcon(getClass().getResource("/res/images/octopusBodyLeft3.png"));
            ImageIcon octFootLeft = new ImageIcon(getClass().getResource("/res/images/octopusFootLeft3.png"));
            ImageIcon octHeadTop = new ImageIcon(getClass().getResource("/res/images/octopusHeadTop3.png"));
            ImageIcon octBodyTop = new ImageIcon(getClass().getResource("/res/images/octopusBodyTop3.png"));
            ImageIcon octFootTop = new ImageIcon(getClass().getResource("/res/images/octopusFootTop3.png"));

            if (dir == 0) {// Horizontal Direction
                // Octopus Head
                button[x][y].setDisabledIcon(octHeadLeft);
                button[x][y].setEnabled(false);
                // Octopus Body
                button[x][y + 1].setDisabledIcon(octBodyLeft);
                button[x][y + 1].setEnabled(false);

                // Octopus Foot
                button[x][y + dim - 1].setDisabledIcon(octFootLeft);
                button[x][y + dim - 1].setEnabled(false);
            } else {
                // Vertical Direction
                // Octopus Head
                button[x][y].setDisabledIcon(octHeadTop);
                button[x][y].setEnabled(false);
                // Octopus Body
                button[x + 1][y].setDisabledIcon(octBodyTop);
                button[x + 1][y].setEnabled(false);

                // Octopus Foot
                button[x + dim - 1][y].setDisabledIcon(octFootTop);
                button[x + dim - 1][y].setEnabled(false);
            }
        }
        else if (dim == 4) {
            ImageIcon octHeadLeft4 = new ImageIcon(getClass().getResource("/res/images/octopusHeadLeft4.png"));
            ImageIcon octBodyLeft4a = new ImageIcon(getClass().getResource("/res/images/octopusBodyLeft4a.png"));
            ImageIcon octBodyLeft4b = new ImageIcon(getClass().getResource("/res/images/octopusBodyLeft4b.png"));
            ImageIcon octFootLeft4 = new ImageIcon(getClass().getResource("/res/images/octopusFootLeft4.png"));
            ImageIcon octHeadTop4 = new ImageIcon(getClass().getResource("/res/images/octopusHeadTop4.png"));
            ImageIcon octBodyTop4a = new ImageIcon(getClass().getResource("/res/images/octopusBodyTop4a.png"));
            ImageIcon octBodyTop4b = new ImageIcon(getClass().getResource("/res/images/octopusBodyTop4b.png"));
            ImageIcon octFootTop4 = new ImageIcon(getClass().getResource("/res/images/octopusFootTop4.png"));

            if (dir == 0) {// Horizontal Direction
                // Octopus Head
                button[x][y].setDisabledIcon(octHeadLeft4);
                button[x][y].setEnabled(false);

                button[x][y + 1].setDisabledIcon(octBodyLeft4a);
                button[x][y + 1].setEnabled(false);

                button[x][y + 2].setDisabledIcon(octBodyLeft4b);
                button[x][y + 2].setEnabled(false);
                // Octopus Foot
                button[x][y + dim - 1].setDisabledIcon(octFootLeft4);
                button[x][y + dim - 1].setEnabled(false);
            } else {
                // Vertical Direction
                // Octopus Head
                button[x][y].setDisabledIcon(octFootTop4);
                button[x][y].setEnabled(false);
                // Octopus Body
                //for (int i = 1; i < dim - 1; i++) {
                button[x + 1][y].setDisabledIcon(octBodyTop4b);
                button[x + 1][y].setEnabled(false);
                //}

                button[x + 2][y].setDisabledIcon(octBodyTop4a);
                button[x + 2][y].setEnabled(false);
                // Octopus Foot
                button[x + dim - 1][y].setDisabledIcon(octHeadTop4);
                button[x + dim - 1][y].setEnabled(false);
            }
        }
    }
}
