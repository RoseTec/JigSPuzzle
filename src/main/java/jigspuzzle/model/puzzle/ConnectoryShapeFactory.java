package jigspuzzle.model.puzzle;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

/**
 * A factory for creating a shape for a puzzlepiec connector.
 *
 * @author RoseTec
 */
class ConnectoryShapeFactory {

    private static ConnectoryShapeFactory instance;

    public static ConnectoryShapeFactory getInstance() {
        if (instance == null) {
            instance = new ConnectoryShapeFactory();
        }
        return instance;
    }

    private ConnectoryShapeFactory() {
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
    Path2D createShape() {
        // TODO: create more shapes...
        GeneralPath shape = new GeneralPath();

        shape.moveTo(0, 40);
        shape.lineTo(15, 30);
        shape.lineTo(25, 50);
        shape.lineTo(15, 70);
        shape.lineTo(0, 60);

        return shape;
    }

}
