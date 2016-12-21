package jigspuzzle.model.puzzle;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class represents a shape for a PuzzlepieceConnection
 *
 * @author RoseTec
 */
public class ConnectorShape extends AbstractPuzzlesModel {

    /**
     * Creates a puzzle from the given file
     *
     * @param settingsNode
     * @return
     * @throws IOException
     * @see #loadFromFile(org.w3c.dom.Element)
     */
    public static ConnectorShape createFromFile(Element settingsNode) throws IOException {
        ConnectorShape connection = new ConnectorShape();
        connection.loadFromFile(settingsNode);
        return connection;
    }

    /**
     * The actual shape for this ConnectionShape
     *
     * // TODO: abstract from this?
     */
    private Path2D shape;

    protected ConnectorShape() {
        // TODO: create more shapes...
        shape = new GeneralPath();

        shape.moveTo(0, 40);
        shape.lineTo(15, 30);
        shape.lineTo(25, 50);
        shape.lineTo(15, 70);
        shape.lineTo(0, 60);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.getId();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConnectorShape other = (ConnectorShape) obj;
        if (this.getId() != other.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Returns the shape that is used to outloine the PuzzlepieceConnection
     *
     * @return
     */
    public Path2D getShape() {
        return new GeneralPath(shape);
    }

    void setShape(Path2D shape) {
        this.shape = shape;
    }

    @Override
    public void loadFromFile(Element settingsNode) throws IOException {
        if (!"shape".equals(settingsNode.getNodeName())) {
            return;
        }
        this.loadIdFromElement(settingsNode);
        shape = ConnectorShapeFactory.getInstance().getShapeWithId(this.getId()).shape;
    }

    @Override
    public void saveToFile(Document doc, Element rootElement) throws IOException {
        Element element = doc.createElement("shape");
        rootElement.appendChild(element);

        saveIdToElement(element);
    }

}
