package UI;

import gameLogic.GameManager;
import gameLogic.MazeManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * KeyboardInput gets user input and notifies listeners of input
 */
public class KeyboardInput extends JPanel implements KeyListener {
    private static List<ChangeListener> mouseListeners = new ArrayList<>();
    private static List<ChangeListener> mazeListeners = new ArrayList<>();
    private static List<ChangeListener> helpListeners = new ArrayList<>();

    public KeyboardInput() {
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_SLASH:
                notifyHelpListeners();
                break;
            case KeyEvent.VK_M:
                notifyMazeListeners();
                break;
            case KeyEvent.VK_UP:
                notifyMouseListeners(MazeManager.UP);
                break;
            case KeyEvent.VK_RIGHT:
                notifyMouseListeners(MazeManager.RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                notifyMouseListeners(MazeManager.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                notifyMouseListeners(MazeManager.LEFT);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void addMouseListener(ChangeListener listener) {
        mouseListeners.add(listener);
    }

    private void notifyMouseListeners(int direction) {
        GameManager.getMouse().move(direction);
        if(GameManager.getMouse().isMoved()) {
            for (ChangeListener listener : mouseListeners) {
                listener.stateChanged();
            }
        }
    }

    public void addMazeListener(ChangeListener listener) {
        mazeListeners.add(listener);
    }

    private void notifyMazeListeners() {
        for (ChangeListener listener : mazeListeners) {
            listener.stateChanged();
        }
    }

    public void addHelpListener(ChangeListener listener) {
        helpListeners.add(listener);
    }

    private void notifyHelpListeners() {
        for (ChangeListener listener : helpListeners) {
            listener.stateChanged();
        }
    }
}
