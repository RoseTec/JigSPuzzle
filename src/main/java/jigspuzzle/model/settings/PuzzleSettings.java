package jigspuzzle.model.settings;

import java.io.IOException;
import java.util.Observable;
import jigspuzzle.model.Savable;
import jigspuzzle.model.puzzle.ConnectorShapeFactory;
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
     * The id of the shape for the puzzlepiece connectors. This value is only
     * used, if <code>useRandomConnectorShape == false</code>.
     */
    private int puzzlepieceConnectorShapeId = ConnectorShapeFactory.getInstance().getAllConnectorShapeIds().get(0);

    /**
     * The number of puzzlepieces that a new puzzle should have.
     */
    private int puzzlepieceNumber = 100;

    /**
     * The distance in percentage of the size of a puzzlepiec, that two
     * puzzlepieces must have at most, so that they can snap together and create
     * a group.
     */
    private int snapDistancePercent = 20;

    /**
     * Indicates, wheather random connector shapes should be used for the
     * puzzlepiece connectors. If no random shapes are used, the value from
     * <code>puzzlepieceConnectorShapeId</code> is used as shape id.
     */
    private boolean useRandomConnectorShape = true;

    /**
     * The id of the shape for the puzzlepiece connectors. This value is only
     * used, if <code>getUseRandomConnectorShape() == false</code>.
     *
     * @return
     * @see #getUseRandomConnectorShape()
     */
    public int getPuzzlepieceConnectorShapeId() {
        return puzzlepieceConnectorShapeId;
    }

    /**
     * The id of the shape for the puzzlepiece connectors. This value is only
     * used, if <code>getUseRandomConnectorShape() == false</code>.
     *
     * @param puzzlepieceConnectorShapeId
     * @see #getUseRandomConnectorShape()
     */
    public void setPuzzlepieceConnectorShapeId(int puzzlepieceConnectorShapeId) {
        this.puzzlepieceConnectorShapeId = puzzlepieceConnectorShapeId;
        if (!this.useRandomConnectorShape) {
            // we need to update the listeners only, when we have 'new' information
            // because, the connector shape is only used in settings and has no
            // affect on the puzzle to show, we do not need to notify observers.
            setChanged();
            notifyObservers();
        }
    }

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
        if (newNumber > 0) {
            puzzlepieceNumber = newNumber;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Gets the distance in percentage of the size of a puzzlepiec, that two
     * puzzlepieces must have at most, so that they can snap together and create
     * a group.
     *
     * @return
     */
    public int getSnapDistancePercent() {
        return snapDistancePercent;
    }

    /**
     * Sets the distance in percentage of the size of a puzzlepiec, that two
     * puzzlepieces must have at most, so that they can snap together and create
     * a group.
     *
     * @param snapDistancePercent
     */
    public void setSnapDistancePercent(int snapDistancePercent) {
        this.snapDistancePercent = snapDistancePercent;
    }

    /**
     * Indicates, wheather random connector shapes should be used for the
     * puzzlepiece connectors. If no random shapes are used, the value from
     * <code>getPuzzlepieceConnectorShapeId()</code> is used as shape id.
     *
     * @return
     * @see #getPuzzlepieceConnectorShapeId()
     */
    public boolean getUseRandomConnectorShape() {
        return useRandomConnectorShape;
    }

    /**
     * Indicates, wheather random connector shapes should be used for the
     * puzzlepiece connectors. If no random shapes are used, the value from
     * <code>getPuzzlepieceConnectorShapeId()</code> is used as shape id.
     *
     * @param useRandomConnectorShape
     * @see #getPuzzlepieceConnectorShapeId()
     */
    public void setUseRandomConnectorShape(boolean useRandomConnectorShape) {
        this.useRandomConnectorShape = useRandomConnectorShape;
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
                    break;
                case "snap-distance-percent":
                    try {
                        snapDistancePercent = Integer.parseInt(node.getTextContent());
                    } catch (NumberFormatException ex) {
                    }
                    break;
                case "use-random-connector-shape":
                    useRandomConnectorShape = Boolean.parseBoolean(node.getTextContent());
                    break;
                case "puzzlepiece-connector-shape-id":
                    try {
                        puzzlepieceConnectorShapeId = Integer.parseInt(node.getTextContent());
                    } catch (NumberFormatException ex) {
                    }
                    break;
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

        tmpElement = doc.createElement("snap-distance-percent");
        tmpElement.setTextContent(Integer.toString(snapDistancePercent));
        settingsElement.appendChild(tmpElement);

        tmpElement = doc.createElement("use-random-connector-shape");
        tmpElement.setTextContent(Boolean.toString(useRandomConnectorShape));
        settingsElement.appendChild(tmpElement);

        tmpElement = doc.createElement("puzzlepiece-connector-shape-id");
        tmpElement.setTextContent(Integer.toString(puzzlepieceConnectorShapeId));
        settingsElement.appendChild(tmpElement);
    }

}
