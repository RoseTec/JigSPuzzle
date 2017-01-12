package jigspuzzle.view.desktop.puzzle;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;
import javax.swing.JPanel;
import jigspuzzle.controller.PuzzleController;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.model.settings.PuzzleareaSettings;

/**
 * A class for displaying the preview of the images of a puzzle.
 *
 * @author RoseTec
 */
public class PuzzlePreview extends JPanel {

    /**
     * The maximal size of the preview. The aspect ratio will be kept, so it can
     * also be less then the number here.
     */
    private int MAX_SIZE = 300;

    public PuzzlePreview() {
        this.setName("puzzle-preview");
        this.setOpaque(false);

        // set size and position
        this.setLocation(0, 0);
        this.setSize(MAX_SIZE, MAX_SIZE);

        // register observer for puzzlearea settings
        SettingsController.getInstance().addPuzzleareaSettingsObserver((Observable o, Object arg) -> {
            PuzzleareaSettings settings = (PuzzleareaSettings) o;

            // preview of the played puzzle
            this.setVisible(SettingsController.getInstance().getShowPuzzlePreview());
        });
        this.setVisible(SettingsController.getInstance().getShowPuzzlePreview());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        // paint preview of this image
        if (SettingsController.getInstance().getShowPuzzlePreview() && PuzzleController.getInstance().isPuzzleAcive()) {
            Image previewImg = PuzzleController.getInstance().getPuzzlepieceImage();
            int height, width;

            if (previewImg.getHeight(null) < previewImg.getWidth(null)) {
                width = MAX_SIZE;
                height = (int) (width * (previewImg.getHeight(null) / (float) previewImg.getWidth(null)));
            } else {
                height = MAX_SIZE;
                width = (int) (height * (previewImg.getWidth(null) / (float) previewImg.getHeight(null)));
            }
            g.drawImage(previewImg, 0, 0, width, height, null);
        }

        // paint other parts
        super.paintComponent(g);
    }

}
