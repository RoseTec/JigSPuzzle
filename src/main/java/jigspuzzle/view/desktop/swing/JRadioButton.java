package jigspuzzle.view.desktop.swing;

/**
 * A class for a nicer JRadioButton.
 *
 * @author RoseTec
 */
public class JRadioButton extends javax.swing.JRadioButton {

    public JRadioButton() {
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setText(String text) {
        super.setText(makeHtmlText(text));
    }

    private void init() {
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
