package jigspuzzle.model.puzzle;

/**
 * An enum for the position for the connecors of one puzzlepiece to another
 * puzzlepiece.
 * 
 * @author RoseTec
 */
public enum ConnectorPosition {
    LEFT(0),
    RIGHT(1),
    TOP(2),
    BUTTOM(3);

    /**
     *
     * @return The number of values, that this enum has
     */
    public static int numberOfElements() {
        return ConnectorPosition.values().length;
    }

    private int intValue;

    private ConnectorPosition(int intValue) {
        this.intValue = intValue;
    }

    /**
     * Return the opposite direction of the this enum value. Consider: It
     * allways holds:
     * <code>position.getOpposite().getOpposite() == position</code>
     *
     * @return
     */
    public ConnectorPosition getOpposite() {
        switch (this) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case TOP:
                return BUTTOM;
            case BUTTOM:
                return TOP;
            default:
                return null;
        }
    }

    /**
     * An int value of this enum. It is allways greater or equal to zero.
     *
     * @return
     */
    public int intValue() {
        return intValue;
    }

}
