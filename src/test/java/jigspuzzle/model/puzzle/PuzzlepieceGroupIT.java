package jigspuzzle.model.puzzle;

import java.util.List;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.testutils.factories.FactorySlave;
import jigspuzzle.testutils.mockups.DummyPuzzleWindow;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzlepieceGroupIT {

    public PuzzlepieceGroupIT() {
    }

    @BeforeClass
    public static void setUpClass() {
        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddFromPuzzlepieceGroup_normal() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis = puzzle.puzzlepieces[1][1];
        Puzzlepiece pieceOther = puzzle.puzzlepieces[1][2];

        PuzzlepieceGroup groupThis = pieceThis.getPuzzlepieceGroup();
        PuzzlepieceGroup groupOther = pieceOther.getPuzzlepieceGroup();

        assertEquals(0, groupThis.getXPositionOfPieceInGroup(pieceThis));
        assertEquals(0, groupThis.getYPositionOfPieceInGroup(pieceThis));
        assertEquals(0, groupOther.getXPositionOfPieceInGroup(pieceOther));
        assertEquals(0, groupOther.getYPositionOfPieceInGroup(pieceOther));

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.RIGHT));

        assertEquals(0, groupThis.getXPositionOfPieceInGroup(pieceThis));
        assertEquals(0, groupThis.getYPositionOfPieceInGroup(pieceThis));
        assertEquals(1, groupThis.getXPositionOfPieceInGroup(pieceOther));
        assertEquals(0, groupThis.getYPositionOfPieceInGroup(pieceOther));
    }

    @Test
    public void testAddFromPuzzlepieceGroup_addLeft() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis = puzzle.puzzlepieces[1][2];
        Puzzlepiece pieceOther = puzzle.puzzlepieces[1][1];

        PuzzlepieceGroup groupThis = pieceThis.getPuzzlepieceGroup();
        PuzzlepieceGroup groupOther = pieceOther.getPuzzlepieceGroup();

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.LEFT));

        assertEquals(1, groupThis.getXPositionOfPieceInGroup(pieceThis));
        assertEquals(0, groupThis.getYPositionOfPieceInGroup(pieceThis));
        assertEquals(0, groupThis.getXPositionOfPieceInGroup(pieceOther));
        assertEquals(0, groupThis.getYPositionOfPieceInGroup(pieceOther));
    }

    @Test
    public void testAddFromPuzzlepieceGroup_addTop() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis = puzzle.puzzlepieces[2][1];
        Puzzlepiece pieceOther = puzzle.puzzlepieces[1][1];

        PuzzlepieceGroup groupThis = pieceThis.getPuzzlepieceGroup();
        PuzzlepieceGroup groupOther = pieceOther.getPuzzlepieceGroup();

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.TOP));

        assertEquals(0, groupThis.getXPositionOfPieceInGroup(pieceThis));
        assertEquals(1, groupThis.getYPositionOfPieceInGroup(pieceThis));
        assertEquals(0, groupThis.getXPositionOfPieceInGroup(pieceOther));
        assertEquals(0, groupThis.getYPositionOfPieceInGroup(pieceOther));
    }

    @Test
    public void testAddFromPuzzlepieceGroup_addBoth() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis, pieceOther;
        PuzzlepieceGroup groupThis, groupOther;

        // first left
        pieceThis = puzzle.puzzlepieces[0][1];
        pieceOther = puzzle.puzzlepieces[0][0];

        groupThis = pieceThis.getPuzzlepieceGroup();
        groupOther = pieceOther.getPuzzlepieceGroup();

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.LEFT));

        // then top
        pieceThis = puzzle.puzzlepieces[1][1];

        groupOther = groupThis;
        groupThis = pieceThis.getPuzzlepieceGroup();

        assertEquals(0, groupThis.getXPositionOfPieceInGroup(pieceThis));
        assertEquals(0, groupThis.getYPositionOfPieceInGroup(pieceThis));

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.TOP));

        assertEquals(1, groupThis.getXPositionOfPieceInGroup(pieceThis));
        assertEquals(1, groupThis.getYPositionOfPieceInGroup(pieceThis));
    }

    @Test
    public void testAddFromPuzzlepieceGroup_addBothMore() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis, pieceOther;
        PuzzlepieceGroup groupThis, groupOther;

        // first left
        pieceThis = puzzle.puzzlepieces[0][1];
        pieceOther = puzzle.puzzlepieces[0][0];

        groupThis = pieceThis.getPuzzlepieceGroup();
        groupOther = pieceOther.getPuzzlepieceGroup();

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.LEFT));

        // 2nd left
        pieceOther = puzzle.puzzlepieces[0][2];
        groupOther = pieceOther.getPuzzlepieceGroup();

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.LEFT));

        // first top
        pieceOther = puzzle.puzzlepieces[1][2];
        groupOther = pieceOther.getPuzzlepieceGroup();

        assertEquals(0, groupOther.getXPositionOfPieceInGroup(pieceOther));
        assertEquals(0, groupOther.getYPositionOfPieceInGroup(pieceOther));

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.TOP));

        // 2nd top
        pieceThis = puzzle.puzzlepieces[2][2];

        groupOther = groupThis;
        groupThis = pieceThis.getPuzzlepieceGroup();

        assertEquals(0, groupThis.getXPositionOfPieceInGroup(pieceThis));
        assertEquals(0, groupThis.getYPositionOfPieceInGroup(pieceThis));

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.TOP));

        assertEquals(2, groupThis.getXPositionOfPieceInGroup(pieceThis));
        assertEquals(2, groupThis.getYPositionOfPieceInGroup(pieceThis));
    }

    @Test
    public void testDestroy() throws ClassNotFoundException, NoSuchFieldException {
        PuzzlepieceGroup instance = (PuzzlepieceGroup) FactorySlave.build(PuzzlepieceGroup.class).create();

        instance.destroy();

        assertEquals(0, instance.getPuzzlepieces().size());
    }

    @Test
    public void testGetPuzzlepieces() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis, pieceOther;
        PuzzlepieceGroup groupThis, groupOther;

        pieceThis = puzzle.puzzlepieces[0][1];
        pieceOther = puzzle.puzzlepieces[0][0];

        groupThis = pieceThis.getPuzzlepieceGroup();
        groupOther = pieceOther.getPuzzlepieceGroup();

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.LEFT));

        List<Puzzlepiece> result = groupThis.getPuzzlepieces();
        assertTrue(result.contains(pieceThis));
        assertTrue(result.contains(pieceOther));
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPuzzlepieceConnectionsInPosition_single() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis;
        PuzzlepieceGroup groupThis;

        pieceThis = puzzle.puzzlepieces[1][1];
        groupThis = pieceThis.getPuzzlepieceGroup();

        for (ConnectorPosition direction : ConnectorPosition.values()) {
            List<PuzzlepieceConnection> result = groupThis.getPuzzlepieceConnectionsInPosition(direction);
            assertTrue(result.contains(pieceThis.getConnectorForDirection(direction)));
            assertEquals(1, result.size());
        }
    }

    @Test
    public void testGetPuzzlepieceConnectionsInPosition_2OnTopButtom() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis, pieceOther;
        PuzzlepieceGroup groupThis, groupOther;

        pieceThis = puzzle.puzzlepieces[1][1];
        pieceOther = puzzle.puzzlepieces[1][0];

        groupThis = pieceThis.getPuzzlepieceGroup();
        groupOther = pieceOther.getPuzzlepieceGroup();

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.LEFT));

        for (ConnectorPosition direction : new ConnectorPosition[]{ConnectorPosition.TOP, ConnectorPosition.BUTTOM}) {
            List<PuzzlepieceConnection> result = groupThis.getPuzzlepieceConnectionsInPosition(direction);
            assertTrue(result.contains(pieceThis.getConnectorForDirection(direction)));
            assertTrue(result.contains(pieceOther.getConnectorForDirection(direction)));
            assertEquals(2, result.size());
        }
    }

    @Test
    public void testGetPuzzlepieceConnectionsInPosition_withAHoleInside() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis, pieceOther;
        PuzzlepieceGroup groupThis, groupOther;

        pieceThis = puzzle.puzzlepieces[0][0];
        pieceOther = puzzle.puzzlepieces[0][1];
        groupThis = pieceThis.getPuzzlepieceGroup();
        groupOther = pieceOther.getPuzzlepieceGroup();
        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.LEFT));

        pieceOther = puzzle.puzzlepieces[0][2];
        groupOther = pieceOther.getPuzzlepieceGroup();
        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.LEFT));

        pieceOther = puzzle.puzzlepieces[1][0];
        groupOther = pieceOther.getPuzzlepieceGroup();
        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.TOP));

        pieceOther = puzzle.puzzlepieces[1][2];
        groupOther = pieceOther.getPuzzlepieceGroup();
        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.TOP));

        pieceOther = puzzle.puzzlepieces[2][0];
        groupOther = pieceOther.getPuzzlepieceGroup();
        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.TOP));

        pieceOther = puzzle.puzzlepieces[2][1];
        groupOther = pieceOther.getPuzzlepieceGroup();
        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.LEFT));

        pieceOther = puzzle.puzzlepieces[2][2];
        groupOther = pieceOther.getPuzzlepieceGroup();
        groupThis.addFromPuzzlepieceGroup(groupOther, pieceOther.getConnectorForDirection(ConnectorPosition.TOP));

        pieceOther = puzzle.puzzlepieces[1][1];
        for (ConnectorPosition direction : ConnectorPosition.values()) {
            List<PuzzlepieceConnection> result = groupThis.getPuzzlepieceConnectionsInPosition(direction);
            assertTrue(result.contains(pieceOther.getConnectorForDirection(direction.getOpposite())));
            assertEquals(1, result.size());
        }
    }

    @Test
    public void testSetPosition() throws ClassNotFoundException {
        PuzzlepieceGroup instance = (PuzzlepieceGroup) FactorySlave.build(PuzzlepieceGroup.class).create();

        for (int x = 0; x < 100; x += 10) {
            for (int y = 0; y < 100; y += 20) {
                instance.setPosition(x, y);

                assertEquals(x, instance.getX());
                assertEquals(y, instance.getY());
            }
        }
    }

    @Test
    public void testIsPuzzlepieceContained() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis;
        PuzzlepieceGroup groupThis;

        pieceThis = puzzle.puzzlepieces[0][1];
        groupThis = pieceThis.getPuzzlepieceGroup();

        assertTrue(groupThis.isPuzzlepieceContained(pieceThis));
    }

    @Test
    public void testIsPuzzlepieceContained_more() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece pieceThis, pieceOther;
        PuzzlepieceGroup groupThis, groupOther;

        pieceThis = puzzle.puzzlepieces[0][1];
        pieceOther = puzzle.puzzlepieces[0][0];

        groupThis = pieceThis.getPuzzlepieceGroup();
        groupOther = pieceOther.getPuzzlepieceGroup();

        groupThis.addFromPuzzlepieceGroup(groupOther, pieceThis.getConnectorForDirection(ConnectorPosition.LEFT));

        assertTrue(groupThis.isPuzzlepieceContained(pieceThis));
        assertTrue(groupThis.isPuzzlepieceContained(pieceOther));
    }

}
