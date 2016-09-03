package jigspuzzle.model.puzzle;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

/**
 * A class for representing a puzzlepiece.
 *
 * @author RoseTec
 */
public class Puzzlepiece {

    /**
     * The Images that this piece has.
     */
    private final BufferedImage image;

    /**
     * The group in which this puzzlepiece is contained.
     */
    private PuzzlepieceGroup group;

    /**
     * The connectors to the puzzlepiece in the given direction to this one. Can
     * be null if it is not connectoed to a puzzlepiece to that direction.
     */
    private PuzzlepieceConnection[] connectors = null;

    public Puzzlepiece(BufferedImage img) {
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
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (this.group != other.group) {
            return false;
        }
        if (!Arrays.deepEquals(this.connectors, other.connectors)) {
            return false;
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
