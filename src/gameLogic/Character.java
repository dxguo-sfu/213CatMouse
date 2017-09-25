package gameLogic;

/**
 * Character class is a super class that moves and tracks location.
 */
public class Character {
    private int row;
    private int col;

    private boolean moved;

    public Character(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void move(int dir) {
        moved = true;
        if (dir == gameLogic.MazeManager.UP) {
            if (!wallCollision(row - 1, col)) {
                row--;
            }
        } else if (dir == MazeManager.RIGHT) {
            if (!wallCollision(row, col + 1)) {
                col++;
            }
        } else if (dir == MazeManager.DOWN) {
            if (!wallCollision(row + 1, col)) {
                row++;
            }
        } else if (dir == MazeManager.LEFT) {
            if (!wallCollision(row, col - 1)) {
                col--;
            }
        }
    }

    private boolean wallCollision(int row, int col) {
        Cell cell = MazeManager.cells[row][col];
        setCell(cell);
        if (cell.isWall()) {
            handleWallCollision();
            return true;
        } else {
            return false;
        }
    }

    protected void setCell(Cell cell) {
    }

    protected void handleWallCollision() {
    }

    public boolean isHere(int row, int col) {
        return row == this.row && col == this.col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
