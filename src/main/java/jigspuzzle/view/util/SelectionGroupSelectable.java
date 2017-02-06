package jigspuzzle.view.util;

import java.awt.Color;

/**
 * A single object that can be seleced in a selection group.
 *
 * @author RoseTec
 * @param <T> The class for the values that this obect represent.
 * @see SelectionGroup
 */
public interface SelectionGroupSelectable<T> {

    public static Color COLOR_SELECTED_OBJECT = new Color(106, 188, 255, 127);

    /**
     * The value that is given back, when with object is selected.
     *
     * @return
     */
    public T getSelectionValue();

    /**
     * Selts the selection group, in that this object is contained.
     *
     * @param selectionGroup
     */
    public void setSelectionGroup(SelectionGroup<T> selectionGroup);

    /**
     *
     * @param value The value that is given back, when with object is selected.
     */
    void setSelectionValue(T value);

}
