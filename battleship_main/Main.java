package battleship_main;

import battleship_main.ui.FrameManageOctopus;
import battleship_main.ui.FrameSplashscreen;

public class Main {
    public static void main(String[] args) {
        FrameSplashscreen intro = new FrameSplashscreen();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        intro.setVisible(false);
        FrameManageOctopus manage = new FrameManageOctopus();
        manage.setVisible(true);
    }
}
