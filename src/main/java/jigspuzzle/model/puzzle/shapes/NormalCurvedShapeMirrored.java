package jigspuzzle.model.puzzle.shapes;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * A normal sized shape, that is not too small and not too big. It is curved and
 * therefore symetrical. It is the mirrored variant of
 * <code>NormalCurvedShape</code>.
 *
 * @author RoseTec
 */
public class NormalCurvedShapeMirrored extends NormalCurvedShape {

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
