package jigspuzzle.view.desktop.swing;

import java.awt.Dimension;
import javax.swing.Action;
import javax.swing.Icon;

/**
 * A class for a nicer JMenuItem.
 *
 * @author RoseTec
 */
public class JMenuItem extends javax.swing.JMenuItem {

    public JMenuItem() {
        init();
    }

    public JMenuItem(Icon icon) {
        super(icon);
        init();
    }

    public JMenuItem(String text) {
        super(text);
        init();
    }

    public JMenuItem(Action a) {
        super(a);
        init();
    }

    public JMenuItem(String text, Icon icon) {
        super(text, icon);
        init();
    }

    public JMenuItem(String text, int mnemonic) {
        super(text, mnemonic);
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setText(String text) {
        super.setText("" + text + "        ");
        this.setPreferredSize(new Dimension(text.length() * 7 + 80, 30));
    }

    private void init() {
        this.setFont(new java.awt.Font("Segoe UI", 0, 13));
    }
    
}
