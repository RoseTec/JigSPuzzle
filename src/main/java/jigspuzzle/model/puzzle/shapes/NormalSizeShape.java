package jigspuzzle.model.puzzle.shapes;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import jigspuzzle.model.puzzle.ConnectorShape;

/**
 * A normal sized shape, that is not too small and not too big. It is
 * symetrical.
 *
 * @author RoseTec
 */
public class NormalSizeShape extends ConnectorShape {

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    protected Path2D constructShape() {
        GeneralPath shape = new GeneralPath();

        shape.moveTo(0, 37);
        shape.curveTo(2.5, 40, 5.5, 40, 8, 37);
        shape.curveTo(14.9, 24, 30, 31, 30, 48);
        shape.lineTo(30, 52);
        shape.curveTo(30, 69, 14.9, 76, 8, 63);
        shape.curveTo(5.5, 60, 2.5, 60, 0, 63);

        return shape;
    }

}
