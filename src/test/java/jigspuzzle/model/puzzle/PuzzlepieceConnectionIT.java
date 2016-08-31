package jigspuzzle.model.puzzle;

import jigspuzzle.testutils.factories.FactorySlave;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzlepieceConnectionIT {

    public PuzzlepieceConnectionIT() {
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
    public void testGetInOutPuzzlepiece() throws ClassNotFoundException {
        Puzzlepiece piece1 = (Puzzlepiece) FactorySlave.build(Puzzlepiece.class).create();
        Puzzlepiece piece2 = (Puzzlepiece) FactorySlave.build(Puzzlepiece.class).create();

        PuzzlepieceConnection instance = new PuzzlepieceConnection(piece1, piece2);

        assertEquals(piece1, instance.getInPuzzlepiece());
        assertEquals(piece2, instance.getOutPuzzlepiece());
    }

}
