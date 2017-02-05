package jigspuzzle.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.testutils.mockups.DummyMultiMonitorPuzzleWindow;
import jigspuzzle.testutils.mockups.DummyPuzzleWindow;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SettingsControllerIT {

    public SettingsControllerIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        File file = new File(SettingsController.SETTINGS_FILE_NAME);

        file.delete();
    }

    @After
    public void tearDown() {
        File file = new File(SettingsController.SETTINGS_FILE_NAME);

        file.delete();

        // reset all controllers
        JigSPuzzle.getInstance().resetInstances();
    }

    @Test
    public void testGetPuzzlepieceSize() {
        SettingsController instance = SettingsController.getInstance();
        instance.setDecreasePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(1);
        int puzzleareaHeight = 1000;
        int puzzleareaWidth = 1500;
        int puzzleHeight = 2000;
        int puzzleWidth = 2000;
        int puzzleRows = 4;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(250, result.height);
        assertEquals(250, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize2() {
        SettingsController instance = SettingsController.getInstance();
        instance.setDecreasePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(0.5);
        int puzzleareaHeight = 1000;
        int puzzleareaWidth = 1500;
        int puzzleHeight = 2000;
        int puzzleWidth = 2000;
        int puzzleRows = 4;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(125, result.height);
        assertEquals(125, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize3() {
        SettingsController instance = SettingsController.getInstance();
        instance.setDecreasePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(1);
        int puzzleareaHeight = 1500;
        int puzzleareaWidth = 1000;
        int puzzleHeight = 2500;
        int puzzleWidth = 2000;
        int puzzleRows = 5;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(250, result.height);
        assertEquals(250, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize4() {
        SettingsController instance = SettingsController.getInstance();
        instance.setDecreasePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(1);
        int puzzleareaHeight = 1000;
        int puzzleareaWidth = 1000;
        int puzzleHeight = 2500;
        int puzzleWidth = 2000;
        int puzzleRows = 5;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(200, result.height);
        assertEquals(200, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize5() {
        SettingsController instance = SettingsController.getInstance();
        instance.setDecreasePuzzleAutomatically(false);
        instance.setUsedSizeOfPuzzlearea(0.5);
        int puzzleareaHeight = 1000;
        int puzzleareaWidth = 1000;
        int puzzleHeight = 2500;
        int puzzleWidth = 2000;
        int puzzleRows = 5;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(500, result.height);
        assertEquals(500, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize6() {
        SettingsController instance = SettingsController.getInstance();
        instance.setDecreasePuzzleAutomatically(true);
        instance.setEnlargePuzzleAutomatically(false);
        instance.setUsedSizeOfPuzzlearea(1);
        int puzzleareaHeight = 1500;
        int puzzleareaWidth = 2000;
        int puzzleHeight = 1000;
        int puzzleWidth = 1500;
        int puzzleRows = 2;
        int puzzleColumns = 3;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(500, result.height);
        assertEquals(500, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize10() {
        SettingsController instance = SettingsController.getInstance();
        instance.setEnlargePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(1);
        int puzzleareaHeight = 1600;
        int puzzleareaWidth = 2000;
        int puzzleHeight = 1000;
        int puzzleWidth = 1000;
        int puzzleRows = 4;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(400, result.height);
        assertEquals(400, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize11() {
        SettingsController instance = SettingsController.getInstance();
        instance.setEnlargePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(1);
        int puzzleareaHeight = 1600;
        int puzzleareaWidth = 2000;
        int puzzleHeight = 1000;
        int puzzleWidth = 750;
        int puzzleRows = 4;
        int puzzleColumns = 3;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(400, result.height);
        assertEquals(400, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize12() {
        SettingsController instance = SettingsController.getInstance();
        instance.setEnlargePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(1);
        int puzzleareaHeight = 1500;
        int puzzleareaWidth = 2000;
        int puzzleHeight = 500;
        int puzzleWidth = 1000;
        int puzzleRows = 2;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(500, result.height);
        assertEquals(500, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize13() {
        SettingsController instance = SettingsController.getInstance();
        instance.setEnlargePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(0.5);
        int puzzleareaHeight = 1500;
        int puzzleareaWidth = 2000;
        int puzzleHeight = 500;
        int puzzleWidth = 1000;
        int puzzleRows = 2;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(250, result.height);
        assertEquals(250, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize14() {
        SettingsController instance = SettingsController.getInstance();
        instance.setEnlargePuzzleAutomatically(false);
        instance.setUsedSizeOfPuzzlearea(1);
        int puzzleareaHeight = 1500;
        int puzzleareaWidth = 2000;
        int puzzleHeight = 500;
        int puzzleWidth = 1000;
        int puzzleRows = 2;
        int puzzleColumns = 4;

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyPuzzleWindow(puzzleareaWidth, puzzleareaHeight));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(250, result.height);
        assertEquals(250, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize15() {
        Rectangle[] screens = new Rectangle[3];
        SettingsController instance = SettingsController.getInstance();
        instance.setEnlargePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(1);

        int puzzleHeight = 500;
        int puzzleWidth = 1000;
        int puzzleRows = 2;
        int puzzleColumns = 4;
        screens[0] = new Rectangle(-1680, 0, 1680, 1050);
        screens[1] = new Rectangle(0, 0, 2000, 1500);
        screens[2] = new Rectangle(2000, 10, 1680, 1050);

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyMultiMonitorPuzzleWindow(screens));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(500, result.height);
        assertEquals(500, result.width);
    }

    @Test
    public void testGetPuzzlepieceSize16() {
        Rectangle[] screens = new Rectangle[2];
        SettingsController instance = SettingsController.getInstance();
        instance.setEnlargePuzzleAutomatically(true);
        instance.setUsedSizeOfPuzzlearea(1);

        int puzzleHeight = 1500;
        int puzzleWidth = 1000;
        int puzzleRows = 3;
        int puzzleColumns = 2;
        screens[0] = new Rectangle(0, 0, 1300, 1800);
        screens[1] = new Rectangle(2000, 3000, 2000, 1000);

        JigSPuzzle.getInstance().setPuzzleWindow(new DummyMultiMonitorPuzzleWindow(screens));
        Dimension result = instance.getPuzzlepieceSize(puzzleHeight, puzzleWidth, puzzleRows, puzzleColumns);
        assertEquals(600, result.height);
        assertEquals(600, result.width);
    }

    @Test
    public void testSetCurrentLanguage() {
        SettingsController instance = SettingsController.getInstance();
        String newLanguage = "deutsch";

        instance.setCurrentLanguage(newLanguage);

        assertEquals(newLanguage, instance.getCurrentLanguage());
    }

    @Test
    public void testGetLanguageText() {
        SettingsController instance = SettingsController.getInstance();

        String result = instance.getLanguageText(1, 1);

        assertEquals("JigSPuzzle", result);
    }

    @Test
    public void testGetLanguageText1() {
        SettingsController instance = SettingsController.getInstance();

        String result = instance.getLanguageText(3215454, 3215455);

        assertEquals("readText-3215454-3215455", result);
    }

    @Test
    public void testGetAvailableLanguages() {
        SettingsController instance = SettingsController.getInstance();

        String result[] = instance.getAvailableLanguages();

        List<?> list = Arrays.asList(result);
        assertTrue(list.contains("deutsch"));
        assertTrue(list.contains("english"));
    }

    @Test
    public void testSaveLoadSettingsFromFile() throws Exception {
        SettingsController instance = SettingsController.getInstance();
        Color initColor = instance.getPuzzleareaBackgroundColor();
        Color newColor = initColor.getRGB() == 0 ? Color.WHITE : Color.BLACK;

        instance.setPuzzleareaBackgroundColor(newColor);
        instance.saveSettingsToFile();
        assertEquals(newColor, instance.getPuzzleareaBackgroundColor());

        instance.setPuzzleareaBackgroundColor(initColor);
        assertEquals(initColor, instance.getPuzzleareaBackgroundColor());
        instance.loadSettingsFromFile();

        assertEquals(newColor, instance.getPuzzleareaBackgroundColor());
    }

}
