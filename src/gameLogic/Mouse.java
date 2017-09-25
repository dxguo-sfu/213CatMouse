package gameLogic;

import UI.Sound;

/**
 * Mouse moves and keeps track of the number of cheese it's eaten.
 */
public class Mouse extends Character {
    private int cheeseCollected;
    private final int MAX_CHEESE = 5;

    public Mouse() {
        super(1, 1);
        cheeseCollected = 0;
    }

    @Override
    public void move(int dir) {
        super.move(dir);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                MazeManager.cells[getRow() + i][getCol() + j].setAsKnown();
            }
        }
    }

    @Override
    protected void setCell(Cell cell) {
        cell.setAsKnown();
    }

    @Override
    protected void handleWallCollision() {
        Sound.hitWall();
        setMoved(false);
    }

    public boolean ateAllCheese() {
        return cheeseCollected == MAX_CHEESE;
    }

    public void ateCheese() {
        cheeseCollected++;
    }

    public int getCheeseCollected() {
        return cheeseCollected;
    }

    public int getMAX_CHEESE() {
        return MAX_CHEESE;
    }
}
