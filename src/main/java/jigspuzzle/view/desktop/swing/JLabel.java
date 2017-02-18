package jigspuzzle.view.desktop.swing;

import javax.swing.Icon;

/**
 * A class for a nicer JLabel.
 *
 * @author RoseTec
 */
public class JLabel extends javax.swing.JLabel {

    public JLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    public JLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public JLabel(String text) {
        super(text);
    }

    public JLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public JLabel(Icon image) {
        super(image);
    }

    public JLabel() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setText(String text) {
        super.setText(makeHtmlText(text));
    }

    /**
     * Makes a String out of the given text, that is conteined in hml-tags.
     *
     * @param text
     * @return
     */
    private String makeHtmlText(String text) {
        if (text.length() == 0) {
            return text;
        }

        String ret;

        // make html-string
        ret = text.startsWith("<html>") ? text : "<html>" + text + "</html>";

        // replace special chars
        ret = ret.replaceAll("\\\\n", "<br/>");

        return ret;
    }

}
