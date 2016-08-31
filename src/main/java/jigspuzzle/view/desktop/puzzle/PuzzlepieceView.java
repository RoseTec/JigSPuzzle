package jigspuzzle.view.desktop.puzzle;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.Observable;
import javax.swing.JPanel;
import jigspuzzle.controller.PuzzleController;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.model.puzzle.ConnectorPosition;
import jigspuzzle.model.puzzle.Puzzlepiece;
import jigspuzzle.model.puzzle.PuzzlepieceConnection;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;

/**
 * This is a class for displaying a puzzlepiece group, that contains out of
 * several puzzlepieces.
 *
 * The idea behind displaying is the following:<br>
 * Each puzzlepiece has a special area for the connector. Out-connectors stand
 * out of the puzzle and need an image from the corresponding puzzlepiecepiece,
 * to that the out-connector is connected.<br>
 * The start-position of a puzzle piece is given through the methods
 * <code>getXStartPositionOfPuzzlepiece(Puzzlepiece piece)</code> and
 * <code>getYStartPositionOfPuzzlepiece(Puzzlepiece piece)</code>. This is the
 * point, where the 'main'-puzzlepiece starts. There is <b>no</b> space for a
 * connector calculated in there. Means if a puzzlepiece has a x-start-point of
 * 100, a connector can have the start-point at 70.
 *
 * @author RoseTec
 * @see #getXStartPositionOfPuzzlepiece(jigspuzzle.model.puzzle.Puzzlepiece)
 * @see #getYStartPositionOfPuzzlepiece(jigspuzzle.model.puzzle.Puzzlepiece)
 */
public class PuzzlepieceView extends JPanel {

    private final PuzzlepieceGroup piecegroup;

    private final Puzzlearea puzzlearea;

    public PuzzlepieceView(Puzzlearea puzzlearea, PuzzlepieceGroup group) {
        this.puzzlearea = puzzlearea;
        this.piecegroup = group;

        this.setOpaque(false);

        // move this piece when the model changed
        piecegroup.addObserver((Observable o, Object arg) -> {
            if (piecegroup.isInPuzzle()) {
                updateViewLocation();
                this.updateViewSize();
            }
        });
        updateViewLocation();

        // make piece able to be moved by user
        MouseAdapter motionLstener = new PieceMoveListener(this);

        this.addMouseListener(motionLstener);
        this.addMouseMotionListener(motionLstener);

        // adapt the size of puzzlepieces to changing setting
        SettingsController.getInstance().addPuzzleareaSettingsObserver((Observable o, Object arg) -> {
            updateViewSize();
            this.repaint();
        });

        // adapt sizes of puzzlepieces when this puzzlearea is resized
        puzzlearea.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateViewSize();
            }
        });

        // when the puzzlearea gets smaller, the puzzlepieces should still be
        // visible. -> Moven them to stay in the puzzleare.
        puzzlearea.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Point p = new Point(piecegroup.getX(), piecegroup.getY());

                correctPointToFitInPuzzleare(p);
                piecegroup.setPosition(p.x, p.y);
            }
        });

        // adapt size of this panel to the puzzlepieces inside it
        this.updateViewSize();
    }

    /**
     * Returns whether this view belongs to the given puzzlepiece group. If so,
     * this view is able to display all puzzlepieces in the group.
     *
     * @param group
     * @return
     */
    public boolean belongsToPuzzlepieceGroup(PuzzlepieceGroup group) {
        return this.piecegroup == group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(int x, int y) {
        /**
         * Defines, if this puzzlepiece group is hit, when clicking at point
         * (x,y) in this panel
         */

        // retrun false, if point is outide of the panel
        if (x < 0 || this.getWidth() < x
                || y < 0 || this.getHeight() < y) {
            return false;
        }

        // 1. simple test: test, if point is in no puzzlepiece (puzzlepiece-size = puzzlepiece + connector_size)
        boolean puzzlepieceHit = false;

        for (Puzzlepiece puzzlepiece : piecegroup.getPuzzlepieces()) {
            int xStart = getXStartPositionOfPuzzlepiece(puzzlepiece);
            int yStart = getYStartPositionOfPuzzlepiece(puzzlepiece);
            int pieceX = xStart - getConnectionsSizeLeftRight();
            int pieceY = yStart - getConnectionsSizeTopButtom();

            if (0 < pieceX && pieceX < puzzlearea.getPuzzlepieceWidth() + 2 * getConnectionsSizeLeftRight()
                    || 0 < pieceY || pieceY < puzzlearea.getPuzzlepieceHeight() + 2 * getConnectionsSizeTopButtom()) {
                puzzlepieceHit = true;
                break;
            }
        }
        if (!puzzlepieceHit) {
            return false;
        }

        // the main test: test on puzzlepieces, if the points hits the puzzlepiece
        puzzlepieceHit = false;

        for (Puzzlepiece puzzlepiece : piecegroup.getPuzzlepieces()) {
            Rectangle imgRect = new Rectangle(getXStartPositionOfPuzzlepiece(puzzlepiece),
                    getYStartPositionOfPuzzlepiece(puzzlepiece),
                    puzzlearea.getPuzzlepieceWidth(),
                    puzzlearea.getPuzzlepieceHeight());
            Area area = new Area(imgRect);

            for (ConnectorPosition direction : ConnectorPosition.values()) {
                PuzzlepieceConnection connection = puzzlepiece.getConnectorForDirection(direction);

                if (connection != null) {
                    GeneralPath connectorPath = getShapeOnConnectorPosition(direction, puzzlepiece);

                    if (puzzlepiece.isOutPieceInDirection(direction)) {
                        area.add(new Area(connectorPath));
                    } else {
                        area.subtract(new Area(connectorPath));
                    }
                }
            }

            if (area.contains(x, y)) {
                puzzlepieceHit = true;
                break;
            }
        }

        return puzzlepieceHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Puzzlepiece puzzlepiece : piecegroup.getPuzzlepieces()) {
            // paint puzzlepiece
            paintPiece(puzzlepiece, g2);
        }
    }

    /**
     * Gets the height of this puzzlepiecegroup view
     *
     * @return
     */
    public int getHeightOfThisGroup() {
        return puzzlearea.getPuzzlepieceHeight() * piecegroup.getMaxPuzzlePiecesInYDirection() + 2 * getConnectionsSizeTopButtom();
    }

    /**
     * Gets the number of the puzzlepiece groups inside this view.
     *
     * @return
     */
    public int getNumberOfContainedPuzzlepieceGroups() {
        return piecegroup.getPuzzlepieces().size();
    }

    /**
     * Gets the width of this puzzlepiecegroup view
     *
     * @return
     */
    public int getWidthOfThisGroup() {
        return puzzlearea.getPuzzlepieceWidth() * piecegroup.getMaxPuzzlePiecesInXDirection() + 2 * getConnectionsSizeLeftRight();
    }

    /**
     * Gets the x-coordinate of the start position of the puzzlepiece.
     * Connectors are <b>not</b> calculated in here. Means, that if a
     * puzzlepiece starts at position 100, a correcponding connector can start
     * at position 30.
     *
     * @param piece
     * @return
     */
    protected int getXStartPositionOfPuzzlepiece(Puzzlepiece piece) {
        return getConnectionsSizeLeftRight() + piecegroup.getXPositionOfPieceInGroup(piece) * puzzlearea.getPuzzlepieceWidth();
    }

    /**
     * Gets the y-coordinate of the start position of the puzzlepiece.
     * Connectors are <b>not</b> calculated in here. Means, that if a
     * puzzlepiece starts at position 100, a correcponding connector can start
     * at position 30.
     *
     * @param piece
     * @return
     */
    protected int getYStartPositionOfPuzzlepiece(Puzzlepiece piece) {
        return getConnectionsSizeTopButtom() + piecegroup.getYPositionOfPieceInGroup(piece) * puzzlearea.getPuzzlepieceHeight();
    }

    /**
     * Updates the view of this puzzlepiece to the given size of the
     * puzzlepiece-model.
     */
    void updateViewSize() {
        setSize(getWidthOfThisGroup(), getHeightOfThisGroup());
    }

    /**
     * Gets a new shap out of the shape from the given puzzlepiece, that
     * transformed in the given position of the puzzlepiece.
     *
     * @param position
     * @param puzzlepiece
     * @return
     */
    private GeneralPath getShapeOnConnectorPosition(ConnectorPosition position, Puzzlepiece puzzlepiece) {
        int shapeSize = 100; // each shape has a size of 100 x 100

        GeneralPath gp = new GeneralPath(puzzlepiece.getConnectorForDirection(position).getShape());
        AffineTransform af;

        // get position of puzzlepiece in the group
        int xStart = piecegroup.getXPositionOfPieceInGroup(puzzlepiece) * puzzlearea.getPuzzlepieceWidth();
        int yStart = piecegroup.getYPositionOfPieceInGroup(puzzlepiece) * puzzlearea.getPuzzlepieceHeight();

        af = AffineTransform.getTranslateInstance(getConnectionsSizeLeftRight(), getConnectionsSizeTopButtom());

        // move the connection to the position of the puzzlepiece
        af.translate(xStart, yStart);

        // this is left -> do nothing for left
        if (position.equals(ConnectorPosition.LEFT)) {
        }

        // mirror and move to right
        if (position.equals(ConnectorPosition.RIGHT)) {
            af.translate(puzzlearea.getPuzzlepieceWidth() / 2, 0);
            af.scale(-1, 1);
            af.translate(-puzzlearea.getPuzzlepieceWidth() / 2, 0);
        }

        // move to top
        if (position.equals(ConnectorPosition.TOP)
                || position.equals(ConnectorPosition.BUTTOM)) {
            //double x = 1.5; //test        104    166,6666     1,60
            //double x = 1.69; //test2       88,75   118,34     1,333
            //double x = 2; //test3         75   75                1
            double x = 3 - (puzzlearea.getPuzzlepieceWidth() + shapeSize / 2) / ((double) puzzlearea.getPuzzlepieceHeight() + shapeSize / 2);
            af.translate(puzzlearea.getPuzzlepieceHeight() / x, puzzlearea.getPuzzlepieceWidth() / x);
            af.rotate(Math.PI / 2);
            af.translate(-puzzlearea.getPuzzlepieceWidth() / x, -puzzlearea.getPuzzlepieceHeight() / x);
        }

        // mirror and move to buttom
        if (position.equals(ConnectorPosition.BUTTOM)) {
            af.translate(puzzlearea.getPuzzlepieceHeight() / 2, 0);
            af.scale(-1, 1);
            af.translate(-puzzlearea.getPuzzlepieceHeight() / 2, 0);
        }

        // consider also out-conections and mirror it outside
        if (puzzlepiece.isOutPieceInDirection(position)) {
            af.scale(-1, 1);
        }

        // scale the connection to the image size
        af.scale(puzzlearea.getPuzzlepieceWidth() / (double) shapeSize, puzzlearea.getPuzzlepieceHeight() / (double) shapeSize);

        // apply transform
        gp.transform(af);

        return gp;
    }

    /**
     * Gets the Size of the connection of the puzzlepieces.
     *
     * @return
     */
    private int getConnectionsSizeLeftRight() {
        return puzzlearea.getPuzzlepieceWidth() / 2;
    }

    /**
     * Gets the Size of the connection of the puzzlepieces.
     *
     * @return
     */
    private int getConnectionsSizeTopButtom() {
        return puzzlearea.getPuzzlepieceHeight() / 2;
    }

    /**
     * moves this piece before all other pieces such that it is in the
     * foreground now.
     */
    private void moveToFront() {
        puzzlearea.bringToFront(this);
    }

    /**
     * Paints one single puzzlepiece on the given graphics
     *
     * @param puzzlepiece
     * @param g2
     */
    private void paintPiece(Puzzlepiece puzzlepiece, Graphics2D g2) {
        g2.setStroke(new BasicStroke((float) 1.1));
        BufferedImage img = puzzlepiece.getImage();
        int puzzlepieceWidth = puzzlearea.getPuzzlepieceWidth();
        int puzzlepieceHeight = puzzlearea.getPuzzlepieceHeight();

        // get position of puzzlepiece in the group
        int xStart = piecegroup.getXPositionOfPieceInGroup(puzzlepiece) * puzzlearea.getPuzzlepieceWidth();
        int yStart = piecegroup.getYPositionOfPieceInGroup(puzzlepiece) * puzzlearea.getPuzzlepieceHeight();

        // draw the Connections to other puzzlepiecs
        PuzzlepieceConnection connection;
        Rectangle imgRect;
        Area area;
        GeneralPath gp;

        imgRect = new Rectangle(xStart + getConnectionsSizeLeftRight(), yStart + getConnectionsSizeTopButtom(), puzzlepieceWidth, puzzlepieceHeight);
        area = new Area(imgRect);
        for (ConnectorPosition position : ConnectorPosition.values()) {
            connection = puzzlepiece.getConnectorForDirection(position);
            if (connection == null) {
                continue;
            }
            // don't paint any shape, if the other piece of the connection is in the same group as this puzzlepiece
            if ((puzzlepiece.isInPieceInDirection(position) && piecegroup.isPuzzlepieceContained(connection.getOutPuzzlepiece()))
                    || (puzzlepiece.isOutPieceInDirection(position) && piecegroup.isPuzzlepieceContained(connection.getInPuzzlepiece()))) {
                continue;
            }

            // update piece
            gp = getShapeOnConnectorPosition(position, puzzlepiece);
            if (puzzlepiece.isInPieceInDirection(position)) {
                // 'delete' connection-space in the in-pieces
                area.subtract(new Area(gp));
            } else {
                // fill out-connectors with the 'deleted' image of the other piece
                BufferedImage conImg = connection.getInPuzzlepiece().getImage();
                Shape oldClip = g2.getClip();
                g2.setClip(gp);

                switch (position) {
                    case LEFT:
                        g2.drawImage(conImg,
                                xStart - getConnectionsSizeLeftRight(), yStart + getConnectionsSizeTopButtom(),
                                xStart + puzzlepieceWidth - getConnectionsSizeLeftRight(), yStart + puzzlepieceHeight + getConnectionsSizeTopButtom(),
                                0, 0,
                                conImg.getWidth(), conImg.getHeight(),
                                null);
                        break;
                    case RIGHT:
                        g2.drawImage(conImg,
                                xStart + puzzlepieceWidth + getConnectionsSizeLeftRight(), yStart + getConnectionsSizeTopButtom(),
                                xStart + 2 * puzzlepieceWidth + getConnectionsSizeLeftRight(), yStart + puzzlepieceHeight + getConnectionsSizeTopButtom(),
                                0, 0,
                                conImg.getWidth(), conImg.getHeight(),
                                null);
                        break;
                    case TOP:
                        g2.drawImage(conImg,
                                xStart + getConnectionsSizeLeftRight(), yStart - getConnectionsSizeTopButtom(),
                                xStart + puzzlepieceWidth + getConnectionsSizeLeftRight(), yStart + puzzlepieceHeight - getConnectionsSizeTopButtom(),
                                0, 0,
                                conImg.getWidth(), conImg.getHeight(),
                                null);
                        break;
                    case BUTTOM:
                        g2.drawImage(conImg,
                                xStart + getConnectionsSizeLeftRight(), yStart + puzzlepieceHeight + getConnectionsSizeTopButtom(),
                                xStart + puzzlepieceWidth + getConnectionsSizeLeftRight(), yStart + 2 * puzzlepieceHeight + getConnectionsSizeTopButtom(),
                                0, 0,
                                conImg.getWidth(), conImg.getHeight(),
                                null);
                        break;
                }

                g2.setClip(oldClip);
            }
        }

        // draws the image chunk
        g2.setPaint(new TexturePaint(img, imgRect));
        g2.fill(area);
    }

    /**
     * Tries to let this puzzlepiece group snap with other puzzlepiece groups.
     */
    private void trySnapWithOtherGroups() {
        PuzzleController.getInstance().trySnapPuzzlepieceGroup(piecegroup);
    }

    /**
     * Updates the view of this puzzlepiece-view to the coordinates of the
     * puzzlepiece-model.
     */
    private void updateViewLocation() {
        int x = piecegroup.getX();
        int y = piecegroup.getY();

        setLocation(x - getConnectionsSizeLeftRight(), y - getConnectionsSizeTopButtom());
    }

    /**
     * Corrects the given point in that way, that this puzzlepiecegroup fits
     * into the puzzlearea. So it is not possible, tht the puzzlepiece is 'out
     * of the puzzlearea' and therefore not visible.
     *
     * @param p
     */
    private void correctPointToFitInPuzzleare(Point p) {
        if (puzzlearea.getWidth() <= getWidth() || puzzlearea.getHeight() <= getHeight()) {
            return;
        }
        if (p.x < 0) {
            p.x = 0;
        }
        if (p.x > puzzlearea.getWidth() - getWidthOfThisGroup() + 2 * getConnectionsSizeLeftRight()) {
            p.x = puzzlearea.getWidth() - getWidthOfThisGroup() + 2 * getConnectionsSizeLeftRight();
        }

        if (p.y < 0) {
            p.y = 0;
        }
        if (p.y > puzzlearea.getHeight() - getHeightOfThisGroup() + 2 * getConnectionsSizeTopButtom()) {
            p.y = puzzlearea.getHeight() - getHeightOfThisGroup() + 2 * getConnectionsSizeTopButtom();
        }
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

                correctPointToFitInPuzzleare(p);
                piecegroup.setPosition(p.x, p.y);
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
