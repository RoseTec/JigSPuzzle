package jigspuzzle.controller;

import java.io.File;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.testutils.factories.FactorySlave;
import jigspuzzle.testutils.mockups.DummyPuzzleWindow;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzleControllerIT {

    private static final String puzzlename = "test-puzzle." + PuzzleController.PUZZLE_SAVES_ENDING;

    public PuzzleControllerIT() {
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
        File file = new File(puzzlename);

        try {
            file.delete();
        } catch (Exception ex) {
        }
    }

    @Test
    public void testSavePuzzle() throws Exception {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        File testFile = new File(puzzlename);
        PuzzleController instance = PuzzleController.getInstance();

        instance.setPuzzle(puzzle);
        instance.savePuzzle(testFile);
        instance.loadPuzzle(testFile);

        assertTrue(puzzle.equals(instance.getPuzzle()));
    }

}
