package UI;

import gameLogic.CatManager;
import gameLogic.Cell;
import gameLogic.GameManager;
import gameLogic.MazeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * GamePanel is the JPanel that displays the game board
 */
public class GamePanel extends JPanel {
    private ImageIcon CAT;
    private ImageIcon MOUSE;
    private ImageIcon CHEESE;
    private ImageIcon WALL;
    private ImageIcon PATH;
    private ImageIcon HIDDEN;

    private final int SIZE = 45;
    private int rowsSize;
    private int colsSize;

    private KeyboardInput input;
    private JLabel icons[][];

    public GamePanel(int rowsSize, int colsSize, KeyboardInput input) {
        this.rowsSize = rowsSize;
        this.colsSize = colsSize;
        this.input = input;

        icons = new JLabel[rowsSize][colsSize];
        this.setLayout(new GridLayout(rowsSize, colsSize));
        initializeIcons();
        updateIcons();
        registerAsMouseListener();
    }

    private void initializeIcons() {
        CAT = getScaleImageIcon(new ImageIcon("src/drawable/cat.png"), SIZE, SIZE);
        MOUSE = getScaleImageIcon(new ImageIcon("src/drawable/mouse.png"), SIZE, SIZE);
        CHEESE = getScaleImageIcon(new ImageIcon("src/drawable/cheese.png"), SIZE, SIZE);
        WALL = getScaleImageIcon(new ImageIcon("src/drawable/wall.png"), SIZE, SIZE);
        PATH = getScaleImageIcon(new ImageIcon("src/drawable/path.png"), SIZE, SIZE);
        HIDDEN = getScaleImageIcon(new ImageIcon("src/drawable/hidden.png"), SIZE, SIZE);

        for (int row = 0; row < rowsSize; row++) {
            for (int col = 0; col < colsSize; col++) {
                icons[row][col] = new JLabel();
                icons[row][col].setIcon(HIDDEN);
                this.add(icons[row][col]);
            }
        }
    }

    public void updateIcons() {
        for (int row = 0; row < rowsSize; row++) {
            for (int col = 0; col < colsSize; col++) {
                Cell cell = MazeManager.cells[row][col];

                if (CatManager.isHere(row, col)) {
                    icons[row][col].setIcon(CAT);
                } else if (GameManager.getMouse().isHere(row, col)) {
                    icons[row][col].setIcon(MOUSE);
                } else if (GameManager.getCheese().isHere(row, col)) {
                    icons[row][col].setIcon(CHEESE);
                } else if (cell.isKnown()) {
                    if (cell.isWall()) {
                        icons[row][col].setIcon(WALL);
                    } else {
                        icons[row][col].setIcon(PATH);
                    }
                } else {
                    icons[row][col].setIcon(HIDDEN);
                }

            }
        }
    }

    private void registerAsMouseListener() {
        input.addMouseListener(new ChangeListener() {
            @Override
            public void stateChanged() {
                updateIcons();
            }
        });
    }

    //Code from assignment instructions:
    static public ImageIcon getScaleImageIcon(ImageIcon icon, int width, int height) {
        return new ImageIcon(getScaledImage(icon.getImage(), width, height));
    }

    static private Image getScaledImage(Image srcImg, int width, int height) {
        BufferedImage resizedImg =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();
        return resizedImg;
    }

}
