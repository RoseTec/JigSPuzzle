package jigspuzzle.model.puzzle;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jigspuzzle.controller.SettingsController;

/**
 * A class for a puzzle, that is created for a given picture.
 *
 * @author RoseTec
 */
public class Puzzle {

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
     * used for testings. Not to b used in other places than constructor
     */
    Puzzlepiece[][] puzzlepieces;

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
     * Removes the given puzzlepiece group from the puzzle.
     *
     * @param group
     */
    void removePuzzlepieceGroup(PuzzlepieceGroup group) {
        puzzlepieceseGroups.remove(group);
    }

    /**
     * Shuffles the puzzle on the puzzleare, so that all puzzlepieces get new
     * coordinates.
     *
     * @param maxX The maximum x-coordinate, die <b>nicht mehr</b> verwendet
     * wird.
     * @param maxY The maximum y-coordinate, die <b>nicht mehr</b> verwendet
     * wird.
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
