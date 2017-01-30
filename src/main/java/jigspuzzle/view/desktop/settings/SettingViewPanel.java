package jigspuzzle.view.desktop.settings;

import java.awt.Dimension;

/**
 * A panel designed to be added in a JTabbedPane in the settings view
 *
 * @author RoseTec
 */
class SettingViewPanel extends javax.swing.JPanel {

    /**
     * Creates new form SettingViewPanel
     */
    public SettingViewPanel() {
        TopToButtomLayoutManager layout = new TopToButtomLayoutManager();
        layout.OFFSET_BETWEEN_COMPONENTS = 7;
        layout.OFFSET_TOP = 5;
        layout.OFFSET_BUTTOM = 10;
        layout.OFFSET_LEFT = 7;
        layout.OFFSET_RIGHT = 5;
        setLayout(layout);
    }

    @Override
    public Dimension getMinimumSize() {
        return getLayout().minimumLayoutSize(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return getLayout().preferredLayoutSize(this);
    }

}
