package jigspuzzle.model.puzzle.shapes;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import jigspuzzle.model.puzzle.ConnectorShape;

/**
 * A shape, that is long and symetrical.
 *
 * @author RoseTec
 */
public class LongShape extends ConnectorShape {

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    protected Path2D constructShape() {
        GeneralPath shape = new GeneralPath();

        shape.moveTo(0, 42);
        shape.curveTo(3, 44, 8, 44, 12, 42);
        shape.curveTo(27, 40, 33, 46, 33, 49);
        shape.lineTo(33, 51);
        shape.curveTo(33, 54, 27, 60, 12, 58);
        shape.curveTo(8, 56, 3, 56, 0, 58);

        return shape;
    }

}
