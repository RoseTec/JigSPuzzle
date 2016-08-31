package jigspuzzle.model.puzzle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RoseTec
 */
public class ConnectorPositionIT {

    public ConnectorPositionIT() {
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
    public void testGetOpposite() {
        assertEquals(ConnectorPosition.RIGHT, ConnectorPosition.LEFT.getOpposite());
        assertEquals(ConnectorPosition.LEFT, ConnectorPosition.RIGHT.getOpposite());
        assertEquals(ConnectorPosition.TOP, ConnectorPosition.BUTTOM.getOpposite());
        assertEquals(ConnectorPosition.BUTTOM, ConnectorPosition.TOP.getOpposite());
    }

}
