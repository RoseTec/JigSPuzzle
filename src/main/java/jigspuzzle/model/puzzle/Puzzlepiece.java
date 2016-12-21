package jigspuzzle.model.puzzle;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import jigspuzzle.util.ImageUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A class for representing a puzzlepiece.
 *
 * @author RoseTec
 */
public class Puzzlepiece extends AbstractPuzzlesModel {

    /**
     * Creates a PuzzlepieceGroup from the given file
     *
     * @param settingsNode
     * @param group
     * @return
     * @throws IOException
     * @see #loadFromFile(org.w3c.dom.Element)
     */
    public static Puzzlepiece createFromFile(Element settingsNode, PuzzlepieceGroup group) throws IOException {
        Puzzlepiece piece = new Puzzlepiece(group);
        piece.loadFromFile(settingsNode);
        return piece;
    }

    /**
     * The Images that this piece has.
     */
    private BufferedImage image;

    /**
     * The group in which this puzzlepiece is contained.
     */
    private PuzzlepieceGroup group;

    /**
     * The connectors to the puzzlepiece in the given direction to this one. Can
     * be null if it is not connectoed to a puzzlepiece to that direction.
     */
    private PuzzlepieceConnection[] connectors = null;

    public Puzzlepiece(PuzzlepieceGroup group) {
        connectors = new PuzzlepieceConnection[ConnectorPosition.numberOfElements()];
        this.group = group;
    }

    public Puzzlepiece(BufferedImage img) {
        //TODO: refactor that puzzlepiece is contained in the group ('no piece without a group'...)
        connectors = new PuzzlepieceConnection[ConnectorPosition.numberOfElements()];
        this.image = img;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.image);
        hash = 31 * hash + Arrays.deepHashCode(this.connectors);
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
        final Puzzlepiece other = (Puzzlepiece) obj;
        if (!ImageUtil.imagesAreEqual(this.image, other.image)) {
            return false;
        }
        for (int i = 0; i < this.connectors.length; i++) {
            if (this.connectors[i] == null && other.connectors[i] == null) {
                continue;
            }
            if (!this.connectors[i].equals(other.connectors[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a connector to the other puzzlepiece.
     *
     * @param otherPiece
     * @param position the position where <b>the other</b> puzzlepiece is <b>in
     * comparision to this</b> puzzlepiece.
     * @return <code>true</code>, if the connection is creates succesfully, else
     * <code>false</code>.
     */
    boolean createConnectorToPiece(Puzzlepiece otherPiece, ConnectorPosition position) {
        // test for valid connecters
        if (connectors[position.intValue()] != null
                || connectors[position.getOpposite().intValue()] != null) {
            return false;
        }

        // create connection
        PuzzlepieceConnection connection = new PuzzlepieceConnection(new Puzzlepiece[]{this, otherPiece});

        // add connection to this model and the other piece
        this.connectors[position.intValue()] = connection;
        otherPiece.connectors[position.getOpposite().intValue()] = connection;

        return true;
    }

    @Override
    public void loadFromFile(Element settingsNode) throws IOException {
        NodeList list;

        if (!"puzzlepiece".equals(settingsNode.getNodeName())) {
            return;
        }
        list = settingsNode.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Element node = (Element) list.item(i);

            switch (node.getNodeName()) {
                case "img":
                    Image img = this.loadImageFromElement(node);
                    image = ImageUtil.transformImageToBufferedImage(img);
                    break;
                case "connectors":
                    NodeList childs = node.getChildNodes();
                    int n = childs.getLength();

                    for (int i2 = 0; i2 < n; i2++) {
                        Element positionElem = (Element) childs.item(i2);
                        int id, position;
                        boolean isInPuzzlepiece;

                        if (!"connector".equals(positionElem.getNodeName())) {
                            continue;
                        }
                        id = Integer.parseInt(positionElem.getTextContent());
                        position = Integer.parseInt(positionElem.getAttribute("position"));
                        isInPuzzlepiece = Boolean.parseBoolean(positionElem.getAttribute("is-in"));

                        connectors[position] = group.getPuzzle().getPuzzlepieceConnectionWithId(id);
                        if (isInPuzzlepiece) {
                            connectors[position].setInPuzzlepiece(this);
                        } else {
                            connectors[position].setOutPuzzlepiece(this);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void saveToFile(Document doc, Element rootElement) throws IOException {
        Element element = doc.createElement("puzzlepiece");
        rootElement.appendChild(element);

        Element tmpElement, tmpElement2;
        tmpElement = doc.createElement("img");
        this.saveImageToElement(tmpElement, image);
        element.appendChild(tmpElement);

        // puzzlepiecegroup is done in PuzzlepieceGroup
        tmpElement = doc.createElement("connectors");
        for (ConnectorPosition position : ConnectorPosition.values()) {
            if (connectors[position.intValue()] == null) {
                continue;
            }
            tmpElement2 = doc.createElement("connector");
            tmpElement2.setAttribute("position", String.valueOf(position.intValue()));
            tmpElement2.setAttribute("is-in", String.valueOf(isInPieceInDirection(position)));
            tmpElement2.setTextContent(String.valueOf(connectors[position.intValue()].getId()));
            tmpElement.appendChild(tmpElement2);
        }
        element.appendChild(tmpElement);
    }

    /**
     * Gets the Connection to the given direction
     *
     * @param direction
     * @return
     */
    public PuzzlepieceConnection getConnectorForDirection(ConnectorPosition direction) {
        return connectors[direction.intValue()];
    }

    /**
     * Returns the images that this puzzlepiece holds.
     *
     * @return
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Gets, if this piece is an 'in-connector'-puzzlepiece
     *
     * @see PuzzlepieceConnection
     * @param direction
     * @return
     */
    public boolean isInPieceInDirection(ConnectorPosition direction) {
        if (connectors[direction.intValue()] == null) {
            return false;
        }
        return connectors[direction.intValue()].getInPuzzlepiece() == this;
    }

    /**
     * Gets, if this piece is an 'out-connector'-puzzlepiece
     *
     * @see PuzzlepieceConnection
     * @param direction
     * @return
     */
    public boolean isOutPieceInDirection(ConnectorPosition direction) {
        if (connectors[direction.intValue()] == null) {
            return false;
        }
        return connectors[direction.intValue()].getOutPuzzlepiece() == this;
    }

    /**
     * Gets the puzzlepiece group in that this piec is contained in.
     *
     * @return
     */
    public PuzzlepieceGroup getPuzzlepieceGroup() {
        return group;
    }

    /**
     * Sets the puzzlepiece group in that this piec is contained in.
     *
     * @param group
     */
    void setPuzzlepieceGroup(PuzzlepieceGroup group) {
        this.group = group;
    }

}
