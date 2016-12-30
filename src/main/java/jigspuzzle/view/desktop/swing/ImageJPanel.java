package jigspuzzle.view.desktop.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 * A JPanel that only draws an image. That image is strechted over the width of
 * the panel.
 *
 * @author RoseTec
 */
public class ImageJPanel extends JPanel {

    /**
     * The image that this panels displays in its background.
     */
    private Image image;

    public ImageJPanel(LayoutManager layout, boolean isDoubleBuffered, Image image) {
        super(layout, isDoubleBuffered);
        this.image = image;
    }

    public ImageJPanel(LayoutManager layout, Image image) {
        super(layout);
        this.image = image;
    }

    public ImageJPanel(boolean isDoubleBuffered, Image image) {
        super(isDoubleBuffered);
        this.image = image;
    }

    public ImageJPanel(Image image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

}
