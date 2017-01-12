package jigspuzzle.model.puzzle.shapes;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import jigspuzzle.model.puzzle.ConnectorShape;

/**
 * A normal sized shape, that is not too small and not too big. It is curved and
 * therefore symetrical.
 *
 * @author RoseTec
 */
public class NormalCurvedShape extends ConnectorShape {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Path2D constructShape() {
        GeneralPath shape = new GeneralPath();

        shape.moveTo(0, 25);
        shape.curveTo(2.5, 28, 5.5, 28, 8, 25);
        shape.curveTo(32, 21, 45, 54, 29, 47);
        shape.curveTo(3, 37, 0, 47, 0, 53);

        return shape;
    }

}
