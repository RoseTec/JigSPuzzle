package jigspuzzle.controller;

import java.awt.Image;
import java.io.File;
import java.util.List;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
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
    public void testRestartPuzzle() throws Exception {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        PuzzleController instance = PuzzleController.getInstance();
        instance.setPuzzle(puzzle);
        instance.restartPuzzle(); // restart first time to actual mak a puzzle

        List<PuzzlepieceGroup> groups = instance.getPuzzle().getPuzzlepieceGroups();
        Image image = instance.getPuzzle().getImage();
        instance.restartPuzzle();
        List<PuzzlepieceGroup> newGroups = instance.getPuzzle().getPuzzlepieceGroups();
        Image newImage = instance.getPuzzle().getImage();

        assertTrue(image == newImage);
//        assertTrue(ImageUtil.imagesAreEqual(image, newImage));

        assertEquals(groups.size(), newGroups.size());
        for (int i = 0; i < groups.size(); i++) {
            assertFalse(groups.get(i) == newGroups.get(i));
            assertTrue(groups.get(i).getPuzzlepieces().equals(newGroups.get(i).getPuzzlepieces()));
        }
    }

    @Test
    public void testShufflePuzzlepieces() throws Exception {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        PuzzleController instance = PuzzleController.getInstance();
        instance.setPuzzle(puzzle);

        int x = puzzle.getPuzzlepieceGroups().get(3).getX();
        int y = puzzle.getPuzzlepieceGroups().get(3).getY();
        instance.shufflePuzzlepieces();
        int newX = puzzle.getPuzzlepieceGroups().get(3).getX();
        int newY = puzzle.getPuzzlepieceGroups().get(3).getY();

        assertNotEquals(x, newX);
        assertNotEquals(y, newY);
    }

    @Test
    public void testSavePuzzle() throws Exception {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        File testFile = new File(puzzlename);
        PuzzleController instance = PuzzleController.getInstance();

        instance.setPuzzle(puzzle);
        instance.savePuzzle(testFile);
        instance.setPuzzle(null);
        instance.loadPuzzle(testFile);

        assertTrue(puzzle.equals(instance.getPuzzle()));
    }

    @Test
    public void testSetPuzzle1() throws Exception {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        PuzzleController instance = PuzzleController.getInstance();

        instance.setPuzzle(puzzle);

        assertTrue(puzzle == instance.getPuzzle());
    }

    @Test
    public void testSetPuzzle2() throws Exception {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        PuzzleController instance = PuzzleController.getInstance();

        instance.setPuzzle(puzzle);
        instance.setPuzzle(null);

        assertTrue(null == instance.getPuzzle());
    }

}
