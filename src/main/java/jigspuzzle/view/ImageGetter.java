package jigspuzzle.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

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

    private Image getImage(String imageName) {
        URL url = getClass().getResource("/images/" + imageName);

        return url == null ? null : Toolkit.getDefaultToolkit().getImage(url);
    }

}
