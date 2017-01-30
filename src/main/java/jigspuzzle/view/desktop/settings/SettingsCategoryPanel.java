package jigspuzzle.view.desktop.settings;

import java.util.Observable;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import jigspuzzle.controller.SettingsController;

/**
 * A panel that is used for displaying a settings group. This group is
 * summerised in this panel and has has a certain name.
 *
 * The panel is streched over the full width of the parent, but it has only the
 * height that is needed for displaying all children in this panel.
 *
 * The children are layouted as follows: One child has one row assigned to it.
 * This row is streched ower the full width. To have several elements in one
 * row, it has to be contained in a additional panel.
 *
 * @author RoseTec
 */
class SettingsCategoryPanel extends JPanel {

    /**
     * Creates a new SettingsCategoryPanel
     *
     * @param languagePageId The name for this category to be displayed: the
     * pageId
     * @param languageTextId The name for this category to be displayed: the
     * textId
     * @see SettingsController#getLanguageText(int, int)
     */
    public SettingsCategoryPanel(int languagePageId, int languageTextId) {
        String categoryName = SettingsController.getInstance().getLanguageText(languagePageId, languageTextId);

        SettingsController.getInstance().addLanguageSettingsObserver((Observable o, Object arg) -> {
            String name = SettingsController.getInstance().getLanguageText(languagePageId, languageTextId);
            ((TitledBorder) getBorder()).setTitle(name);
        });
        setBorder(javax.swing.BorderFactory.createTitledBorder(categoryName));

        TopToButtomLayoutManager layout = new TopToButtomLayoutManager();
        layout.OFFSET_BETWEEN_COMPONENTS = 5;
        layout.OFFSET_TOP = 15;
        layout.OFFSET_BUTTOM = 5;
        layout.OFFSET_LEFT = 20;
        layout.OFFSET_RIGHT = 7;
        setLayout(layout);
    }

}
