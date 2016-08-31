package jigspuzzle.model.settings;

import java.io.IOException;
import java.util.Observable;
import jigspuzzle.controller.Savable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The settings for the appearence of the puzzle area are stored here.
 *
 * @author RoseTec
 */
public class PuzzleSettings extends Observable implements Savable {

    /**
     * The numbr of puzzlepieces that a new puzzle should have
     */
    private int puzzlepieceNumber = 100;

    /**
     * Gets the numbr of puzzlepieces that a new puzzle should have.
     *
     * @return
     */
    public int getPuzzlepieceNumber() {
        return puzzlepieceNumber;
    }

    /**
     * Sets the numbr of puzzlepieces that a new puzzle should have.
     *
     * @param newNumber
     */
    public void setPuzzlepieceNumber(int newNumber) {
        puzzlepieceNumber = newNumber;
        setChanged();
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromFile(Element settingsNode) throws IOException {
        Node thisSettingsNode = settingsNode.getElementsByTagName("puzzle-settings").item(0);
        NodeList list;

        if (thisSettingsNode == null) {
            return;
        }
        list = thisSettingsNode.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            switch (node.getNodeName()) {
                case "puzzlepiece-number":
                    try {
                        puzzlepieceNumber = Integer.parseInt(node.getTextContent());
                    } catch (NumberFormatException ex) {
                    }
            }
        }

        setChanged();
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveToFile(Document doc, Element rootElement) throws IOException {
        Element settingsElement = doc.createElement("puzzle-settings");
        Element tmpElement;

        rootElement.appendChild(settingsElement);

        tmpElement = doc.createElement("puzzlepiece-number");
        tmpElement.setTextContent(Integer.toString(puzzlepieceNumber));
        settingsElement.appendChild(tmpElement);
    }

}
