package jigspuzzle.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

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

    /**
     * Transforms the given image to an icon. For this, the class
     * <code>ImageIcon</code> is used.
     *
     * @param img
     * @return An instance of an icon for the given image or <code>null</code>,
     * when the given image is <code>null</code>.
     */
    public static Icon transformImageToIcon(Image img) {
        if (img == null) {
            return null;
        } else {
            return new ImageIcon(img);
        }
    }

    /**
     * Transforms the given image to an icon. For this, the class
     * <code>ImageIcon</code> is used.
     *
     * @param img
     * @param size The that the icon should have.
     * @return An instance of an icon for the given image or <code>null</code>,
     * when the given image is <code>null</code>.
     */
    public static Icon transformImageToIcon(Image img, Dimension size) {
        if (img == null) {
            return null;
        } else {
            Image resisedImg = img.getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT);
            return transformImageToIcon(resisedImg);
        }
    }

    /**
     * Compares two images pixel by pixel and returns if they are equal.
     *
     * @param imgageA the first image.
     * @param imgageB the second image.
     * @return whether the images are both the same or not.
     */
    public static boolean imagesAreEqual(Image imgageA, Image imgageB) {
        BufferedImage imgA = transformImageToBufferedImage(imgageA);
        BufferedImage imgB = transformImageToBufferedImage(imgageB);
        //source: http://stackoverflow.com/a/29886786
        // The images must be the same size.
        if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
            int width = imgA.getWidth();
            int height = imgA.getHeight();

            // Loop over every pixel.
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Compare the pixels for equality.
                    if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }
}
