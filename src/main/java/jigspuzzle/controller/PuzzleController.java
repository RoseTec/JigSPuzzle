package jigspuzzle.controller;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.model.puzzle.ConnectorPosition;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.Puzzlepiece;
import jigspuzzle.model.puzzle.PuzzlepieceConnection;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;

/**
 * A controller for all kinds of buissniss with a puzzle. Either the puzzle
 * itself or parts of the puzzle like puzzlepiecs.
 *
 * @author RoseTec
 */
public class PuzzleController extends AbstractController {

    private static PuzzleController instance;

    public static PuzzleController getInstance() {
        if (instance == null) {
            instance = new PuzzleController();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetInstance() {
        instance = null;
    }

    private Puzzle puzzle;

    private PuzzleController() {
    }

    /**
     * Gets the height, that the puzzle has, when it is completed
     *
     * @return
     */
    public int getPuzzleHeight() {
        return puzzle.getImage().getHeight(null);
    }

    /**
     * Gets the width, that the puzzle has, when it is completed
     *
     * @return
     */
    public int getPuzzleWidth() {
        return puzzle.getImage().getWidth(null);
    }

    /**
     * Gets the number of columns that the puzzle has.
     *
     * @return
     */
    public int getPuzzlepieceColumnCount() {
        return puzzle.getColumnCount();
    }

    /**
     * Gets the number of rows that the puzzle has.
     *
     * @return
     */
    public int getPuzzlepieceRowCount() {
        return puzzle.getRowCount();
    }

    /**
     * Creates a new puzzle that can be solved by the user
     *
     * @param imageFile The image for that a puzzle should be created.
     * @param puzzleareaHeight The availible height on the puzzlearea.
     * @param puzzleareaWidth The availible width on the puzzlearea.
     * @throws IOException Will be thrown, when the given images cannot be
     * opened.
     */
    public void newPuzzle(File imageFile, int puzzleareaHeight, int puzzleareaWidth) throws IOException {
        if (puzzle != null) {
            puzzle.destroy();
        }

        // load the image to the puzzle
        BufferedImage image = ImageIO.read(imageFile);

        // calculate number of rows/columns
        int rowCount;
        int columnCount;
        int numberOfPieces = SettingsController.getInstance().getPuzzlepieceNumber();

        int puzzleareaSize = image.getWidth() * image.getHeight();
        int puzzlepieceHeight = (int) (Math.sqrt(puzzleareaSize / (double) numberOfPieces));
        int puzzlepieceWidth = puzzlepieceHeight;

        rowCount = image.getHeight() / puzzlepieceHeight;
        columnCount = image.getWidth() / puzzlepieceWidth;

        // create puzzle
        Dimension pieceSize = SettingsController.getInstance().getPuzzlepieceSize(puzzleareaHeight,
                puzzleareaWidth,
                image.getHeight(),
                image.getWidth(),
                rowCount,
                columnCount);

        puzzle = new Puzzle(image, rowCount, columnCount, pieceSize.width, pieceSize.height);

        // show puzzle on view
        JigSPuzzle.getInstance().getPuzzleWindow().setNewPuzzle(puzzle);

        // shuffle puzzle over the puzzlewindow
        shufflePuzzlepieces(puzzleareaWidth - pieceSize.width, puzzleareaHeight - pieceSize.height);
    }

    /**
     * Shuffles the puzzle on the puzzleare, so that all puzzlepieces get new
     * coordinates.
     *
     * @param maxX The maximum x-coordinate, die <b>nicht mehr</b> verwendet
     * wird.
     * @param maxY The maximum y-coordinate, die <b>nicht mehr</b> verwendet
     * wird.
     */
    public void shufflePuzzlepieces(int maxX, int maxY) {
        if (puzzle != null) {
            puzzle.shufflePuzzlepieces(maxX, maxY, 10);
        }
    }

    /**
     * Tries to let the given puzzlepiece group snap with other puzzlepiece
     * groups. In this case one group is deleten and the puzzlepieces inside the
     * deleted group are added to are other group.
     *
     * @param puzzlepieceGroup The group to test, whether it can snap with other
     * groups.
     */
    public void trySnapPuzzlepieceGroup(PuzzlepieceGroup puzzlepieceGroup) {
        for (PuzzlepieceGroup otherGroup : puzzle.getPuzzlepieceGroups()) {
            if (!otherGroup.equals(puzzlepieceGroup)) {
                // test if the groups can snap and therefore have a puzzlepiece-
                // connection of a piece in each group that can connect the two
                // pieces and therefore the two groups
                PuzzlepieceConnection connection = null;

                for (ConnectorPosition direction : ConnectorPosition.values()) {
                    List<PuzzlepieceConnection> connectionsThisGroup = puzzlepieceGroup.getPuzzlepieceConnectionsInPosition(direction);
                    List<PuzzlepieceConnection> connectionsOtherGroup = otherGroup.getPuzzlepieceConnectionsInPosition(direction.getOpposite());

                    for (PuzzlepieceConnection thisConnection : connectionsThisGroup) {
                        for (PuzzlepieceConnection otherConnection : connectionsOtherGroup) {
                            if (thisConnection != null && thisConnection == otherConnection) {
                                connection = thisConnection;
                                break;
                            }
                        }
                        if (connection != null) {
                            break;
                        }
                    }
                    if (connection != null) {
                        break;
                    }
                }

                if (connection == null) {
                    continue;
                }

                // get the pieces of the connection
                Puzzlepiece piece1;
                Puzzlepiece otherPuzzlepiece;

                if (puzzlepieceGroup.isPuzzlepieceContained(connection.getInPuzzlepiece())) {
                    piece1 = connection.getInPuzzlepiece();
                } else if (puzzlepieceGroup.isPuzzlepieceContained(connection.getOutPuzzlepiece())) {
                    piece1 = connection.getOutPuzzlepiece();
                } else {
                    // this case shuld not happen
                    System.out.println("Exectued a line that should not be exectuted in:\n"
                            + this.getClass().toString() + "::trySnapPuzzlepieceGroup() - create 'piece1'");
                    continue;
                }
                if (otherGroup.isPuzzlepieceContained(connection.getInPuzzlepiece())) {
                    otherPuzzlepiece = connection.getInPuzzlepiece();
                } else if (otherGroup.isPuzzlepieceContained(connection.getOutPuzzlepiece())) {
                    otherPuzzlepiece = connection.getOutPuzzlepiece();
                } else {
                    // this case shuld not happen
                    System.out.println("Exectued a line that should not be exectuted in:\n"
                            + this.getClass().toString() + "::trySnapPuzzlepieceGroup() - create 'otherPuzzlepiece'");
                    continue;
                }

                // get the direction in that piece1 'should see' otherPuzzlepiece
                ConnectorPosition direction = null;

                for (ConnectorPosition positionToTest : ConnectorPosition.values()) {
                    if (connection == piece1.getConnectorForDirection(positionToTest)) {
                        direction = positionToTest;
                        break;
                    }
                }
                if (direction == null) {
                    System.out.println("Exectued a line that should not be exectuted in:\n"
                            + this.getClass().toString() + "::trySnapPuzzlepieceGroup() - create 'direction'");
                    continue;
                }

                // test if the groups are 'near enough' to each other
                if (!isPuzzlepieceNearOtherPieceInDirection(piece1, otherPuzzlepiece, direction)) {
                    continue;
                }

                // if all is ok, delete one and add the pieces to the other
                otherGroup.addFromPuzzlepieceGroup(puzzlepieceGroup, connection);
                puzzlepieceGroup.destroy();
                puzzlepieceGroup = otherGroup;

                // bring the other group to the front
                JigSPuzzle.getInstance().getPuzzleWindow().bringToFront(otherGroup);
            }
        }
    }

    /**
     * Tests if piece 1 has in direction direction the given other piece near
     * by.
     *
     * @param piece1
     * @param otherPiece
     * @param direction
     * @return
     */
    private boolean isPuzzlepieceNearOtherPieceInDirection(Puzzlepiece piece1, Puzzlepiece otherPiece, ConnectorPosition direction) {
        int pieceWidth = JigSPuzzle.getInstance().getPuzzleWindow().getPuzzlepieceWidth();
        int pieceHeight = JigSPuzzle.getInstance().getPuzzleWindow().getPuzzlepieceHeight();

        // calculate the tolerance offset
        int possibleGroupOffset = 50;

        // get the groups of the puzzlepieces
        PuzzlepieceGroup puzzlepieceGroup = piece1.getPuzzlepieceGroup();
        PuzzlepieceGroup otherGroup = otherPiece.getPuzzlepieceGroup();

        // get the positions of the puzzlepieces in the window
        int xPiece1, yPiece1, xOtherPiece, yOtherPiece;

        xPiece1 = puzzlepieceGroup.getX() + puzzlepieceGroup.getXPositionOfPieceInGroup(piece1) * pieceWidth;
        yPiece1 = puzzlepieceGroup.getY() + puzzlepieceGroup.getYPositionOfPieceInGroup(piece1) * pieceHeight;
        xOtherPiece = otherGroup.getX() + otherGroup.getXPositionOfPieceInGroup(otherPiece) * pieceWidth;
        yOtherPiece = otherGroup.getY() + otherGroup.getYPositionOfPieceInGroup(otherPiece) * pieceHeight;

        // calculate the position, where the othr piece is expected
        int xOtherPieceExpected = xPiece1;
        int yOtherPieceExpected = yPiece1;

        switch (direction) {
            case LEFT:
                xOtherPieceExpected -= pieceWidth;
                break;
            case RIGHT:
                xOtherPieceExpected += pieceWidth;
                break;
            case TOP:
                yOtherPieceExpected -= pieceHeight;
                break;
            case BUTTOM:
                yOtherPieceExpected += pieceHeight;
                break;
        }

        // determine te result
        boolean isNear = false;

        if (xOtherPiece - possibleGroupOffset < xOtherPieceExpected
                && xOtherPieceExpected < xOtherPiece + possibleGroupOffset
                && yOtherPiece - possibleGroupOffset < yOtherPieceExpected
                && yOtherPieceExpected < yOtherPiece + possibleGroupOffset) {
            isNear = true;
        }

        // return
        return isNear;
    }

}
