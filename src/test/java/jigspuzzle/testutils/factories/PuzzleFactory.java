package jigspuzzle.testutils.factories;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import jigspuzzle.model.puzzle.Puzzle;

class PuzzleFactory extends AbstractFactory {

    @Override
    Object createObject() throws Exception {
        BufferedImage image = ImageIO.read(new File(getImageDir() + "test_puzzle.jpg"));
        int rowCount = 3;
        int columnCount = 3;

        Puzzle puzzle = new Puzzle(image, rowCount, columnCount, image.getWidth() / columnCount, image.getHeight() / rowCount);

        return puzzle;
    }

}
