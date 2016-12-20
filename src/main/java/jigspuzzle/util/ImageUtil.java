package jigspuzzle.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * A class that provides useful methods for images.
 *
 * @author RoseTec
 */
public class ImageUtil {

    /**
     * Transforms a Image into a BufferedImage.
     *
     * @param img
     * @return
     */
    public static BufferedImage transformImageToBufferedImage(Image img) {
        // source: http://stackoverflow.com/a/13605411
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

}
