package jigspuzzle.model.puzzle.shapes;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import jigspuzzle.model.puzzle.ConnectorShape;

/**
 * A shape, that is flat and symetrical.
 *
 * @author RoseTec
 */
public class FlatShape extends ConnectorShape {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Path2D constructShape() {
        GeneralPath shape = new GeneralPath();

        shape.moveTo(0, 40);
        shape.curveTo(3, 39, 3, 39, 6, 40);
        shape.curveTo(8, 29, 20, 15, 20, 45);
        shape.lineTo(20, 55);
        shape.curveTo(20, 85, 8, 71, 6, 60);
        shape.curveTo(3, 61, 3, 61, 0, 60);

        return shape;
    }

}
