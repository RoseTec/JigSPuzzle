package jigspuzzle.model.puzzle;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import jigspuzzle.model.Savable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A class for a puzzle, that is created for a given picture.
 *
 * @author RoseTec
 */
public class Puzzle implements Savable {

    /**
     * Creates a puzzle from the given file
     *
     * @param settingsNode
     * @return
     * @throws IOException
     * @see #loadFromFile(org.w3c.dom.Element)
     */
    public static Puzzle createFromFile(Element settingsNode) throws IOException {
        Puzzle p = new Puzzle();
        p.loadFromFile(settingsNode);
        return p;
    }

    /**
     * The pieces of the puzzle. Represented as groups of puzzlepieces.
     */
    private final ArrayList<PuzzlepieceGroup> puzzlepieceseGroups;

    /**
     * The image of this puzzle.
     */
    private Image image;

    /**
     * The number of rows in this puzzle;
     */
    private int rowCount;

    /**
     * The number of columns in this puzzle;
     */
    private int columnCount;

    /**
     * used for testings. Not to be used in other places than constructor
     */
    Puzzlepiece[][] puzzlepieces;

    private Puzzle() {
        puzzlepieceseGroups = null;
    }

    public Puzzle(BufferedImage image, int rowCount, int columnCount, int pieceWidth, int pieceHeight) {
        this.image = image;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        puzzlepieces = new Puzzlepiece[rowCount][columnCount];

        puzzlepieceseGroups = new ArrayList<>(rowCount * columnCount);
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                BufferedImage img = new BufferedImage(image.getWidth() / columnCount, image.getHeight() / rowCount, image.getType());

                // split the image in pieces
                Puzzlepiece newPiece;
                Graphics2D gr = img.createGraphics();
                gr.drawImage(image,
                        0, 0,
                        img.getWidth(), img.getHeight(),
                        img.getWidth() * y, img.getHeight() * x,
                        img.getWidth() * y + img.getWidth(), img.getHeight() * x + img.getHeight(),
                        null);
                gr.dispose();

                newPiece = new Puzzlepiece(img);
                puzzlepieces[x][y] = newPiece;
                puzzlepieceseGroups.add(x * columnCount + y, new PuzzlepieceGroup(this, newPiece, pieceWidth * y, pieceHeight * x));

                // connect the puzzlepieces
                if (x > 0) {
                    puzzlepieces[x][y].createConnectorToPiece(puzzlepieces[x - 1][y], ConnectorPosition.TOP);
                }
                if (y > 0) {
                    puzzlepieces[x][y].createConnectorToPiece(puzzlepieces[x][y - 1], ConnectorPosition.LEFT);
                }
            }
        }
    }

    /**
     * An method that should be called, when the puzzle is no longer needed is
     * about to be destroyed.
     */
    public void destroy() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.puzzlepieceseGroups);
        hash = 89 * hash + Objects.hashCode(this.image);
        hash = 89 * hash + this.rowCount;
        hash = 89 * hash + this.columnCount;
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
        final Puzzle other = (Puzzle) obj;
        if (this.rowCount != other.rowCount) {
            return false;
        }
        if (this.columnCount != other.columnCount) {
            return false;
        }
        if (!Objects.equals(this.puzzlepieceseGroups, other.puzzlepieceseGroups)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromFile(Element settingsNode) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Removes the given puzzlepiece group from the puzzle.
     *
     * @param group
     */
    void removePuzzlepieceGroup(PuzzlepieceGroup group) {
        puzzlepieceseGroups.remove(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveToFile(Document doc, Element rootElement) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Shuffles the puzzle on the puzzleare, so that all puzzlepieces get new
     * coordinates.
     *
     * @param maxX The maximum x-coordinate, that is <b>not</b> be used. Means,
     * the given number is excludes.
     * @param maxY The maximum y-coordinate, that is <b>not</b> be used. Means,
     * the given number is excludes.
     * @param waitBetweenShuffle The Time in miliseconds that should be waited
     * before the next puzzlepiece gets a new coordinate.
     */
    public void shufflePuzzlepieces(int maxX, int maxY, int waitBetweenShuffle) {
        Random r = new Random();

        for (int i = 0; i < puzzlepieceseGroups.size(); i++) {
            PuzzlepieceGroup group = puzzlepieceseGroups.get(i);
            try {
                Thread.sleep(waitBetweenShuffle);
            } catch (InterruptedException ex) {
            }
            try {
                group.setX(r.nextInt(maxX));
                group.setY(r.nextInt(maxY));
            } catch (NullPointerException ex) {
                // puzzlepiece group was already cnnected to another group and does not exist anymore
                continue;
            }
        }
    }

    /**
     * Gets the number of columns in this puzzle.
     *
     * @return
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Sets the number of columns in this puzzle.
     *
     * @param columnCount
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    /**
     * Returns the image that this puzzle is made of.
     *
     * @return
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the number of rows in this puzzle.
     *
     * @return
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Sets the number of rows in this puzzle.
     *
     * @param rowCount
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * Returns a list that contains all puzzlepieces. They are represented by
     * grouping them into PuzzlepieceGroups, that contains several puzzlepieces.
     *
     * @return
     */
    public List<PuzzlepieceGroup> getPuzzlepieceGroups() {
        return new ArrayList<>(puzzlepieceseGroups);
    }

}
