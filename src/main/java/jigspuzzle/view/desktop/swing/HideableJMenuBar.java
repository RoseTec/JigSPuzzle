package jigspuzzle.view.desktop.swing;

import java.awt.Dimension;
import javax.swing.JMenuBar;

/**
 * source:
 * http://stackoverflow.com/questions/18093773/jmenubar-selectionmodel-changelistener-only-fires-once/18097498#18097498
 *
 * A JMenuBar that can be hidden. When the menubar is hidden, all Keystrokes are
 * still working.
 *
 * @author RoseTec
 */
public class HideableJMenuBar extends JMenuBar {

    private boolean hidden;

    public void setHidden(boolean hidden) {
        if (this.hidden == hidden) {
            return;
        }
        this.hidden = hidden;
        revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension pref = super.getPreferredSize();
        if (hidden) {
            pref.height = 0;
        }
        return pref;
    }

}
