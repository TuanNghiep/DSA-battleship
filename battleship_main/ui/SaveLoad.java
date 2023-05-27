package battleship_main.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class SaveLoad {
    public void marshallLinkedListToFile(LinkedList<int[]> playerShips){   
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("playerOctopus.dat"));
            oos.writeObject(playerShips);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void marshallAdvLinkedListToFile(LinkedList<int[]> advShips){   
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("advShips.dat"));
            oos.writeObject(advShips);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadfromFile(String path1,String path2){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path1));
            ObjectInputStream oas = new ObjectInputStream(new FileInputStream(path1));
            LinkedList<int[]> playerShips = (LinkedList<int[]>) ois.readObject();
            LinkedList<int[]> advShips = (LinkedList<int[]>) oas.readObject();
            ois.close();
            oas.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
