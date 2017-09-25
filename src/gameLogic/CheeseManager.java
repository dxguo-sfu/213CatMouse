package gameLogic;

import UI.ChangeListener;
import UI.KeyboardInput;

import java.util.Random;

/**
 * Created by cooperwhite on 2017-03-15.
 */
public class CheeseManager {
    private Cheese cheese;
    private KeyboardInput input;
    private Random random = new Random();


    public CheeseManager(KeyboardInput input) {
        this.input = input;
        placeCheese();
        registerAsMouseListener();

    }

    private void registerAsMouseListener() {
        input.addMouseListener(new ChangeListener() {
            @Override
            public void stateChanged() {
                if (cheese.isEaten()) {
                    GameManager.cheeseIsEaten();
                    if(GameManager.getMouse().ateAllCheese()) {
                        GameManager.gameWon();
                    } else {
                        placeCheese();
                    }
                }
            }
        });

    }

    public void placeCheese() {
        int WALL_SIZE = 1;
        int row = random.nextInt(MazeManager.INSIDE_ROWS_SIZE) + WALL_SIZE;
        int col = random.nextInt(MazeManager.INSIDE_COLS_SIZE) + WALL_SIZE;
        if (MazeManager.cells[row][col].isWall() ||
                GameManager.getMouse().isHere(row, col)) {
            placeCheese();
        } else {
            cheese = new Cheese(row, col);
        }
    }

    public Cheese getCheese() {
        return cheese;
    }
}
