package jigspuzzle.controller;

import java.util.Observable;
import java.util.Observer;
import jigspuzzle.model.version.Version;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VersionControllerIT {

    public VersionControllerIT() {
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
    public void testCheckForNewVersion() throws Exception {
        VersionController instance = VersionController.getInstance();
        instance.addNewVersionObserver((Observable o, Object arg) -> {
            Version result = (Version) arg;

            assertNotEquals("", result.getVersionString());
            assertTrue(new Version("0.1").isOlderThan(result));
        });
        instance.checkForNewVersion();
    }

    @Test
    public void testGetCurrentVersion() {
        VersionController instance = VersionController.getInstance();
        Version result = instance.getCurrentVersion();
        assertNotEquals("", result.getVersionString());
    }

}
