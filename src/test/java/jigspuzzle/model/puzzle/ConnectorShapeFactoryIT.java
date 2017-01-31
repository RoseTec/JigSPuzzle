package jigspuzzle.model.puzzle;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConnectorShapeFactoryIT {

    public ConnectorShapeFactoryIT() {
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
    public void testGetAllConnectorShapeIds() {
        ConnectorShapeFactory instance = ConnectorShapeFactory.getInstance();

        int maxShapes = 7;
        List<Integer> result = instance.getAllConnectorShapeIds();

        List<Integer> expResult = new ArrayList<>();
        for (int i = 1; i <= maxShapes; i++) {
            expResult.add(i);
        }
        assertEquals(expResult, result);
    }

}
