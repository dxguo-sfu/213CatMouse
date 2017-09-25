package gameLogic;

import UI.ChangeListener;
import UI.KeyboardInput;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MazeManager class generates the maze.
 */
public class MazeManager {
    public static final int ROWS_SIZE = 15;
    public static final int INSIDE_ROWS_SIZE = ROWS_SIZE - 2;
    public static final int COLS_SIZE = 20;
    public static final int INSIDE_COLS_SIZE = COLS_SIZE - 2;
    public static Cell[][] cells = new Cell[ROWS_SIZE][COLS_SIZE];

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int NUM_DIRECTIONS = 4;

    private static Random rand = new Random();
    private static List<Cell> previous = new ArrayList<>();
    private KeyboardInput input;

    public MazeManager(KeyboardInput input) {
        this.input = input;
        initializeCells();
        generateMaze();
        registerAsMazeListener();
    }

    private void initializeCells() {
        for (int row = 0; row < ROWS_SIZE; row++) {
            for (int col = 0; col < COLS_SIZE; col++) {
                if (row == 0 ||
                        row == ROWS_SIZE - 1 ||
                        col == 0 ||
                        col == COLS_SIZE - 1) {
                    cells[row][col] = new Cell(true, true, row, col);
                } else {
                    cells[row][col] = new Cell(true, false, row, col);
                }
            }
        }
        cells[1][1].setAsKnown();
    }

    private void generateMaze() {
        //Depth-first search maze generation
        int alternateRows = 7;
        int startCol = rand.nextInt(INSIDE_COLS_SIZE) + 1;
        int startRow = (rand.nextInt(alternateRows) * 2) + 1;
        makePath(cells[startRow][startCol]);
        int emptyCol;
        if (startCol % 2 == 0) {
            emptyCol = 1;
        } else {
            emptyCol = INSIDE_COLS_SIZE;
        }
        for (int i = 0; i < INSIDE_ROWS_SIZE; i++) {
            cells[i + 1][emptyCol].setWallFalse();
        }
        knockWalls();
    }

    private void knockWalls() {
        int knockBounds = 3;
        int rollKnock = 10;
        for (int i = 1; i < INSIDE_ROWS_SIZE; i++) {
            for (int j = 1; j < INSIDE_COLS_SIZE; j++) {
                if (cells[i][j].isWall()) {
                    int knock = rand.nextInt(rollKnock);
                    if (knock < knockBounds) {
                        cells[i][j].setWallFalse();
                    }
                }
            }
        }
    }

    private void makePath(Cell c) {
        Cell current = c;
        current.setWallFalse();
        boolean[] directions = new boolean[NUM_DIRECTIONS];
        boolean allDirections = false;
        int direction;

        while (!allDirections) {
            direction = rand.nextInt(NUM_DIRECTIONS);
            boolean isAllChecked = true;
            for (boolean isChecked : directions) {
                if (!isChecked) {
                    isAllChecked = false;
                }
            }
            if (isAllChecked) {
                allDirections = true;
            }
            if (!directions[direction]) {                       //if direction has not been checked
                directions[direction] = true;                   //check it
                if (checkMove(direction, current)) {            //if possible
                    previous.add(0, current);            //add current cell to list of previously visited
                    current = traversePath(direction, current); //assign new current and traverse 2 steps
                    makePath(current);
                }
            }
        }
        if (previous.size() > 0) {                              //backtrack through previously visited
            current = previous.get(0);
            previous.remove(0);
            makePath(current);
        }
    }

    private Cell traversePath(int dir, Cell c) {
        Cell current = c;
        if (dir == UP) {
            cells[current.getRow() - 1][current.getCol()].setWallFalse();
            current = cells[current.getRow() - 2][current.getCol()];
            current.setWallFalse();
        } else if (dir == RIGHT) {
            cells[current.getRow()][current.getCol() + 1].setWallFalse();
            current = cells[current.getRow()][current.getCol() + 2];
            current.setWallFalse();
        } else if (dir == DOWN) {
            cells[current.getRow() + 1][current.getCol()].setWallFalse();
            current = cells[current.getRow() + 2][current.getCol()];
            current.setWallFalse();
        } else { //left
            cells[current.getRow()][current.getCol() - 1].setWallFalse();
            current = cells[current.getRow()][current.getCol() - 2];
            current.setWallFalse();
        }
        return current;
    }

    private boolean checkMove(int dir, Cell current) {
        int topBounds = 3;
        int rightBounds = 16;
        int bottomBounds = 11;
        int leftBounds = 3;

        if (dir == UP) {
            if (current.getRow() < topBounds) {
                return false;
            } else {
                return cells[current.getRow() - 2][current.getCol()].isWall();
            }
        } else if (dir == RIGHT) {
            if (current.getCol() > rightBounds) {
                return false;
            } else {
                return cells[current.getRow()][current.getCol() + 2].isWall();
            }
        } else if (dir == DOWN) {
            if (current.getRow() > bottomBounds) {
                return false;
            } else {
                return cells[current.getRow() + 2][current.getCol()].isWall();
            }
        } else { //left
            if (current.getCol() < leftBounds) {
                return false;
            } else {
                return cells[current.getRow()][current.getCol() - 2].isWall();
            }
        }
    }

    private void revealMaze() {
        for (int row = 0; row < MazeManager.ROWS_SIZE; row++) {
            for (int col = 0; col < MazeManager.COLS_SIZE; col++) {
                MazeManager.cells[row][col].setAsKnown();
            }
        }
    }

    private void registerAsMazeListener() {
        input.addMazeListener(new ChangeListener() {
            @Override
            public void stateChanged() {
                revealMaze();
                GameManager.displayGame();
            }
        });

    }
}
