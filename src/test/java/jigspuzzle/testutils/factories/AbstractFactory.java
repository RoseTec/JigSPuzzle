package jigspuzzle.testutils.factories;

/**
 * A template for a factory.
 *
 * @author RoseTec
 */
abstract class AbstractFactory {

    /**
     * Creates an instance of the class that this factory is made for.
     *
     * @return
     * @throws
     */
    abstract Object createObject() throws Exception;

    /**
     * Gets the direcory in that all images for tests are stored.
     *
     * @return
     */
    protected String getImageDir() {
        return "../src/test/images/";
    }

}
