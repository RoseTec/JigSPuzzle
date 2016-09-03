package jigspuzzle.model.puzzle;

import java.awt.geom.Path2D;
import java.util.Objects;
import java.util.Random;

/**
 * A class for modeling the connection between two puzzlepieces.
 *
 * Two puzzle pieces are connected with a Connector, that has a special shaps
 * for displaying.
 *
 * For example in thefollowing picture we see two puzzle pieces, that are
 * connected by a connector, that is displayed by a line between the
 * puzzlepiecs. For one puzzlepiece the connector is going in the ouzzlepiece
 * and for the other puzzlepiece the connector is going outside.
 * <pre>
 * +-------+-------+
 * |   /-\_|       |
 * |   |  _        |
 * |   \-/ |       |
 * +-------+-------+
 *    in      out
 * </pre>
 *
 * The connector is an 'in-Connector' for one puzzlepiece, in that the connector
 * goes in. It is an 'out-connector' for the puzzlepiecefrom that the connector
 * goes out, such that the puzzlepiece has also a part of the
 * 'in-connector'-puzzlepiece to show.
 *
 * @author RoseTec
 */
public class PuzzlepieceConnection {

    /**
     * The shape of this Connector
     */
    private Path2D shape;

    /**
     * The 'in-connector'-puzzlepiece
     */
    private Puzzlepiece inPuzzlepiece;

    /**
     * The 'out-connector'-puzzlepiece
     */
    private Puzzlepiece outPuzzlepiece;

    /**
     * @param pieces The <b>two</b> puzzlepieces. It will be random, which
     * puzzlepiece is the 'in-Connector'-puzzlepiece and which is the
     * 'out-Connector'-puzzlepiece.
     */
    public PuzzlepieceConnection(Puzzlepiece[] pieces) {
        Random rn = new Random();
        int inPieceIndex;
        int outPieceIndex;

        inPieceIndex = rn.nextInt(2);
        outPieceIndex = inPieceIndex == 0 ? 1 : 0;
        initConnector(pieces[inPieceIndex], pieces[outPieceIndex]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.shape);
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
        final PuzzlepieceConnection other = (PuzzlepieceConnection) obj;
        if (!Objects.equals(this.shape, other.shape)) {
            return false;
        }
        if (this.inPuzzlepiece != other.inPuzzlepiece) {
            return false;
        }
        if (this.outPuzzlepiece != other.outPuzzlepiece) {
            return false;
        }
        return true;
    }

    /**
     * @param in The 'in-Connector'-puzzlepiece
     * @param out The 'out-Connector'-puzzlepiece
     */
    public PuzzlepieceConnection(Puzzlepiece in, Puzzlepiece out) {
        initConnector(in, out);
    }

    /**
     * Gets the 'in-connector'-puzzlepiece.
     *
     * @return
     */
    public Puzzlepiece getInPuzzlepiece() {
        return inPuzzlepiece;
    }

    /**
     * Gets the 'outconnector'-puzzlepiece.
     *
     * @return
     */
    public Puzzlepiece getOutPuzzlepiece() {
        return outPuzzlepiece;
    }

    /**
     * Returns the shape of this connection.
     *
     * Shapes have allways the following properties: <br>
     * It is assumed, that a puzzlepiece starts at point (0,0) and ends at point
     * (0,100). This means a puzzlepiece has a height of 100.<br>
     * Now we draw the shape to the right. Means we start somewhere at
     * point(0,y) and draw something to the right with then y>0.<br>
     * After drawing all we end somewhere at point (0,y').
     *
     * @return
     */
    public Path2D getShape() {
        return shape;
    }

    private void initConnector(Puzzlepiece in, Puzzlepiece out) {
        this.inPuzzlepiece = in;
        this.outPuzzlepiece = out;

        // create shape
        this.shape = ConnectoryShapeFactory.getInstance().createShape();
    }

}
