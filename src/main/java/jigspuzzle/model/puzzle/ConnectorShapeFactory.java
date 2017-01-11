package jigspuzzle.model.puzzle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import jigspuzzle.model.puzzle.shapes.FlatShape;
import jigspuzzle.model.puzzle.shapes.NormalSizeShape;

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

        shape = new NormalSizeShape();
        connectorShapes.put(1, shape);
        shape.setId(1); // TODO: not that nice....

        shape = new FlatShape();
        connectorShapes.put(2, shape);
        shape.setId(2);
    }

    /**
     * Creates a random shape for a puzzlepiece.
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
