package jigspuzzle.model.version;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VersionIT {

    //TODO: This is a unit test-case. Problem: execute this in netbeans...
    public VersionIT() {
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
    public void testGetVersionString1() {
        Version instance = new Version("1.0");
        String expResult = "1.0";
        String result = instance.getVersionString();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetVersionString2() {
        Version instance = new Version("v1.0");
        String expResult = "1.0";
        String result = instance.getVersionString();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetVersionString3() {
        Version instance = new Version("v1.0-SNAPSHOT");
        String expResult = "1.0-SNAPSHOT";
        String result = instance.getVersionString();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan1() {
        Version otherVersion = new Version("v1.0");
        Version instance = new Version("1.0");
        boolean expResult = false;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan2() {
        Version otherVersion = new Version("0.2");
        Version instance = new Version("1.0");
        boolean expResult = false;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan3() {
        Version otherVersion = new Version("0.2");
        Version instance = new Version("1.3");
        boolean expResult = false;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan4() {
        Version otherVersion = new Version("1.2");
        Version instance = new Version("1.0");
        boolean expResult = true;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan5() {
        Version otherVersion = new Version("1.0.1");
        Version instance = new Version("1.0");
        boolean expResult = true;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan6() {
        Version otherVersion = new Version("1.0");
        Version instance = new Version("1.0.1");
        boolean expResult = false;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan7() {
        Version otherVersion = new Version("1.0.0");
        Version instance = new Version("1.0");
        boolean expResult = false;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan8() {
        Version otherVersion = new Version("1.0.0.1");
        Version instance = new Version("1.0");
        boolean expResult = true;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan9() {
        Version otherVersion = new Version("1.0");
        Version instance = new Version("1.0-SNAPSHOT");
        boolean expResult = true;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan10() {
        Version otherVersion = new Version("1.0-SNAPSHOT");
        Version instance = new Version("1.0");
        boolean expResult = false;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan11() {
        Version otherVersion = new Version("1.0-SNAPSHOT");
        Version instance = new Version("1.0-SNAPSHOT");
        boolean expResult = false;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan12() {
        Version otherVersion = new Version("1.0-SNAPSHOT");
        Version instance = new Version("2");
        boolean expResult = false;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsOlderThan13() {
        Version otherVersion = new Version("1.5-SNAPSHOT");
        Version instance = new Version("1.2.3");
        boolean expResult = true;
        boolean result = instance.isOlderThan(otherVersion);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetVersion() {
        String version = "1.1";
        Version instance = new Version("v1.0");
        String expResult = "1.1";
        boolean result = instance.setVersion(version);
        assertTrue(result);
        assertEquals(expResult, instance.getVersionString());
    }

}
