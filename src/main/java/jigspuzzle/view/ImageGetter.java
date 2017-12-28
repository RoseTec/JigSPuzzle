package jigspuzzle.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import jigspuzzle.JigSPuzzleResources;

/**
 * With this class one can get the images for using in the user interface.
 *
 * All methods of this cass return either an image or <code>null</code> if the
 * image was not found.
 *
 * @author RoseTec
 */
public class ImageGetter {

    private static ImageGetter instance;

    public static ImageGetter getInstance() {
        if (instance == null) {
            instance = new ImageGetter();
        }
        return instance;
    }

    private ImageGetter() {
    }

    /**
     * @return The image used for exiting this program.
     */
    public Image getExitImage() {
        return getImage("exit.png");
    }

    /**
     * @return The image used for showing the fullscreen mode.
     */
    public Image getFullscreenImage() {
        return getImage("fullscreen.png");
    }

    /**
     * @return The image used for closing the fullscreen mode.
     */
    public Image getFullscreenCloseImage() {
        return getImage("fullscreen_close.png");
    }

    /**
     * @return The image used for showing information of the program.
     */
    public Image getInfoImage() {
        return getImage("info.png");
    }

    /**
     * @return The image used for loading an existing puzzle from the hdd.
     * @see #getSaveImage()
     */
    public Image getLoadImage() {
        return getImage("load.png");
    }

    /**
     *
     * @return The main image that stands for JigSPuzzle.
     */
    public Image getJigSPuzzleImage() {
        return getImage("icon.png");
    }

    /**
     * Gets the image of the contry for the given language.
     *
     * @param language
     * @return Can be <code>null</code>, if there is no image for the given
     * language.
     */
    public Image getImageForLanguage(String language) {
        return getImage("lang/" + language + ".jpg");
    }

    /**
     * Gets the image of the contry for the given language. Also the images will
     * have the given height.
     *
     * @param language
     * @param height
     * @return Can be <code>null</code>, if there is no image for the given
     * language.
     */
    public Image getImageForLanguage(String language, int height) {
        // get image
        Image img = getImageForLanguage(language);

        // bring to desired height
        int width = height * img.getWidth(null) / img.getHeight(null);
        return img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    /**
     * Gets an image that shows a monitor.
     *
     * @return
     */
    public Image getMonitorImage() {
        return getImage("monitor.png");
    }

    /**
     * @return The image used for creating a new puzzle from an image.
     */
    public Image getNewPuzzleImage() {
        return getImage("new_puzzle.png");
    }

    /**
     * @return The image used for restarting the current puzzle.
     */
    public Image getRestartImage() {
        return getImage("restart_puzzle.png");
    }

    /**
     * @return The image used for saving a puzzle to the hdd.
     * @see #getLoadImage()
     */
    public Image getSaveImage() {
        return getImage("save.png");
    }

    /**
     * @return The image used for displaying settings.
     */
    public Image getSettingsImage() {
        return getImage("settings.png");
    }

    /**
     * @return The image used for shuffleing the puzzlepieces.
     */
    public Image getShuffleImage() {
        return getImage("shuffle_puzzlepieces.png");
    }

    private Image getImage(String imageName) {
        URL url = JigSPuzzleResources.getResource("/images/" + imageName);

        try {
            return url == null ? null : ImageIO.read(url);
        } catch (IOException ex) {
            // prefer ImageIO.read() over this one.
            // use this only, when ImageIO.read() fails
            return Toolkit.getDefaultToolkit().createImage(url);
        }
    }

}
