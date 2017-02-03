package jigspuzzle.view.desktop.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import jigspuzzle.view.util.SelectionGroup;
import jigspuzzle.view.util.SelectionGroupSelectable;

/**
 * A JPanel that only draws an image. That image is strechted over the width of
 * the panel.
 *
 * @author RoseTec
 */
public class ImageJPanel extends JPanel implements SelectionGroupSelectable<Integer> {

    /**
     * The image that this panels displays in its background.
     */
    private Image image;

    private int marginTop, marginButtom, marginLeft, marginRight;

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

    /**
     * Sets the margin for displaying the image.
     *
     * The margin will be added as offset to display the image. An offset of 1
     * in each direction means, that there will be 1px space between the image
     * and the end of this panel.
     *
     * @param marginTop
     * @param marginButtom
     * @param marginLeft
     * @param marginRight
     */
    public void setMargin(int marginTop, int marginButtom, int marginLeft, int marginRight) {
        this.marginTop = marginTop;
        this.marginButtom = marginButtom;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        int offsetX = marginLeft + marginRight;
        int offsetY = marginTop + marginButtom;

        return new Dimension(image.getWidth(null) + offsetX, image.getHeight(null) + offsetY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(image, marginLeft, marginTop, getWidth() - marginLeft - marginRight, getHeight() - marginTop - marginButtom, null);

        // paint an overlay if this puzzlepiece is selected
        if (selectionGroup != null && selectionGroup.isSelected(this)) {
            g2.setColor(SelectionGroupSelectable.COLOR_SELECTED_OBJECT);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    private SelectionGroup<Integer> selectionGroup;
    private Integer selectionValue;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSelectionValue() {
        return selectionValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectionGroup(SelectionGroup<Integer> selectionGroup) {
        // add click listener
        if (this.selectionGroup == null) {
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ImageJPanel.this.selectionGroup.changeSelectedValue(ImageJPanel.this);
                }
            });
        }

        // set the selection group
        this.selectionGroup = selectionGroup;

        // repaint on changes on theselected value
        this.selectionGroup.addChangeListener((ChangeEvent e) -> {
            this.repaint();
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectionValue(Integer value) {
        selectionValue = value;
    }

}
