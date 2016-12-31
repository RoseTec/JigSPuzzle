package jigspuzzle.view.desktop.swing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileView;

/**
 * A FileView for displaying previews of images in a filechoose.
 *
 * source: http://stackoverflow.com/a/4397927
 *
 * @author RoseTec
 */
public class ThumbnailView extends FileView {

    /**
     * The maximal length of the filename to be displayed. The rest will be
     * indicated by "..."
     */
    private final int maxLengthOfFilename = 30;

    /**
     * All preview icons will be this width and height
     */
    private final int iconSize;

    /**
     * This blank icon will be used while previews are loading
     */
    private final Image loadingImage;

    private final Pattern imageFilePattern = Pattern.compile(".+?\\.(png|jpe?g|gif|tiff?)$", Pattern.CASE_INSENSITIVE);

    /**
     * Use a weak hash map to cache images until the next garbage collection
     * (saves memory)
     */
    private final Map<File, ImageIcon> imageCache = new WeakHashMap<>();

    /**
     * This thread pool is where the thumnnail icon loaders run
     */
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final JFileChooser fileChooser;

    public ThumbnailView(JFileChooser fileChooser, int iconSize) {
        this.fileChooser = fileChooser;
        this.iconSize = iconSize;
        loadingImage = new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB);
    }

    private Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            icon.paintIcon(null, image.getGraphics(), 0, 0);
            return image;
        }
    }

    @Override
    public Icon getIcon(File file) {
//        if (file.isDirectory()) {
//            FileSystemView view = FileSystemView.getFileSystemView();
//            return new ImageIcon(getScaledImage(iconToImage(view.getSystemIcon(file))));
//        }
        if (!imageFilePattern.matcher(file.getName()).matches()) {
            return null;
        }

        // Our cache makes browsing back and forth lightning-fast! :D
        synchronized (imageCache) {
            ImageIcon icon = imageCache.get(file);

            if (icon == null) {
                // Create a new icon with the default image
                icon = new ImageIcon(loadingImage);

                // Add to the cache
                imageCache.put(file, icon);

                // Submit a new task to load the image and update the icon
                executor.submit(new ThumbnailIconLoader(icon, file));
            }

            return icon;
        }
    }

    @Override
    public String getName(File f) {
        if (!imageFilePattern.matcher(f.getName()).matches()) {
            return null;
        }
        String newName = f.getName();

        // shorten the names of images
        if (newName.length() > maxLengthOfFilename) {
            String appending = "...";

            newName = newName.substring(0, maxLengthOfFilename - appending.length()) + appending;
        }
        return newName;
    }

    /**
     * Gets a scaled version of the given image. The scaled version is
     * restricted by the variable <code>iconSize</code>.
     *
     * It will be tried to maintain the image's aspect ratio.
     *
     * @param img
     * @return
     */
    private Image getScaledImage(Image img) {
        BufferedImage scaledImg = new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImg.createGraphics();

        g.setComposite(AlphaComposite.DstAtop);
        if (img.getWidth(null) > img.getHeight(null)) {
            int height = iconSize * img.getHeight(null) / img.getWidth(null);
            g.drawImage(img, 0, (iconSize - height) / 2, iconSize, height, null);
        } else {
            int width = iconSize * img.getWidth(null) / img.getHeight(null);
            g.drawImage(img, (iconSize - width) / 2, 0, width, iconSize, null);
        }
        g.dispose();
        return scaledImg;
    }

    private class ThumbnailIconLoader implements Runnable {

        private final ImageIcon icon;
        private final File file;

        public ThumbnailIconLoader(ImageIcon i, File f) {
            icon = i;
            file = f;
        }

        @Override
        public void run() {
            Image newIcon;
            newIcon = new ImageIcon(file.getAbsolutePath()).getImage();
            icon.setImage(getScaledImage(newIcon));

            // Repaint the dialog so we see the new icon.
            SwingUtilities.invokeLater(() -> {
                ThumbnailView.this.fileChooser.repaint();
            });
        }
    }

}
