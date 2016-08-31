package jigspuzzle.controller;

import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An implementation of this class can be saved to a file on the harddrive. It
 * also can be loaded again from this file.
 *
 * @author RoseTec
 */
public interface Savable {

    /**
     * Loads again the content from the file, that is opened by the given
     * document.
     *
     * @param settingsNode
     * @throws java.io.IOException
     */
    public void loadFromFile(Element settingsNode) throws IOException;

    /**
     * Saves the current content to the file, that is opened by the given
     * document.
     *
     * @param doc
     * @param rootElement The root element, where a subclass can create elements
     * @throws java.io.IOException
     */
    public void saveToFile(Document doc, Element rootElement) throws IOException;

}
