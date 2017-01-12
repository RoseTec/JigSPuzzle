package jigspuzzle.model.puzzle.shapes;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import jigspuzzle.model.puzzle.ConnectorShape;

/**
 * A shape, that is long and asymetrical.
 *
 * @author RoseTec
 */
public class LongCurvedShape extends ConnectorShape {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Path2D constructShape() {
        GeneralPath shape = new GeneralPath();

        shape.moveTo(0, 34);
        shape.curveTo(3, 36, 8, 36, 12, 34);
        shape.curveTo(37, 26, 48, 63, 30, 48);
        shape.curveTo(26, 45, 0, 48, 0, 52);

        return shape;
    }

}
