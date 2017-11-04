/**
 * Created by Ashley Zhang, APCS Period 2 on 5/15/17
 */

import javax.swing.*;

public class AcesDown {
    //Complete! Do not change.
    public static void main(String[] args) {
        GameController controller = new GameController();
        JFrame frame = new JFrame("Aces Down");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GamePanel(controller));
        frame.pack();
        frame.setVisible(true);
    }
}