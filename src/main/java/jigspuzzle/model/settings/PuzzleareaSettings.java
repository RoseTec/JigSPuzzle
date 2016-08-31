package jigspuzzle.model.settings;

import java.awt.Color;
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
public class PuzzleareaSettings extends Observable implements Savable {

    /**
     * The backgroundcolor, that the puzzleare has.
     */
    private Color puzzleareaBackgroundColor = Color.WHITE;

    /**
     * The value, if the puzzle should automatically be decreased, if the puzzle
     * does not fit into the puzzleare.
     */
    private boolean decreasePuzzleAutomatically = true;

    /**
     * The value, if the puzzle should automatically be enlarged, so that the
     * puzzle fits into the puzzleare.
     */
    private boolean enlargePuzzleAutomatically = true;

    /**
     * The number shows in percent, how much of the puzzleare should be used for
     * puzzeling. A number of 0.5 for instance means, that only 50% of the
     * puzzelarea should be used for the final puzzle.
     */
    private double usedSizeOfPuzzleare = 1;

    /**
     * Gets the value for automatically decrease the size of the puzzle, if it
     * does not fit into the puzzleare, which displays the puzzle.
     *
     * @return
     */
    public boolean getDecreasePuzzleAutomatically() {
        return decreasePuzzleAutomatically;
    }

    /**
     * Sets the value for automatically decrease the size of the puzzle, if it
     * does not fit into the puzzleare, which displays the puzzle.
     *
     * @param value
     */
    public void setDecreasePuzzleAutomatically(boolean value) {
        if (decreasePuzzleAutomatically != value) {
            decreasePuzzleAutomatically = value;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Gets the value for automatically enlarge the size of the puzzle, so that
     * it fits into the puzzlearea.
     *
     * @return
     */
    public boolean getEnlargePuzzleAutomatically() {
        return enlargePuzzleAutomatically;
    }

    /**
     * Sets the value for automatically enlarge the size of the puzzle, so that
     * it fits into the puzzlearea.
     *
     * @param value
     */
    public void setEnlargePuzzleAutomatically(boolean value) {
        if (enlargePuzzleAutomatically != value) {
            enlargePuzzleAutomatically = value;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Gets the bckground color for the puzzlearea where the player playes
     * around with the puzzlepieces.
     *
     * @return
     */
    public Color getPuzzleareaBackgroundColor() {
        return puzzleareaBackgroundColor;
    }

    /**
     * Sets the bckground color for the puzzlearea where the player playes
     * around with the puzzlepieces.
     *
     * @param newColor
     */
    public void setPuzzleareaBackgroundColor(Color newColor) {
        puzzleareaBackgroundColor = newColor;
        setChanged();
        notifyObservers();
    }

    /**
     * Gets the number in percent, how much of the puzzleare should be used for
     * puzzeling. A number of 0.5 for instance means, that only 50% of the
     * puzzelarea should be used for the final puzzle.
     *
     * @return
     */
    public double getUsedSizeOfPuzzleare() {
        return usedSizeOfPuzzleare;
    }

    /**
     * Sets the number in percent, how much of the puzzleare should be used for
     * puzzeling. A number of 0.5 for instance means, that only 50% of the
     * puzzelarea should be used for the final puzzle.
     *
     * @param number
     */
    public void setUsedSizeOfPuzzleare(double number) {
        usedSizeOfPuzzleare = number;
        setChanged();
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromFile(Element settingsNode) throws IOException {
        Node thisSettingsNode = settingsNode.getElementsByTagName("puzzlearea-settings").item(0);
        NodeList list;

        if (thisSettingsNode == null) {
            return;
        }
        list = thisSettingsNode.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            switch (node.getNodeName()) {
                case "automatically-decrease-puzzle":
                    try {
                        decreasePuzzleAutomatically = Boolean.parseBoolean(node.getTextContent());
                    } catch (NumberFormatException ex) {
                    }
                case "automatically-enlarge-puzzle":
                    try {
                        enlargePuzzleAutomatically = Boolean.parseBoolean(node.getTextContent());
                    } catch (NumberFormatException ex) {
                    }
                case "background-color":
                    try {
                        puzzleareaBackgroundColor = new Color(Integer.parseInt(node.getTextContent()));
                    } catch (NumberFormatException ex) {
                    }
                case "used-size-of-puzzleare":
                    try {
                        usedSizeOfPuzzleare = Double.parseDouble(node.getTextContent());
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
        Element settingsElement = doc.createElement("puzzlearea-settings");
        Element tmpElement;

        rootElement.appendChild(settingsElement);

        tmpElement = doc.createElement("automatically-decrease-puzzle");
        tmpElement.setTextContent(String.valueOf(decreasePuzzleAutomatically));
        settingsElement.appendChild(tmpElement);

        tmpElement = doc.createElement("automatically-enlarge-puzzle");
        tmpElement.setTextContent(String.valueOf(enlargePuzzleAutomatically));
        settingsElement.appendChild(tmpElement);

        tmpElement = doc.createElement("background-color");
        tmpElement.setTextContent(String.valueOf(puzzleareaBackgroundColor.getRGB()));
        settingsElement.appendChild(tmpElement);

        tmpElement = doc.createElement("used-size-of-puzzleare");
        tmpElement.setTextContent(String.valueOf(usedSizeOfPuzzleare));
        settingsElement.appendChild(tmpElement);
    }

}
