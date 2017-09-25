package gameLogic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Cat class moves without unnecessary backtracking and
 * checks if it's eaten a mouse.
 */
public class Cat extends Character {
    private Random random = new Random();
    private ArrayList<Integer> previous = new ArrayList<>();
    private ArrayList<Integer> directions = new ArrayList<>();
    private int recursionCount = 0;

    public Cat(int row, int col) {
        super(row, col);
        int noPrevious = 5;
        previous.add(noPrevious);
    }

    public void move() {
        boolean match = false;
        int nextMove = random.nextInt(MazeManager.NUM_DIRECTIONS);

        while (nextMove == previous.get(0)) {
            nextMove = random.nextInt(MazeManager.NUM_DIRECTIONS);
            for (Integer i : directions) {
                if (nextMove == i) {
                    match = true;
                }
            }
            if (directions.size() > 2) {
                nextMove = previous.get(0);
                break;
            }
            if (!match && nextMove != previous.get(0)) {
                directions.add(nextMove);
            }
        } //while

        super.move(nextMove);
        directions.clear();
        previous.add(0, (nextMove + 2) % 4);
    }

    @Override
    protected void handleWallCollision() {
        move();
        recursionCount++;
    }

    public void removeBacktrack() {
        for (int i = 0; i < recursionCount; i++) {
            previous.remove(0);
        }
        recursionCount = 0;
    }

    public boolean mouseCollision() {
        return GameManager.getMouse().isHere(getRow(), getCol());
    }
}
