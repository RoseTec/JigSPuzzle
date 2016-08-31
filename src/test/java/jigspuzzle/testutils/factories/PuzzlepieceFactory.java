package jigspuzzle.testutils.factories;

import java.awt.image.BufferedImage;
import jigspuzzle.model.puzzle.Puzzlepiece;

class PuzzlepieceFactory extends AbstractFactory {

    @Override
    Object createObject() {
        BufferedImage img = null;

        return new Puzzlepiece(img);
    }

}
