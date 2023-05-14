package battleship_main;

import battleship_main.ui.FrameManageship;
import battleship_main.ui.FrameSplashscreen;

public class Main {
    public static void main(String[] args) {
        FrameSplashscreen intro = new FrameSplashscreen();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        intro.setVisible(true);
         //FrameManageship2PlClient manage = new FrameManageship2PlClient();
        //FrameManageship2PlServer manage = new FrameManageship2PlServer();
        FrameManageship manage = new FrameManageship();
        manage.setVisible(true);
    }
}
