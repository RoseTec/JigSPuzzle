package jigspuzzle.model.puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import jigspuzzle.JigSPuzzle;

/**
 * A Puzzlepiece-group has one to many puzzlepieces. A puzzle consists only out
 * of This group. Inside the goup one or many puzzlepieces are contained and can
 * be displayed to the user.
 *
 * @author RoseTec
 */
public class PuzzlepieceGroup extends Observable {

    /**
     * All puzzlepieces are stored in this array. We agree on the following:
     *
     * The puzzlepieces in this group are strored according to the position in
     * the final puzzle in this array. That does <b>not</b> mean, that the
     * puzzlepiece (0,0) in this array is the toppest left puzzlepiece in the
     * final puzzle.<br>
     * However, the position (0,1) in this array belongs to the puzzlepiece that
     * is in the final puzzle right to the puzzlepiece on position (0,0). There
     * can be a puzzlepiece-group where the puzzlepiece to the top of position
     * (0,0) is contained.
     */
    private final ArrayList<ArrayList<Puzzlepiece>> puzzlepiecesList;

    /**
     * The x-coordinate of this group. It indicates where in the puzzlearea this
     * group lays.
     */
    private int x;

    /**
     * The y-coordinate of this group. It indicates where in the puzzlearea this
     * group lays.
     */
    private int y;

    /**
     * The puzzle in that thisgroup is contained.
     */
    private final Puzzle puzzle;

    PuzzlepieceGroup(Puzzle puzzle, Puzzlepiece puzzlepiece, int x, int y) {
        this.puzzle = puzzle;
        this.x = x;
        this.y = y;
        this.puzzlepiecesList = new ArrayList<>();
        this.addPuzzlepieceAtPosition(puzzlepiece, 0, 0);
        puzzlepiece.setPuzzlepieceGroup(this);
    }

    /**
     * Adds all the puzzlepieces from the given group to this group.
     *
     * @param otherGroup
     * @param connection The connection that has a puzzlepiece in this group and
     * a puzzlepiece in the other group.
     */
    public void addFromPuzzlepieceGroup(PuzzlepieceGroup otherGroup, PuzzlepieceConnection connection) {
        // Get the puzzlepiece of the connection in this group and in the other group
        Puzzlepiece pieceThis = null;
        Puzzlepiece pieceOther = null;

        for (Puzzlepiece piece : this.getPuzzlepieces()) {
            if (piece == connection.getInPuzzlepiece()
                    || piece == connection.getOutPuzzlepiece()) {
                pieceThis = piece;
            }
        }
        for (Puzzlepiece piece : otherGroup.getPuzzlepieces()) {
            if (piece == connection.getInPuzzlepiece()
                    || piece == connection.getOutPuzzlepiece()) {
                pieceOther = piece;
            }
        }

        // Get the direction of the other group
        ConnectorPosition directionOfOtherGroup = null;
        for (ConnectorPosition direction : ConnectorPosition.values()) {
            if (pieceThis.getConnectorForDirection(direction) == connection) {
                directionOfOtherGroup = direction;
                break;
            }
        }

        // calculate the position of the puzzlepieces in the groups
        int rPositionThis = 0, cPositionThis = 0; // The row and column position of the puzzlepiece in the connection inside this group
        int rPositionInOtherGroup = 0, cPositionInOtherGroup = 0; // The row and column position of the puzzlepiece in the connection inside the other group

        rPositionThis = getYPositionOfPieceInGroup(pieceThis);
        cPositionThis = getXPositionOfPieceInGroup(pieceThis);
        rPositionInOtherGroup = otherGroup.getYPositionOfPieceInGroup(pieceOther);
        cPositionInOtherGroup = otherGroup.getXPositionOfPieceInGroup(pieceOther);

        // calculate the position, that the piece in the other group would have in this group
        int rPositionOther = rPositionThis;
        int cPositionOther = cPositionThis;

        switch (directionOfOtherGroup) {
            case LEFT:
                cPositionOther -= 1;
                break;
            case RIGHT:
                cPositionOther += 1;
                break;
            case TOP:
                rPositionOther -= 1;
                break;
            case BUTTOM:
                rPositionOther += 1;
                break;
        }

        // adjust the dimensions of the arraylists if necessary
        if (rPositionOther - rPositionInOtherGroup < rPositionThis) {
            int toAdd = rPositionThis + rPositionInOtherGroup - rPositionOther;
            for (int i = 0; i < toAdd; i++) {
                puzzlepiecesList.add(0, new ArrayList<>());
            }
            y -= JigSPuzzle.getInstance().getPuzzleWindow().getPuzzlepieceHeight() * toAdd;
            rPositionThis += toAdd;
            rPositionOther += toAdd;
        }
        if (cPositionOther - cPositionInOtherGroup < cPositionThis) {
            int toAdd = cPositionThis + cPositionInOtherGroup - cPositionOther;
            for (ArrayList<Puzzlepiece> row : puzzlepiecesList) {
                for (int i = 0; i < toAdd; i++) {
                    row.add(0, null);
                }
            }
            x -= JigSPuzzle.getInstance().getPuzzleWindow().getPuzzlepieceWidth() * toAdd;
            cPositionThis += toAdd;
            cPositionOther += toAdd;
        }

        // add the other pieces
        for (int rIndex = otherGroup.puzzlepiecesList.size() - 1; rIndex >= 0; rIndex--) {
            ArrayList<Puzzlepiece> row = otherGroup.puzzlepiecesList.get(rIndex);
            for (int cIndex = 0; cIndex < row.size(); cIndex++) {
                Puzzlepiece piece = row.get(cIndex);
                if (piece != null) {
                    addPuzzlepieceAtPosition(piece, rPositionOther + rIndex - rPositionInOtherGroup, cPositionOther + cIndex - cPositionInOtherGroup);
                }
            }
        }

        // rescue: Delete the first rows if there are no puzzlepieces
        // Sometimes after the 'merge' the first rows contain no elements.
        // Don't know, why they was added in first place... :(
        boolean firstRowOnlyNullValues = true;
        ArrayList<Puzzlepiece> firstRow;

        try {
            while (firstRowOnlyNullValues) {
                firstRow = puzzlepiecesList.get(0);
                for (Puzzlepiece piece : firstRow) {
                    if (piece != null) {
                        firstRowOnlyNullValues = false;
                        break;
                    }
                }
                if (firstRowOnlyNullValues) {
                    puzzlepiecesList.remove(0);
                    y += JigSPuzzle.getInstance().getPuzzleWindow().getPuzzlepieceHeight();
                }
            }
        } catch (IndexOutOfBoundsException ex) {
        }

        // rescue: Delete the first columns if all have there a 'null'
        // Sometimes after the 'merge' the first columns of the rows contains no elements.
        // Don't know, why they was added in first place... :(
        boolean firstColumnOnlyNullValues = true;

        try {
            while (firstColumnOnlyNullValues) {
                for (ArrayList<Puzzlepiece> row : puzzlepiecesList) {
                    if (row.get(0) != null) {
                        firstColumnOnlyNullValues = false;
                        break;
                    }
                }
                if (firstColumnOnlyNullValues) {
                    for (ArrayList<Puzzlepiece> row : puzzlepiecesList) {
                        row.remove(0);
                    }
                    x += JigSPuzzle.getInstance().getPuzzleWindow().getPuzzlepieceWidth();
                }
            }
        } catch (IndexOutOfBoundsException ex) {
        }

        // notify observers
        setChanged();
        notifyObservers();
    }

    /**
     * Destroyd this puzzlegroup. It does <b>not</b> destroy the containing
     * puzzlepieces.
     */
    public void destroy() {
        puzzlepiecesList.clear();
        puzzle.removePuzzlepieceGroup(this);
        setChanged();
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.puzzlepiecesList);
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
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
        final PuzzlepieceGroup other = (PuzzlepieceGroup) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (!Objects.equals(this.puzzlepiecesList, other.puzzlepiecesList)) {
            return false;
        }
        return true;
    }

    /**
     * Returns all puzzlepieces that are contained in this group.
     *
     * @return
     */
    public List<Puzzlepiece> getPuzzlepieces() {
        List<Puzzlepiece> list = new ArrayList<>();

        for (ArrayList<Puzzlepiece> row : puzzlepiecesList) {
            for (Puzzlepiece piece : row) {
                if (piece != null) {
                    list.add(piece);
                }
            }
        }

        return list;
    }

    /**
     * Gets all the puzzlepiece-connections in the given direction
     *
     * @param direction
     * @return
     */
    public List<PuzzlepieceConnection> getPuzzlepieceConnectionsInPosition(ConnectorPosition direction) {
        List<PuzzlepieceConnection> connections = new ArrayList<>();

        for (int rIndex = puzzlepiecesList.size() - 1; rIndex >= 0; rIndex--) {
            ArrayList<Puzzlepiece> row = puzzlepiecesList.get(rIndex);
            for (int cIndex = 0; cIndex < row.size(); cIndex++) {
                Puzzlepiece piece = row.get(cIndex);

                if (piece == null) {
                    continue;
                }
                try {
                    int otherRow = rIndex;
                    int otherColumn = cIndex;

                    switch (direction) {
                        case LEFT:
                            otherColumn -= 1;
                            break;
                        case RIGHT:
                            otherColumn += 1;
                            break;
                        case TOP:
                            otherRow -= 1;
                            break;
                        case BUTTOM:
                            otherRow += 1;
                            break;
                    }
                    if (puzzlepiecesList.get(otherRow).get(otherColumn) != null) {
                        continue;
                    }
                } catch (IndexOutOfBoundsException ex) {
                }

                PuzzlepieceConnection newConnection = piece.getConnectorForDirection(direction);
                if (newConnection != null) {
                    connections.add(newConnection);
                }
            }
        }

        return connections;
    }

    /**
     * Gets the maximum number of puzzlepieces in x-direction in this group.
     *
     * @return
     */
    public int getMaxPuzzlePiecesInXDirection() {
        int maxNumber = 0;

        for (List<Puzzlepiece> row : puzzlepiecesList) {
            if (row.size() > maxNumber) {
                maxNumber = row.size();
            }
        }
        return maxNumber;
    }

    /**
     * Gets the maximum number of puzzlepieces in y-direction in this group.
     *
     * @return
     */
    public int getMaxPuzzlePiecesInYDirection() {
        return puzzlepiecesList.size();
    }

    /**
     * Gets the x-position of the puzzlepiece inside this group.
     *
     * @param puzzlepiece
     * @return The x-position of the puzzlepiece inside this group or
     * <code>-1</code> if this group does not contain the puzzlepiece.
     */
    public int getXPositionOfPieceInGroup(Puzzlepiece puzzlepiece) {
        for (int rIndex = puzzlepiecesList.size() - 1; rIndex >= 0; rIndex--) {
            ArrayList<Puzzlepiece> row = puzzlepiecesList.get(rIndex);
            for (int cIndex = 0; cIndex < row.size(); cIndex++) {
                Puzzlepiece piece = row.get(cIndex);

                if (piece == puzzlepiece) {
                    return cIndex;
                }
            }
        }

        return -1;
    }

    /**
     * Gets the y-position of the puzzlepiece inside this group.
     *
     * @param puzzlepiece
     * @return The y-position of the puzzlepiece inside this group or
     * <code>-1</code> if this group does not contain the puzzlepiece.
     */
    public int getYPositionOfPieceInGroup(Puzzlepiece puzzlepiece) {
        for (int rIndex = puzzlepiecesList.size() - 1; rIndex >= 0; rIndex--) {
            ArrayList<Puzzlepiece> row = puzzlepiecesList.get(rIndex);
            for (int cIndex = 0; cIndex < row.size(); cIndex++) {
                Puzzlepiece piece = row.get(cIndex);

                if (piece == puzzlepiece) {
                    return rIndex;
                }
            }
        }

        return -1;
    }

    /**
     * Gets the x-coordinates of this group. It indicates where in the
     * puzzlearea the pieces of this group lay.
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinates of this group. It indicates where in the
     * puzzlearea the pieces of this group lay.
     *
     * @param x
     */
    public void setX(int x) {
        setPosition(x, getY());
    }

    /**
     * Sets the x- and y-coordinates of this group. It indicates where in the
     * puzzlearea the pieces of this group lay.
     *
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        setChanged();
        notifyObservers();
    }

    /**
     * Gets the y-coordinates of this group. It indicates where in the
     * puzzlearea the pieces of this group lay.
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinates of this group. It indicates where in the
     * puzzlearea the pieces of this group lay.
     *
     * @param y
     */
    public void setY(int y) {
        setPosition(getX(), y);
    }

    /**
     * Gets the state, whether this group is contained in a puzzle or not.
     *
     * @return
     */
    public boolean isInPuzzle() {
        return !puzzlepiecesList.isEmpty();
    }

    /**
     * Tests, whether this group contains the given puzzlepiece.
     *
     * @param puzzlepiece
     * @return
     */
    public boolean isPuzzlepieceContained(Puzzlepiece puzzlepiece) {
        boolean contained = false;

        for (Puzzlepiece tempPuzzlepiece : getPuzzlepieces()) {
            if (tempPuzzlepiece == puzzlepiece) {
                contained = true;
                break;
            }
        }

        return contained;
    }

    /**
     * Adds the given puzzlepiece to this group at position (x,y).
     *
     * @param puzzlepiece
     * @param x
     * @param y
     * @return The puzzlepiece that was at the given position.
     */
    private Puzzlepiece addPuzzlepieceAtPosition(Puzzlepiece puzzlepiece, int x, int y) {
        // get row index or create it
        ArrayList<Puzzlepiece> row;

        try {
            row = puzzlepiecesList.get(x);
        } catch (IndexOutOfBoundsException ex) {
            for (int i = puzzlepiecesList.size(); i <= x; i++) {
                row = new ArrayList<>();
                puzzlepiecesList.add(row);
            }
            row = puzzlepiecesList.get(x);
        }

        // save the element currently at this position
        try {
            row.get(y);
        } catch (IndexOutOfBoundsException ex) {
            for (int i = row.size(); i <= y; i++) {
                row.add(null);
            }
        }

        // add the new element
        puzzlepiece.setPuzzlepieceGroup(this);
        return row.set(y, puzzlepiece);
    }

}
