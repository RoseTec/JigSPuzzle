package jigspuzzle.view.desktop.puzzle;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.JLayeredPane;
import jigspuzzle.controller.PuzzleController;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.model.settings.PuzzleareaSettings;

/**
 * This is the area for the puzzle. The User can see the puzzlepieces here and
 * arrange them.
 *
 * @author RoseTec
 */
public class Puzzlearea extends JLayeredPane {

    /**
     * The current puzzle that the user tries to solve.
     */
    private Puzzle puzzle;

    public Puzzlearea() {
        this.setLayout(null);
        this.setOpaque(true);
        this.setName("puzzlearea");

        // register observer for setting the background color in the main window for live preview
        SettingsController.getInstance().addPuzzleareaSettingsObserver((Observable o, Object arg) -> {
            PuzzleareaSettings settings = (PuzzleareaSettings) o;
            Puzzlearea.this.setBackground(settings.getPuzzleareaBackgroundColor());
        });
        this.setBackground(SettingsController.getInstance().getPuzzleareaBackgroundColor());

        // enable drag&drop into this panel to create a puzzle
        DropTarget dt = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                Transferable transferable = dtde.getTransferable();
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrop(dtde.getDropAction());
                    try {
                        List<File> transferData = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                        if (transferData != null && transferData.size() > 0) {
                            new Thread(() -> {
                                try {
                                    PuzzleController.getInstance().newPuzzle(transferData.get(0), Puzzlearea.this.getHeight(), Puzzlearea.this.getWidth());
                                } catch (IOException ex) {
                                }
                            }).start();
                            dtde.dropComplete(true);
                        }
                    } catch (Exception ex) {
                        dtde.rejectDrop();
                    }
                } else {
                    dtde.rejectDrop();
                }
            }
        }, true);
    }

    /**
     * Brings the given puzzlepiece view to the front so that it is displayed
     * above all other puzzlepieces.
     *
     * @param puzzlepieceView
     */
    public void bringToFront(PuzzlepieceView puzzlepieceView) {
        moveToFront(puzzlepieceView);
    }

    /**
     * Brings the given puzzlepiece group to the front so that it is displayed
     * above all other puzzlepieces.
     *
     * @param puzzlepieceGroup
     */
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
        // find the view of the given group
        PuzzlepieceView groupView = null;

        for (Component comp : this.getComponents()) {
            if (comp instanceof PuzzlepieceView) {
                PuzzlepieceView group = (PuzzlepieceView) comp;

                if (group.belongsToPuzzlepieceGroup(puzzlepieceGroup)) {
                    groupView = group;
                    break;
                }
            }
        }
        if (groupView == null) {
            return;
        }

        //bring it to the front
        bringToFront(groupView);
    }

    /**
     * Deletes the current puzzle from the puzzlearea. After calling this
     * method, the puzzlearea will be empty.
     */
    public void deletePuzzle() {
        puzzle = null;
        removeAll();
        repaint();
    }

    /**
     * Gets the height of one puzzlepiece that is shown to the user.
     *
     * @return
     */
    public int getPuzzlepieceHeight() {
        return SettingsController.getInstance().getPuzzlepieceSize(this.getHeight(), this.getWidth()).height;
    }

    /**
     * Gets the width of one puzzlepiece that is shown to the user.
     *
     * @return
     */
    public int getPuzzlepieceWidth() {
        return SettingsController.getInstance().getPuzzlepieceSize(this.getHeight(), this.getWidth()).width;
    }

    /**
     * Sets a new puzzle to this Puzzlearea
     *
     * @param puzzle
     */
    public void setNewPuzzle(Puzzle puzzle) {
        // delete old puzzle before
        this.deletePuzzle();

        // set new puzzle
        List<PuzzlepieceGroup> piecegroups = puzzle.getPuzzlepieceGroups();
        List<PuzzlepieceView> piecegroupsViews = new ArrayList<>();

        for (int i = 0; i < piecegroups.size(); i++) {
            piecegroupsViews.add(null);
        }

        this.puzzle = puzzle;
        for (int x = 0; x < puzzle.getRowCount(); x++) {
            for (int y = 0; y < puzzle.getColumnCount(); y++) {
                int listIndex = x * puzzle.getColumnCount() + y;
                PuzzlepieceGroup group = piecegroups.get(listIndex);

                PuzzlepieceView newView = new PuzzlepieceView(this, group);
                newView.setName("puzzlepiece-group-" + x + "-" + y); // name is needed for tests
                piecegroupsViews.set(listIndex, newView);
            }
        }

        // display new puzzle
        setVisible(false);
        for (int x = 0; x < puzzle.getRowCount(); x++) {
            for (int y = 0; y < puzzle.getColumnCount(); y++) {
                int listIndex = x * puzzle.getColumnCount() + y;
                PuzzlepieceView newView = piecegroupsViews.get(listIndex);
                PuzzlepieceGroup group = piecegroups.get(listIndex);

                add(newView);
                group.addObserver((Observable o, Object arg) -> {
                    if (!group.isInPuzzle()) {
                        Puzzlearea.this.remove(newView);
                        repaint();
                    }
                });
            }
        }
        setVisible(true);
    }

}
