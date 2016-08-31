package jigspuzzle.view.desktop.settings;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * A JLabel, that has a special color, such that it is recognised as a label
 * that explaines some other things.
 *
 * @author RoseTec
 */
public class ExplainingJLabel extends JLabel {

    public ExplainingJLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        init();
    }

    public ExplainingJLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        init();
    }

    public ExplainingJLabel(String text) {
        super(text);
        init();
    }

    public ExplainingJLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        init();
    }

    public ExplainingJLabel(Icon image) {
        super(image);
        init();
    }

    public ExplainingJLabel() {
        init();
    }

    @Override
    public void setText(String text) {
        super.setText(makeHtmlText(text));
    }

    private void init() {
        this.setForeground(Color.GRAY);
    }

    /**
     * Makes a String out of the given text, that is conteined in hml-tags.
     *
     * @param text
     * @return
     */
    private String makeHtmlText(String text) {
        if (text.startsWith("<html>")) {
            return text;
        } else {
            return "<html>" + text + "</html>";
        }
    }

}
