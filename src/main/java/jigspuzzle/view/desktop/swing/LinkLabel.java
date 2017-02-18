package jigspuzzle.view.desktop.swing;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * A link label is a component with a hyperlink behind it. It can be clicked so
 * tht a link will open.
 *
 * @author RoseTec
 */
public class LinkLabel extends JLabel {

    private URI link;

    public LinkLabel(String link) throws URISyntaxException {
        this(new URI(link));
    }

    public LinkLabel(URI link) {
        this.link = link;
        init();
    }

    public LinkLabel() {
        this((URI) null);
    }

    private void init() {
        if (this.link != null) {
            this.setText(this.link.getPath());
        }

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(link);
                } catch (IOException ex) {
                    //todo: error handling
                }
            }
        });
    }

    /**
     * Sets the link for this link label.
     *
     * @param link
     * @throws URISyntaxException
     */
    public void setLink(String link) throws URISyntaxException {
        this.setLink(new URI(link));
    }

    /**
     * Sets the link for this link label.
     *
     * @param link
     */
    public void setLink(URI link) {
        String oldLink = null;

        if (this.link != null) {
            oldLink = this.link.getPath();
        }
        this.link = link;
        updateText(oldLink);
    }

    /**
     * Updates the text of this label to the new link, when the current text
     * contains only the link.
     *
     * @param oldLink
     */
    private void updateText(String oldLink) {
        if (oldLink != null && this.getText().equals(oldLink)) {
            this.setText(this.link.getPath());
        }
    }

}
