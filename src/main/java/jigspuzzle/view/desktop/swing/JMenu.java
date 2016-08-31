package jigspuzzle.view.desktop.swing;

import java.awt.Dimension;
import javax.swing.Action;

/**
 * A class for a nicer JMenu.
 *
 * @author RoseTec
 */
public class JMenu extends javax.swing.JMenu {

    public JMenu() {
        init();
    }

    public JMenu(String s) {
        super(s);
        init();
    }

    public JMenu(Action a) {
        super(a);
        init();
    }

    public JMenu(String s, boolean b) {
        super(s, b);
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setText(String text) {
        String newText = "  " + text + "    ";
        
        super.setText(newText);
        this.setPreferredSize(new Dimension(text.length() * 7 + 25, 30));
    }

    private void init() {
        this.setFont(new java.awt.Font("Segoe UI", 0, 13));
    }

}
