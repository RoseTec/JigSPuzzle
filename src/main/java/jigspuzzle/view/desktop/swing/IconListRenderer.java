package jigspuzzle.view.desktop.swing;

import java.awt.Component;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * source: http://stackoverflow.com/a/17774895
 *
 * A renderer for a JComboBox that can display images and texts in a JComboBox.
 *
 * @author RoseTec
 */
public class IconListRenderer extends DefaultListCellRenderer {

    private Map<String, Icon> icons;

    /**
     * @param icons A mapping from the texts of one entry to the image that this
     * entry should have.
     */
    public IconListRenderer(Map<String, Icon> icons) {
        this.icons = icons;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        // Get icon to use for the list item value
        Icon icon = icons.get(value);

        // Set icon to display for value
        label.setIcon(icon);
        return label;
    }
}
