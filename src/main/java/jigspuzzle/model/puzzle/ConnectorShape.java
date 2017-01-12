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
     * The shape that this connectorShae has. It is created in the subclasses.
     */
    private final Path2D shape;

    public ConnectorShape() {
        shape = constructShape();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.getId();
        return hash;
    }

    /**
     * {@inheritDoc}
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
     * Creates the shape. Each subclass creaes a different shape in this method.
     * This method is called in the construcror of this method. At this point, a
     * subclass is asked to create a special shape. This shape is saved and
     * returned, when requested via <code>getShape()</code>.
     *
     * @return
     * @see #getShape()
     */
    protected abstract Path2D constructShape();

    /**
     * Returns the shape that is used to outline the PuzzlepieceConnection
     *
     * TODO: abstract from Path2D?
     *
     * @return
     */
    public Path2D getShape() {
        return shape;
    }

    /**
     * Implementation of the abstract method of the superclass.
     *
     * <b>This method is not supported.</b> Use <code>createFromFile()</code>
     * instead.
     *
     * @param settingsNode
     * @throws IOException
     * @see AbstractPuzzlesModel#loadFromFile(org.w3c.dom.Element)
     * @see #createFromFile(org.w3c.dom.Element)
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
