package jigspuzzle.view.util;

/**
 * A single object that can be seleced in a selection group.
 *
 * @author RoseTec
 * @param <T> The class for the values that this obect represent.
 * @see SelectionGroup
 */
public interface SelectionGroupSelectable<T> {

    /**
     * Selts the selection group, in that this object is contained.
     *
     * @param selectionGroup
     */
    public void setSelectionGroup(SelectionGroup<T> selectionGroup);

    /**
     * The value that is given back, when with object is selected.
     *
     * @return
     */
    public T getSelectionValue();

    /**
     *
     * @param value The value that is given bac, when with object is selected.
     */
    void setSelectionValue(T value);

}
