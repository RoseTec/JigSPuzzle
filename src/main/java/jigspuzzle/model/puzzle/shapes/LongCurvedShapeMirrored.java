package jigspuzzle.model.puzzle.shapes;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * A shape, that is long and asymetrical. It is mirrored from
 * <code>LongCurvedShape</code>.
 *
 * @author RoseTec
 */
public class LongCurvedShapeMirrored extends LongCurvedShape {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Path2D constructShape() {
        Path2D shape = super.constructShape();

        AffineTransform at = new AffineTransform();
        at.translate(0, 50);
        at.scale(1, -1);
        at.translate(0, -50);
        shape.transform(at);

        return shape;
    }

}
