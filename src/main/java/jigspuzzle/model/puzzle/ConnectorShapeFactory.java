package jigspuzzle.model.puzzle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import jigspuzzle.model.puzzle.shapes.*;

/**
 * A factory for creating a shape for a puzzlepiece connector.
 *
 * @author RoseTec
 */
class ConnectorShapeFactory {

    private static ConnectorShapeFactory instance;

    public static ConnectorShapeFactory getInstance() {
        if (instance == null) {
            instance = new ConnectorShapeFactory();
        }
        return instance;
    }

    private final Map<Integer, ConnectorShape> connectorShapes;

    private ConnectorShapeFactory() {
        connectorShapes = new HashMap<>();
        //TODO: also put in other connectorShapes for different shapes
        ConnectorShape shape;
        int id = 0;
        Class<ConnectorShape>[] classes = new Class[]{
            NormalSizeShape.class,
            FlatShape.class,
            LongShape.class,
            LongCurvedShape.class,
            NormalCurvedShape.class};

        for (Class<ConnectorShape> shapeClass : classes) {
            try {
                shape = shapeClass.newInstance();
                id++;
                connectorShapes.put(id, shape);
                shape.setId(id); // TODO: not that nice....
            } catch (InstantiationException | IllegalAccessException ex) {
            }
        }
    }

    /**
     * Creates a random shape for a puzzlepiece connection.
     *
     * Shapes have allways the following properties: <br>
     * It is assumed, that a puzzlepiece starts at point (0,0) and ends at point
     * (0,100). This means a puzzlepiece has a height of 100.<br>
     * Now we draw the shape to the right. Means we start somewhere at
     * point(0,y) and draw something to the right with then y>0.<br>
     * After drawing all we end somewhere at point (0,y').
     *
     * @return
     */
    ConnectorShape createShape() {
        Random r = new Random();
        int i = r.nextInt(connectorShapes.size()) + 1;

        return connectorShapes.get(i);
    }

    /**
     * Returns the shap that has the given ID. If the ID does not exist, a
     * random shape will be returned.
     *
     * @param id
     * @return
     */
    ConnectorShape getConnectorShapeWithId(int id) {
        ConnectorShape shape = connectorShapes.get(id);

        if (shape == null) {
            return createShape();
        } else {
            return shape;
        }
    }

}
