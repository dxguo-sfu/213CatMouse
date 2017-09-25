package gameLogic;

import UI.Display;
import UI.KeyboardInput;
import UI.Sound;

/**
 * GameManager gets user input and manages game elements.
 */
public class GameManager {
    private static Mouse mouse;
    private static CheeseManager cheeseManager;
    private static Display display;


    public static void main(String args[]) {
        KeyboardInput input = new KeyboardInput();
        new MazeManager(input);
        new CatManager(input);
        mouse = new Mouse();
        cheeseManager = new CheeseManager(input);
        display = new Display(input);
    }

    public static void displayGame() {
        display.displayMaze();
    }

    public static void gameLost() {
        Sound.lose();
        display.gameLost();
    }

    public static void gameWon() {
        Sound.win();
        display.gameWon();
    }

    public static Mouse getMouse() {
        return mouse;
    }

    public static Cheese getCheese() {
        return cheeseManager.getCheese();
    }

    public static void cheeseIsEaten() {
        mouse.ateCheese();
    }

}
