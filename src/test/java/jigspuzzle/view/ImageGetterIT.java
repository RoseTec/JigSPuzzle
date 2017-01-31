package jigspuzzle.view;

import java.awt.Image;
import jigspuzzle.controller.SettingsController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ImageGetterIT {

    public ImageGetterIT() {
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
    public void testGetJigSPuzzleImage() {
        ImageGetter instance = ImageGetter.getInstance();
        Image result = instance.getJigSPuzzleImage();

        assertImageExists(result);
    }

    @Test
    public void testGetImageForLanguage_String() {
        String language = SettingsController.getInstance().getCurrentLanguage();
        ImageGetter instance = ImageGetter.getInstance();
        Image result = instance.getImageForLanguage(language);

        assertImageExists(result);
    }

    @Test
    public void testGetImageForLanguage_String_int() {
        String language = SettingsController.getInstance().getCurrentLanguage();
        int height = 20;
        ImageGetter instance = ImageGetter.getInstance();
        Image result = instance.getImageForLanguage(language, height);

        assertImageExists(result);
        assertEquals(height, result.getHeight(null));
    }

    /**
     * Checks if the given image exists
     *
     * @param image
     */
    private void assertImageExists(Image image) {
        assertNotNull(image);

        // check sizes
        assertTrue(image.getHeight(null) > 0);
        assertTrue(image.getWidth(null) > 0);
    }

}
