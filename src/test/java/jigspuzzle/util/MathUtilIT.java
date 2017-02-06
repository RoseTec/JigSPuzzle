package jigspuzzle.util;

import java.awt.Point;
import java.awt.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MathUtilIT {

    public MathUtilIT() {
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
    public void testGetSmallesDistanceFromPointToRectangle1() {
        Point p = new Point(-50, 0);
        Rectangle rect = new Rectangle(0, 0, 150, 200);
        double expResult = 50.0;

        double result = MathUtil.getSmallesDistanceFromPointToRectangle(p, rect);

        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetSmallesDistanceFromPointToRectangle2() {
        Point p = new Point(-50, 300);
        Rectangle rect = new Rectangle(0, 100, 150, 350);
        double expResult = 50.0;

        double result = MathUtil.getSmallesDistanceFromPointToRectangle(p, rect);

        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetSmallesDistanceFromPointToRectangle3() {
        Point p = new Point(200, 200);
        Rectangle rect = new Rectangle(0, 100, 150, 350);
        double expResult = 50.0;

        double result = MathUtil.getSmallesDistanceFromPointToRectangle(p, rect);

        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetSmallesDistanceFromPointToRectangle4() {
        Point p = new Point(100, 0);
        Rectangle rect = new Rectangle(0, 100, 150, 350);
        double expResult = 100.0;

        double result = MathUtil.getSmallesDistanceFromPointToRectangle(p, rect);

        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetSmallesDistanceFromPointToRectangle5() {
        Point p = new Point(250, 450);
        Rectangle rect = new Rectangle(0, 0, 150, 350);
        double expResult = Math.sqrt(100 * 100 + 100 * 100.0);

        double result = MathUtil.getSmallesDistanceFromPointToRectangle(p, rect);

        assertEquals(expResult, result, 0.0);
    }

}
