package jigspuzzle.model.puzzle;

import jigspuzzle.testutils.factories.FactorySlave;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzleIT {

    public PuzzleIT() {
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

    /**
     * Test of removePuzzlepieceGroup method, of class Puzzle.
     */
    @Test
    public void testRemovePuzzlepieceGroup() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        PuzzlepieceGroup group = puzzle.getPuzzlepieceGroups().get(3);

        puzzle.removePuzzlepieceGroup(group);

        assertFalse(puzzle.getPuzzlepieceGroups().contains(group));
    }

}
