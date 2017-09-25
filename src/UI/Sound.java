package UI;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Sound manages playing a sound for certain events
 */
public class Sound {

    public static void hitWall() {
        playSound("src/sounds/pop.wav");
    }

    public static void win() {
        playSound("src/sounds/ifeelgood.wav");
    }

    public static void lose() {
        playSound("src/sounds/bark.wav");
    }

    //Modified code from assignment instructions:
    private static void playSound(String pathname) {
        File sound = new File(pathname);
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
