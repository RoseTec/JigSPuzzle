package jigspuzzle.model.puzzle;

import java.awt.geom.Path2D;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class represents a shape for a PuzzlepieceConnection.
 *
 * A connector shape is created by a puzzlepieceConnection via the class
 * <code>ConnectorShapeFactory</code>
 *
 * @author RoseTec
 * @see ConnectorShapeFactory#createShape()
 */
public abstract class ConnectorShape extends AbstractPuzzlesModel {

    /**
     * Creates a puzzle from the given file
     *
     * @param settingsNode
     * @return
     * @throws IOException
     * @see #loadFromFile(org.w3c.dom.Element)
     */
    public static ConnectorShape createFromFile(Element settingsNode) throws IOException {
        if (!"shape".equals(settingsNode.getNodeName())) {
            return null;
        }
        int id = Integer.parseInt(settingsNode.getAttribute("id"));
        return ConnectorShapeFactory.getInstance().getConnectorShapeWithId(id);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.getId();
        return hash;
    }

    /**
     * {@inheritDoc}
     *
     * @param obj
     * @return
     */
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
     * Returns the shape that is used to outline the PuzzlepieceConnection
     *
     * TODO: abstract from Path2D?
     *
     * @return
     */
    public abstract Path2D getShape();

    /**
     * {@inheritDoc}
     *
     * @param settingsNode
     * @throws IOException
     */
    @Override
    public void loadFromFile(Element settingsNode) throws IOException {
        throw new UnsupportedOperationException("ConnectorShaps cannot be loaded. Use #createFromFile() for this.");
    }

    /**
     * {@inheritDoc}
     *
     * @param doc
     * @param rootElement
     * @throws IOException
     */
    @Override
    public void saveToFile(Document doc, Element rootElement) throws IOException {
        Element element = doc.createElement("shape");
        rootElement.appendChild(element);

        saveIdToElement(element);
    }

}
