package jigspuzzle.model.puzzle;

import java.util.HashMap;
import java.util.Map;

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

    private Map<Integer, ConnectorShape> connectorShapes;

    private ConnectorShapeFactory() {
        connectorShapes = new HashMap<>();
        //TODO: also put in other connectorShapes for different shapes

        connectorShapes.put(1, new ConnectorShape());
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
        // TODO: random of other shapes....
        return connectorShapes.get(1);
    }

    /**
     * Returns the shap that has the given ID. If the ID does not exist, a
     * random shape will be returned.
     *
     * @param id
     * @return
     */
    ConnectorShape getShapeWithId(int id) {
        ConnectorShape shape = connectorShapes.get(id);

        if (shape == null) {
            return createShape();
        } else {
            return shape;
        }
    }

}
