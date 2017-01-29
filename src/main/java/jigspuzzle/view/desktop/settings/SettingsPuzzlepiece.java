package jigspuzzle.view.desktop.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import jigspuzzle.model.puzzle.ConnectorPosition;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.Puzzlepiece;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.view.desktop.puzzle.DrawablePuzzlepieceGroup;

/**
 * This class represents a view for a puzzlepiece. One can add a puzzlepiece to
 * this container in order to show it on the settings window.
 *
 * It can hold a special puzzlepiece that shows only one special property of the
 * appearance of a puzzlepiece.
 *
 * By default, a puzzlepiece here will be displayed in one color and not having
 * an image.
 *
 * @author RoseTec
 */
class SettingsPuzzlepiece extends JPanel {

    private SimpleDrawablePuzzlepiece puzzlepieceView;

    private Puzzlepiece puzzlepieceToShow;

    public SettingsPuzzlepiece() {
        setBackground(Color.WHITE);
        setLayout(null);

        // create Image for the puzzlepiece to show
        Color puzzlepieceColor = new Color(0, 120, 215);
        BufferedImage img = new BufferedImage(3 * getPuzzlepieceWidth(), 3 * getPuzzlepieceHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i1 = 0; i1 < img.getHeight(); i1++) {
            for (int i2 = 0; i2 < img.getWidth(); i2++) {
                img.setRGB(i1, i2, puzzlepieceColor.getRGB());
            }
        }

        // create a puzzlepiece with 4 connectors
        Puzzle puzzle = new Puzzle(img, 3, 3, getPuzzlepieceWidth(), getPuzzlepieceHeight());
        puzzlepieceToShow = puzzle.getPuzzlepieceGroups().get(4).getPuzzlepieces().get(0);

        // make connectors of the puzzlepiece nicer: top,buttom:out; left,right:in
        for (ConnectorPosition position : new ConnectorPosition[]{ConnectorPosition.LEFT, ConnectorPosition.RIGHT}) {
            if (puzzlepieceToShow == puzzlepieceToShow.getConnectorForDirection(position).getOutPuzzlepiece()) {
                puzzlepieceToShow.getConnectorForDirection(position).convertPuzzlepieceConnections();
            }
        }
        for (ConnectorPosition position : new ConnectorPosition[]{ConnectorPosition.TOP, ConnectorPosition.BUTTOM}) {
            if (puzzlepieceToShow == puzzlepieceToShow.getConnectorForDirection(position).getInPuzzlepiece()) {
                puzzlepieceToShow.getConnectorForDirection(position).convertPuzzlepieceConnections();
            }
        }

        // create the view for a puzzlepiece in the middle
        puzzlepieceView = new SimpleDrawablePuzzlepiece(this.puzzlepieceToShow.getPuzzlepieceGroup());
        puzzlepieceToShow.getPuzzlepieceGroup().setPosition(getOffsetLeftRight(), puzzlepieceView.getConnectionsSizeTopButtom());
        add(puzzlepieceView);
    }

    /**
     * Converts the shape of the connectors to the one with the given id.
     *
     * @param ShapeId
     * @return This SettingsPuzzlepiece object
     */
    public SettingsPuzzlepiece withConnectorShape(int ShapeId) {
        for (ConnectorPosition position : ConnectorPosition.values()) {
            puzzlepieceToShow.getConnectorForDirection(position).setShape(ShapeId);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return puzzlepieceView.getPreferredSize();
    }

    /**
     * The distance between the puzzlepiece on the left/right and the start/end
     * of this panel.
     *
     * @return
     */
    private int getOffsetLeftRight() {
        return 10;
    }

    /**
     * The height of a puzzlepiece in the settings view.
     *
     * @return
     */
    private int getPuzzlepieceHeight() {
        return 50;
    }

    /**
     * The width of a puzzlepiece in the settings view.
     *
     * @return
     */
    private int getPuzzlepieceWidth() {
        return getPuzzlepieceHeight();
    }

    private class SimpleDrawablePuzzlepiece extends DrawablePuzzlepieceGroup {

        public SimpleDrawablePuzzlepiece(PuzzlepieceGroup pieceGroup) {
            super(pieceGroup);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected int getPuzzlepieceHeight() {
            return SettingsPuzzlepiece.this.getPuzzlepieceHeight();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected int getPuzzlepieceWidth() {
            return SettingsPuzzlepiece.this.getPuzzlepieceWidth();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getConnectionsSizeLeftRight() {
            return super.getConnectionsSizeLeftRight();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getConnectionsSizeTopButtom() {
            return super.getConnectionsSizeTopButtom();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(getPuzzlepieceWidth() + 2 * getOffsetLeftRight(), // all puzzlepieces have in-connectors left and right
                    getPuzzlepieceHeight() + 2 * getConnectionsSizeTopButtom());
        }

    }

}
