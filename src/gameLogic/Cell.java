package gameLogic;

/**
 * Cell class tracks if a maze space is a wall, and if it's been discovered.
 */
public class Cell {

    private boolean isWall;
    private boolean isKnown;
    private int col;
    private int row;

    public Cell(boolean isWall, boolean isKnown, int row, int col) {
        this.isWall = isWall;
        this.isKnown = isKnown;
        this.col = col;
        this.row = row;
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public void setAsKnown() {
        isKnown = true;
    }

    public void setWallFalse() {
        isWall = false;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

}
