package jigspuzzle.view.desktop.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
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

    @Override
    public Icon getIcon(File file) {
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

    private class ThumbnailIconLoader implements Runnable {

        private final ImageIcon icon;
        private final File file;

        public ThumbnailIconLoader(ImageIcon i, File f) {
            icon = i;
            file = f;
        }

        @Override
        public void run() {
            // Load and scale the image down, then replace the icon's old image with the new one.
            try {
                BufferedImage newIcon = ImageIO.read(file);
                BufferedImage scaledImg = new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = scaledImg.createGraphics();

                g.setComposite(AlphaComposite.DstAtop);
                if (newIcon.getWidth() > newIcon.getHeight()) {
                    int height = iconSize * newIcon.getHeight() / newIcon.getWidth();
                    g.drawImage(newIcon, 0, (iconSize - height) / 2, iconSize, height, null);
                } else {
                    int width = iconSize * newIcon.getWidth() / newIcon.getHeight();
                    g.drawImage(newIcon, (iconSize - width) / 2, 0, width, iconSize, null);
                }
                g.dispose();
                icon.setImage(scaledImg);
            } catch (IOException ex) {
                // this scaling does not maintain the image's aspect ratio:
                ImageIcon newIcon = new ImageIcon(file.getAbsolutePath());
                Image scaledImg = newIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
                icon.setImage(scaledImg);
            }

            // Repaint the dialog so we see the new icon.
            SwingUtilities.invokeLater(() -> {
                ThumbnailView.this.fileChooser.repaint();
            });
        }
    }

}
