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
