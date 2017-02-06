package jigspuzzle.testutils.mockups;

import jigspuzzle.view.util.SelectionGroup;
import jigspuzzle.view.util.SelectionGroupSelectable;

/**
 * A dummy implementation of a SelectionGroupSelectable. Only fundamental
 * opperations are implemented.
 *
 * @author RoseTec
 * @param <T>
 */
public class DummySelectionGroupSelectable<T> implements SelectionGroupSelectable<T> {

    private T value;

    private SelectionGroup<T> selectionGroup;

    @Override
    public T getSelectionValue() {
        return this.value;
    }

    @Override
    public void setSelectionGroup(SelectionGroup<T> selectionGroup) {
        this.selectionGroup = selectionGroup;
    }

    @Override
    public void setSelectionValue(T value) {
        this.value = value;
    }

}
