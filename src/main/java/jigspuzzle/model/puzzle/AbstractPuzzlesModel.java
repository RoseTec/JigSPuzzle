package jigspuzzle.model.puzzle;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import jigspuzzle.model.Savable;
import jigspuzzle.util.ImageUtil;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Element;

/**
 * A class that is the superclass of all models in the puzle package.
 *
 * @author RoseTec
 */
public abstract class AbstractPuzzlesModel implements Savable {

    private static int LAST_ID_COUNT = 0;

    private int id;

    public AbstractPuzzlesModel() {
        id = LAST_ID_COUNT++;
    }

    /**
     * The ID of this model.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of this model.
     *
     * <b>Use carefull!</b> This methos overrides the id of the model. This is
     * the case when loading an existing puzzle.
     *
     * @param id
     */
    void setId(int id) {
        this.id = id;
    }

    /**
     * Loads the ID of this model from the given element.
     *
     * @param node
     * @see Savable
     * @see #saveIdToElement(org.w3c.dom.Element)
     */
    protected void loadIdFromElement(Element node) {
        this.setId(Integer.parseInt(node.getAttribute("id")));
    }

    /**
     * Loads an image from the given Element.
     *
     * @param node
     * @return
     * @throws java.io.IOException
     * @see Savable
     * @see #saveImageToElement(org.w3c.dom.Element, java.awt.Image)
     */
    protected Image loadImageFromElement(Element node) throws IOException {
        //TODO: move this to a direct subclass of Savable?
        // source: http://stackoverflow.com/a/1313180
        String encodedImage = node.getTextContent();
        byte[] bytes = Base64.decodeBase64(encodedImage);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        return image;
    }

    /**
     * Saves the ID of this model to the given element.
     *
     * @param node
     * @see Savable
     * @see #loadIdFromElement(org.w3c.dom.Element)
     */
    protected void saveIdToElement(Element node) {
        node.setAttribute("id", String.valueOf(this.getId()));
    }

    /**
     * Saves an image to the given element.
     *
     * <b>Important:</b> This method will NOT create a element for the image,
     * but save it only in the given element.
     *
     * @param node
     * @param img
     * @throws java.io.IOException
     * @see Savable
     * @see #loadImageFromElement(org.w3c.dom.Element)
     */
    protected void saveImageToElement(Element node, Image img) throws IOException {
        //TODO: move this to a direct subclass of Savable?
        // source: http://stackoverflow.com/a/1313180
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(ImageUtil.transformImageToBufferedImage(img), "png", baos);
            baos.flush();
            String encodedImage = Base64.encodeBase64String(baos.toByteArray());
            node.setTextContent(encodedImage);
        } finally {
            baos.close();
        }
    }

}
