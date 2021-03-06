package jigspuzzle.view.desktop.puzzle;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.Observable;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.controller.PuzzleController;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.model.puzzle.Puzzlepiece;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.util.MathUtil;

/**
 * This class represents a puzzlepiece group that can be drawn on a puzzlearea.
 * There are several improvements to the <code>DrawablePuzzlepieceGroup</code>,
 * e.g. it can be dragged around on a puzzleare.
 *
 * @author RoseTec
 */
public class PuzzlepieceView extends DrawablePuzzlepieceGroup {

    private final Puzzlearea puzzlearea;

    public PuzzlepieceView(Puzzlearea puzzlearea, PuzzlepieceGroup group) {
        super(group);
        this.puzzlearea = puzzlearea;

        // make piece able to be moved by user
        MouseAdapter motionLstener = new PieceMoveListener(this);

        this.addMouseListener(motionLstener);
        this.addMouseMotionListener(motionLstener);

        // adapt the size of puzzlepieces to changing setting
        SettingsController.getInstance().addPuzzleareaSettingsObserver((Observable o, Object arg) -> {
            updateViewSize();
            this.repaint();
        });

        // adapt size of this panel to the puzzlepieces inside it
        this.updateViewLocation();
        this.updateViewSize();
    }

    /**
     * Adjusts the size of this puzzlepiece group to the size of the puzzlearea.
     * This means, it gets smaller or bigger depending on the size of the
     * epuzzlearea.
     */
    void adjustSizeToPuzzlearea() {
        Point p = new Point(getPuzzlepieceGroup().getX(), getPuzzlepieceGroup().getY());

        correctPuzzlepieceGroupToFitInPuzzlearea(p);
        setPuzzlepieceGroupPosition(p);

        // adapt sizes of puzzlepieces when this puzzlearea is resized
        updateViewSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Dimension getPuzzleareaSize() {
        return puzzlearea.getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Point getPuzzleareaStart() {
        if (puzzlearea == null) {
            return new Point(0, 0);
        } else {
            return puzzlearea.getPuzzleareaStart();
        }
    }

    @Override
    protected PuzzlepieceGroup getPuzzlepieceGroup() {
        return super.getPuzzlepieceGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getPuzzlepieceHeight() {
        if (puzzlearea == null) {
            return 0;
        } else {
            return puzzlearea.getPuzzlepieceHeight();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getPuzzlepieceWidth() {
        if (puzzlearea == null) {
            return 0;
        } else {
            return puzzlearea.getPuzzlepieceWidth();
        }
    }

    /**
     * Sets the position of this puzzlepiece group.
     *
     * @param position The position of the group <b>in the puzzlearea</b>.
     */
    private void setPuzzlepieceGroupPosition(Point position) {
        position.x += puzzlearea.getPuzzleareaStart().x;
        position.y += puzzlearea.getPuzzleareaStart().y;
        getPuzzlepieceGroup().setPosition(position.x, position.y);
    }

    /**
     * moves this piece before all other pieces such that it is in the
     * foreground now.
     */
    private void moveToFront() {
        JigSPuzzle.getInstance().getPuzzleWindow().bringToFront(getPuzzlepieceGroup());
    }

    /**
     * Tries to let this puzzlepiece group snap with other puzzlepiece groups.
     */
    private void trySnapWithOtherGroups() {
        PuzzleController.getInstance().trySnapPuzzlepieceGroup(getPuzzlepieceGroup());
    }

    /**
     * Corrects the given point in that way, that this puzzlepiecegroup fits
     * into the puzzlearea. So it is not possible, tht the puzzlepiece is 'out
     * of the puzzlearea' and therefore not visible.
     *
     * @param newLocation
     */
    private void correctPuzzlepieceGroupToFitInPuzzlearea(Point newLocation) {
        // correct the point first to be in this puzzlearea
        newLocation.x += puzzlearea.getPuzzleareaStart().x;
        newLocation.y += puzzlearea.getPuzzleareaStart().y;

        try {
            // don't correct the size, when this puzzlepiece group is bigger than the puzzlearea
            if (puzzlearea.getWidth() < getWidth() - 2 * getConnectionsSizeLeftRight()
                    || puzzlearea.getHeight() < getHeight() - 2 * getConnectionsSizeTopButtom()) {
                return;
            }
            // create a shape for the puzzlepiece group
            Area groupShape = getShapeForPuzzlepeceGroup(newLocation);

            // checks if the point is in the screen
            Area screnArea = new Area();
            Rectangle[] allScreens = JigSPuzzle.getInstance().getPuzzleWindow().getPuzzleareaBounds();

            for (Rectangle rect : allScreens) {
                screnArea.add(new Area(rect));
            }
            if (screnArea.contains(groupShape.getBounds())) {
                return;
            }

            // get the screen that the new point is nearest to
            Rectangle puzzleareaRect;

            puzzleareaRect = MathUtil.getRectangleNearestToPoint(newLocation, allScreens);

            // correct point to be in sceen
            if (newLocation.x < puzzleareaRect.x) {
                newLocation.x = puzzleareaRect.x;
            }
            if (newLocation.x > puzzleareaRect.x + puzzleareaRect.width - getWidthOfThisGroup() + 2 * getConnectionsSizeLeftRight()) {
                newLocation.x = puzzleareaRect.x + puzzleareaRect.width - getWidthOfThisGroup() + 2 * getConnectionsSizeLeftRight();
            }

            if (newLocation.y < puzzleareaRect.y) {
                newLocation.y = puzzleareaRect.y;
            }
            if (newLocation.y > puzzleareaRect.y + puzzleareaRect.height - getHeightOfThisGroup() + 2 * getConnectionsSizeTopButtom()) {
                newLocation.y = puzzleareaRect.y + puzzleareaRect.height - getHeightOfThisGroup() + 2 * getConnectionsSizeTopButtom();
            }
        } finally {
            // undo the correction from the start
            newLocation.x -= puzzlearea.getPuzzleareaStart().x;
            newLocation.y -= puzzlearea.getPuzzleareaStart().y;
        }
    }

    /**
     * Gets a shape that contains all puzzlepieces in this group completely.
     *
     * Delegated to
     * <code>getShapeForPuzzlepeceGroup(getPuzzlepieceGroup().x, getPuzzlepieceGroup().y)</code>.
     *
     * @return
     */
    private Shape getShapeForPuzzlepeceGroup() {
        return getShapeForPuzzlepeceGroup(new Point(getPuzzlepieceGroup().getX(), getPuzzlepieceGroup().getY()));
    }

    /**
     * Gets a shape that contains all puzzlepieces in this group completely.
     *
     * @param startOfGroup The start point of this puzzlepiece group.
     * @return
     */
    private Area getShapeForPuzzlepeceGroup(Point startOfGroup) {
        Area ret = new Area();

        for (Puzzlepiece piece : getPuzzlepieceGroup().getPuzzlepieces()) {
            int x = startOfGroup.x + getPuzzlepieceGroup().getXPositionOfPieceInGroup(piece) * getPuzzlepieceWidth();
            int y = startOfGroup.y + getPuzzlepieceGroup().getYPositionOfPieceInGroup(piece) * getPuzzlepieceHeight();
            int width = getPuzzlepieceWidth();
            int height = getPuzzlepieceHeight();
            Area pieceShape = new Area(new Rectangle(x, y, width, height));

            ret.add(pieceShape);
        }
        return ret;
    }

    /**
     * The MouseListener with that a puzzlepiece can be moved around.
     */
    private class PieceMoveListener extends MouseAdapter {

        private boolean isAbleToMovePiece = false;

        /**
         * The initial x position, when the mousebutton has been clicked
         */
        private int initX;

        /**
         * The initial y position, when the mousebutton has been clicked
         */
        private int initY;

        /**
         * The puzzlepiece view for this listener
         */
        private PuzzlepieceView puzzlepieceView;

        public PieceMoveListener(PuzzlepieceView puzzlepieceView) {
            this.puzzlepieceView = puzzlepieceView;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                puzzlepieceView.moveToFront();
                isAbleToMovePiece = true;
                initX = e.getX();
                initY = e.getY();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            if (isAbleToMovePiece) {
                int newX = puzzlepieceView.getX() + e.getX() - initX + getConnectionsSizeLeftRight();
                int newY = puzzlepieceView.getY() + e.getY() - initY + getConnectionsSizeTopButtom();
                Point p = new Point(newX, newY);

                puzzlepieceView.correctPuzzlepieceGroupToFitInPuzzlearea(p);
                puzzlepieceView.setPuzzlepieceGroupPosition(p);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                trySnapWithOtherGroups();
                isAbleToMovePiece = false;
            }
        }

    }

}
