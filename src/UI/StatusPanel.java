package UI;

import gameLogic.Mouse;

import javax.swing.*;

/**
 * StatusPanel is the JPanel for the game text information
 */
public class StatusPanel extends JPanel {
    private JLabel title;
    private String titleText;
    private Mouse mouse;
    private KeyboardInput input;

    public StatusPanel(Mouse mouse, KeyboardInput input) {
        this.mouse = mouse;
        this.input = input;
        setTitleText();
        title = new JLabel(titleText);
        setupLayout();
        registerAsMouseListener();
    }

    private void setTitleText() {
        titleText = "Collected " + mouse.getCheeseCollected() +
                " of " + mouse.getMAX_CHEESE() + " cheese. Press ? for help.";
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(title);
    }

    private void registerAsMouseListener() {
        input.addMouseListener(new ChangeListener() {
            @Override
            public void stateChanged() {
                update();
            }
        });
    }

    private void update() {
        setTitleText();
        title.setText(titleText);
    }

}
