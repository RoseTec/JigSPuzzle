package jigspuzzle.util;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * A class that provides useful methods for mathamatical problems.
 *
 * @author RoseTec
 */
public class MathUtil {

    /**
     * Gets the rectange, that is nearest to the given point.
     *
     * @param p
     * @param rects
     * @return
     */
    public static Rectangle getRectangleNearestToPoint(Point p, Rectangle[] rects) {
        // calc all distances
        int[] distances = new int[rects.length];

        for (int i = 0; i < rects.length; i++) {
            distances[i] = (int) MathUtil.getSmallesDistanceFromPointToRectangle(p, rects[i]);
        }

        // find smalles distance
        int smallesRectIndex = 0;

        for (int i = 1; i < distances.length; i++) {
            if (distances[i] < distances[smallesRectIndex]) {
                smallesRectIndex = i;
            }
        }

        // return rect with smalles distance
        return rects[smallesRectIndex];
    }

    /**
     * Gets the distance from the given point to the point of the given
     * rectangle, that is nearest to the given point.
     *
     * @param p
     * @param rect
     * @return
     */
    public static double getSmallesDistanceFromPointToRectangle(Point p, Rectangle rect) {
        int dx, dy;

        if (rect.x <= p.x && p.x <= rect.x + rect.width) {
            dx = 0;
        } else {
            dx = Math.min(Math.abs(rect.x - p.x), Math.abs(p.x - (rect.x + rect.width)));
            if (dx < 0) {
                dx = -dx;
            }
        }
        if (rect.y <= p.y && p.y <= rect.y + rect.height) {
            dy = 0;
        } else {
            dy = Math.min(Math.abs(rect.y - p.y), Math.abs(p.y - (rect.y + rect.height)));
            if (dy < 0) {
                dy = -dy;
            }
        }
        return Math.sqrt(dx * dx + dy * dy);
    }

}
