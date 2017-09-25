package gameLogic;

/**
 * Cheese class tracks location and eaten status of cheese, and places the cheese.
 */
public class Cheese {
    private int row;
    private int col;

    public Cheese(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isEaten() {
        return GameManager.getMouse().isHere(row, col);
    }

    public boolean isHere(int row, int col) {
        return row == this.row && col == this.col;
    }
}
