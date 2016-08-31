package jigspuzzle.controller;

/**
 * An abstract controller. A controller is for comunicting with the models from
 * the views.
 *
 * @author RoseTec
 */
public abstract class AbstractController {

    /**
     * Controllers are implemented as Singleton, that can be called by the
     * application any time. They are therefore availible over the complete
     * runtime of the program. However, in the assertJ-tests, it is not possible
     * to exit the program.<br>
     * Therefore all controllers have to be reset.
     *
     * It is simple possible by the following code:<br>
     * <code>
     * private static Controller instance;
     *
     * public static Controller getInstance() {
     *     // implement getInsance()....
     * }
     *
     * public void resetInstance() {
     *     // ---------------------------
     *     // this resets the instance:
     *     // ---------------------------
     *     instance = null;
     * }
     * </code>
     *
     */
    public abstract void resetInstance();

}
