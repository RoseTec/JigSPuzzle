package jigspuzzle.view.desktop.puzzle;

import java.awt.Shape;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.controller.PuzzleController;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.model.puzzle.ConnectorPosition;
import jigspuzzle.model.puzzle.ConnectorShapeFactory;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.Puzzlepiece;
import jigspuzzle.testutils.factories.FactorySlave;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzlepieceViewIT {

    public PuzzlepieceViewIT() {
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
        // reset all controllers
        JigSPuzzle.getInstance().resetInstances();
    }

    @Test
    public void testGetShapeOnConnectorPosition1() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece piece = puzzle.getPuzzlepieceGroups().get(puzzle.getPuzzlepieceGroups().size() / 2).getPuzzlepieces().get(0);
        PuzzleController.getInstance().setPuzzle(puzzle);
        PuzzlepieceView instance = new PuzzlepieceView(new Puzzlearea(), piece.getPuzzlepieceGroup());

        SettingsController.getInstance().setUseRandomConnectorShape(true);
        for (ConnectorPosition position : ConnectorPosition.values()) {
            Shape result = instance.getShapeOnConnectorPosition(position, piece);
            Shape expResult = piece.getConnectorForDirection(position).getShape();

            assertTrue(result.equals(expResult));
        }
    }

    @Test
    public void testGetShapeOnConnectorPosition2() throws ClassNotFoundException {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        Puzzlepiece piece = puzzle.getPuzzlepieceGroups().get(puzzle.getPuzzlepieceGroups().size() / 2).getPuzzlepieces().get(0);
        PuzzleController.getInstance().setPuzzle(puzzle);
        PuzzlepieceView instance = new PuzzlepieceView(new Puzzlearea(), piece.getPuzzlepieceGroup());

        SettingsController.getInstance().setUseRandomConnectorShape(false);
        for (int shapeId : ConnectorShapeFactory.getInstance().getAllConnectorShapeIds()) {
            SettingsController.getInstance().setPuzzlepieceConnectorShapeId(shapeId);

            for (ConnectorPosition position : ConnectorPosition.values()) {
                Shape result = instance.getShapeOnConnectorPosition(position, piece);
                Shape expResult = ConnectorShapeFactory.getInstance().getConnectorShapeWithId(shapeId).getShape();

                assertTrue(result.equals(expResult));
            }
        }
    }

}
