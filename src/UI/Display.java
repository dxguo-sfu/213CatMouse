package UI;

import gameLogic.GameManager;
import gameLogic.MazeManager;

import javax.swing.*;
import java.awt.*;


/**
 * Display builds the JFrame and pop-up windows
 */
public class Display {
    private JFrame frame;
    private KeyboardInput input;
    private GamePanel gamePanel;

    private final int RESUME = 0;
    private final int END = 1;

    public Display(KeyboardInput input) {
        this.input = input;
        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.add(makeGamePanel(), BorderLayout.NORTH);
        frame.add(makeStatusPanel(), BorderLayout.EAST);
        frame.add(input);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        instructions();
        registerAsHelpListener();
    }

    private GamePanel makeGamePanel() {
        gamePanel = new GamePanel(MazeManager.ROWS_SIZE, MazeManager.COLS_SIZE, input);
        return gamePanel;
    }

    private StatusPanel makeStatusPanel() {
        return new StatusPanel(GameManager.getMouse(), input);
    }

    public void displayMaze() {
        gamePanel.updateIcons();
    }

    public void gameLost() {
        displayMaze();
        String header = "Game lost...";
        String message = "Oh no! You got eaten by a cat. Game Over.";
        message(header, message, END);
    }

    public void gameWon() {
        displayMaze();
        String header = "Congratulations!";
        String message = "You ate all the cheese! You win!";
        message(header, message, END);
    }

    private void instructions() {
        String header = "How to Play";
        String message =
                "You are a mouse lost in a dark maze.\n\n" +
                        "Use the ARROW KEYS to explore and \ncollect cheese, " +
                        "but watch out for CATS!\n" +
                        "Collect FIVE CHEESE to win!";
        message(header, message, RESUME);
    }

    private void message(String header, String message, int action) {
        int input = JOptionPane.showOptionDialog(frame, message, header, JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (action == END &&
                input == JOptionPane.OK_OPTION) {
            frame.dispose();
        }
    }

    private void registerAsHelpListener() {
        input.addHelpListener(new ChangeListener() {
            @Override
            public void stateChanged() {
                instructions();
            }
        });
    }

}
