package jigspuzzle.model.puzzle;

import jigspuzzle.testutils.factories.FactorySlave;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzlepieceIT {

    public PuzzlepieceIT() {
    }

    @BeforeClass
    public static void setUpClass() {
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
    public void testCreateConnectorToPiece() throws ClassNotFoundException {
        for (ConnectorPosition position : ConnectorPosition.values()) {
            Puzzlepiece instance = (Puzzlepiece) FactorySlave.build(Puzzlepiece.class).create();
            Puzzlepiece otherPiece = (Puzzlepiece) FactorySlave.build(Puzzlepiece.class).create();

            assertTrue(instance.createConnectorToPiece(otherPiece, position));

            PuzzlepieceConnection connection = instance.getConnectorForDirection(position);
            assertEquals(connection, otherPiece.getConnectorForDirection(position.getOpposite()));

            if (instance == connection.getInPuzzlepiece()) {
                assertTrue(instance.isInPieceInDirection(position));
                assertFalse(instance.isOutPieceInDirection(position));
            } else {
                assertTrue(otherPiece.isInPieceInDirection(position.getOpposite()));
                assertFalse(otherPiece.isOutPieceInDirection(position.getOpposite()));
            }
            if (instance == connection.getOutPuzzlepiece()) {
                assertTrue(instance.isOutPieceInDirection(position));
                assertFalse(instance.isInPieceInDirection(position));
            } else {
                assertTrue(otherPiece.isOutPieceInDirection(position.getOpposite()));
                assertFalse(otherPiece.isInPieceInDirection(position.getOpposite()));
            }
        }
    }

}
