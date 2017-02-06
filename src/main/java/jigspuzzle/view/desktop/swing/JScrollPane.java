package jigspuzzle.view.desktop.swing;

/**
 * A class for a nicer JScrollPane.
 *
 * @author RoseTec
 */
public class JScrollPane extends javax.swing.JScrollPane {

    public JScrollPane() {
        init();
    }

    private void init() {
        this.getVerticalScrollBar().setUnitIncrement(10);
        this.getHorizontalScrollBar().setUnitIncrement(5);
    }

}
