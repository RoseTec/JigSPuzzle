package jigspuzzle.view;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import jigspuzzle.JigSPuzzleResources;
import jigspuzzle.controller.SettingsController;

/**
 * A class for playing sounds that are used in JigSPuzzle.
 *
 * @author RoseTec
 */
public class SoundPlayer implements ISoundPlayer {

    /**
     * {@inheritDoc}
     */
    @Override
    public void playSnapPuzzlepieces() {
        try {
            playSound(getSoundfile("snap_puzzlepieces.wav"));
        } catch (LineUnavailableException | IOException ex) {
            // todo: better error-handliing
            System.out.println("Exectued a line that should not be exectuted in:\n"
                    + this.getClass().toString() + "::playSnapPuzzlepieces()");
        }
    }

    /**
     * Gets the soundfile width the given name.
     *
     * All soundfiles are located in one special resource-folder. By calling
     * this method, the soundfile will be search for in that special
     * resource-folder.
     *
     * @param soundName The name of the soundfile. It is possible to look in
     * other directories like e.g. <code>sub/forder/file.wav</code>
     * @return
     */
    private URL getSoundfile(String soundName) {
        return JigSPuzzleResources.getResource("/sounds/" + soundName);
    }

    /**
     * Plays the sound from the given path.
     *
     * @param soundpath
     * @throws LineUnavailableException
     * @throws IOException
     */
    private void playSound(URL soundpath) throws LineUnavailableException, IOException {
        // check settings
        if (!SettingsController.getInstance().getPlaySounds()) {
            return;
        }

        // play sound
        AudioInputStream inputStream = null;

        try {
            final Clip clip = AudioSystem.getClip();
            clip.addLineListener((LineEvent event) -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    // close after played
                    clip.close();
                }
            });
            inputStream = AudioSystem.getAudioInputStream(soundpath);
            clip.open(inputStream);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Exectued a line that should not be exectuted in:\n"
                    + this.getClass().toString() + "::playSound()");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
