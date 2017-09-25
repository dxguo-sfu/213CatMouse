package gameLogic;

import UI.ChangeListener;
import UI.KeyboardInput;

import java.util.ArrayList;
import java.util.List;

/**
 * CatManager class handles the collection of cats.
 */
public class CatManager {
    private static List<Cat> cats = new ArrayList<>();
    private KeyboardInput input;

    public CatManager(KeyboardInput input) {
        this.input = input;
        initializeCats();
        registerAsMouseListener();
    }

    private void initializeCats() {
        int WALL_SIZE = 1;
        cats.add(new Cat(gameLogic.MazeManager.INSIDE_ROWS_SIZE, WALL_SIZE));
        cats.add(new Cat(WALL_SIZE, MazeManager.INSIDE_COLS_SIZE));
        cats.add(new Cat(MazeManager.INSIDE_ROWS_SIZE, MazeManager.INSIDE_COLS_SIZE));

        cats.add(new Cat(gameLogic.MazeManager.INSIDE_ROWS_SIZE - 1, WALL_SIZE));
        cats.add(new Cat(WALL_SIZE, MazeManager.INSIDE_COLS_SIZE - 1));
        cats.add(new Cat(MazeManager.INSIDE_ROWS_SIZE - 1, MazeManager.INSIDE_COLS_SIZE));
    }

    private void registerAsMouseListener() {
        input.addMouseListener(new ChangeListener() {
            @Override
            public void stateChanged() {
                moveCats();
                if (catAteMouse()) {
                    GameManager.gameLost();
                }
            }
        });
    }

    private void moveCats() {
        for (Cat cat : cats) {
            cat.move();
            cat.removeBacktrack();
        }
    }

    private static boolean catAteMouse() {
        for (Cat cat : cats) {
            if (cat.mouseCollision()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHere(int row, int col) {
        for (Cat cat : cats) {
            if (cat.isHere(row, col)) {
                return true;
            }
        }
        return false;
    }

}
