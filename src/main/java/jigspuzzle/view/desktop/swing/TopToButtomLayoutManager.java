package jigspuzzle.view.desktop.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * A Layoutmanager that layouts the components as follows:
 *
 * The children are layouted as follows: One child has one row assigned to it.
 * This row is streched ower the full width. To have several elements in one
 * row, it has to be contained in a additional panel.
 *
 * @author RoseTec
 */
public class TopToButtomLayoutManager implements LayoutManager {

    /**
     * The offset of the components to the left side of this component to be
     * left blank.
     *
     * Default is 0.
     */
    public int OFFSET_LEFT = 0;

    /**
     * The offset of the components to the right side of this component to be
     * left blank.
     *
     * Default is 0.
     */
    public int OFFSET_RIGHT = 0;

    /**
     * The offset of the first components to the border to be left blank.
     *
     * Default is 0.
     */
    public int OFFSET_TOP = 0;

    /**
     * The offset of the last components to the border to be left blank.
     *
     * Default is 0.
     */
    public int OFFSET_BUTTOM = 0;

    /**
     * The size of empty space between two components in the comtainer.
     *
     * Default is 5.
     */
    public int OFFSET_BETWEEN_COMPONENTS = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLayoutComponent(Component comp) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int width = getParentContainerWidth(parent) - 50;
        int height = OFFSET_TOP + OFFSET_BUTTOM + parent.getInsets().top + parent.getInsets().bottom;
        Component[] comps = parent.getComponents();

        if (comps.length > 0) {
            height += (comps.length - 1) * OFFSET_BETWEEN_COMPONENTS;
        }
        for (Component comp : comps) {
            height += comp.getPreferredSize().height;
        }

        return new Dimension(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        int width = 100;
        int height = OFFSET_TOP + OFFSET_BUTTOM + parent.getInsets().top + parent.getInsets().bottom;
        Component[] comps = parent.getComponents();

        if (comps.length > 0) {
            height += (comps.length - 1) * OFFSET_BETWEEN_COMPONENTS;
        }
        for (Component comp : comps) {
            height += comp.getMinimumSize().height;
        }

        return new Dimension(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void layoutContainer(Container parent) {
        Component[] comps = parent.getComponents();
        int compStartHeight = OFFSET_TOP + parent.getInsets().left;
        int width = getParentContainerWidth(parent) - OFFSET_LEFT - OFFSET_RIGHT - parent.getInsets().left - parent.getInsets().right;

        for (Component comp : comps) {
            int height = comp.getPreferredSize().height;

            comp.setBounds(OFFSET_LEFT, compStartHeight, width, height);
            compStartHeight += height;
            compStartHeight += OFFSET_BETWEEN_COMPONENTS;
        }
    }

    /**
     * Gets the width of the parent of given container. Normally the given
     * container is the container to be layouted. For it to be steched over the
     * full width of the parent container, the size has to be known.
     *
     * @param thisContainer
     * @return
     */
    private int getParentContainerWidth(Container thisContainer) {
        return thisContainer.getWidth();
    }

}
