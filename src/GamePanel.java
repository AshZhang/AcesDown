/**
 * Created by Ashley Zhang, APCS Period 2 on 5/15/17
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.YES_OPTION;

public class GamePanel extends JPanel {
    //Complete! Do not change.
    private JButton[] discardButton;
    private JButton[] moveButton;
    private JButton newGameButton;
    private JButton dealButton;
    private JButton undoButton;
    private JLabel scoreLabel1;
    private JPanel controlPanel, cardPanel;
    private GameController game;
    private JLabel[][] iconArray;
    private String fileprefix;

    public GamePanel(GameController controller) {
        game = controller;
        game.startNewGame();
        fileprefix = "images/";
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        controlPanel = new JPanel(new GridLayout(4, 3));
        discardButton = new JButton[4];
        moveButton = new JButton[4];
        // Each button gets a listener object associated with it
        // If the user clicks on the button, the listener invokes
        // the actionPerformed method below.
        ButtonListener listener = new ButtonListener();
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    newGameButton = new JButton("NEW GAME");
                    newGameButton.setBackground(Color.LIGHT_GRAY);
                    newGameButton.setForeground(Color.RED);
                    newGameButton.addActionListener(listener);
                    controlPanel.add(newGameButton);
                    break;
                case 1:
                    dealButton = new JButton("DEAL 4");
                    dealButton.setBackground(Color.LIGHT_GRAY);
                    dealButton.addActionListener(listener);
                    controlPanel.add(dealButton);
                    break;
                case 2:
                    scoreLabel1 = new JLabel(" SCORE: 0");
                    controlPanel.add(scoreLabel1);
                    break;
                case 3:
                    undoButton = new JButton("UNDO");
                    undoButton.setBackground(Color.LIGHT_GRAY);
                    undoButton.setForeground(Color.RED);
                    undoButton.addActionListener(listener);
                    controlPanel.add(undoButton);
                    break;
            }
            discardButton[i] = new JButton("DISCARD");
            discardButton[i].setBackground(Color.LIGHT_GRAY);
            discardButton[i].addActionListener(listener);
            controlPanel.add(discardButton[i]);
            moveButton[i] = new JButton("MOVE");
            moveButton[i].addActionListener(listener);
            controlPanel.add(moveButton[i]);
        }
        add(controlPanel);
        cardPanel = new JPanel(new GridLayout(4, 13));
        iconArray = new JLabel[4][13];
        for (int listNum = 0; listNum < 4; listNum++) {
            for (int index = 0; index < 13; index++) {
                PlayingCard c = game.getCard(listNum, index);
                if (c != null) {
                    JLabel cardImage = new JLabel(new ImageIcon(fileprefix +
                            c.getImageFileName()));
                    iconArray[listNum][index] = cardImage;
                    cardPanel.add(cardImage);
                } else {
                    JLabel cardImage = new JLabel(new ImageIcon(""));
                    iconArray[listNum][index] = cardImage;
                    cardPanel.add(cardImage);
                }
            }
        }
        add(cardPanel);
    }

    private void updatePanel() {
        // Each card is a JLabel with an icon (picture) displayed
        // The iconArray keeps track of the references to all of
        // the icons used for the game display.
        for (int listNum = 0; listNum < 4; listNum++) {
            for (int index = 0; index < 13; index++) {
                PlayingCard c = game.getCard(listNum, index);
                if (c == null)
                    iconArray[listNum][index].setIcon(null);
                else
                    iconArray[listNum][index].setIcon(new ImageIcon(fileprefix +
                            c.getImageFileName()));
            }
        }
        scoreLabel1.setText("SCORE: " + game.getScore());
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == dealButton) {
                game.deal();
            }
            else if (event.getSource() == newGameButton) {
                int n = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to restart the game?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION);
                if(n == YES_OPTION) {
                    game.startNewGame();
                }
            }
            else if(event.getSource() == undoButton) {
                game.undo();
            }
            else {
                for (int listNum = 0; listNum < 4; listNum++) {
                    if (event.getSource() == discardButton[listNum])
                        game.discard(listNum);
                    else if (event.getSource() == moveButton[listNum])
                        game.move(listNum);
                }
            }
            updatePanel();
        }
    }
}