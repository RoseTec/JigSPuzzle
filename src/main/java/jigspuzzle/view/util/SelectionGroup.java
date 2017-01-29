package jigspuzzle.view.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A selection group is a group of several objects that can be selected. In a
 * group only one of the objects is selected at a time. If another object is
 * selected, the previous selected object is not selected any more.
 *
 * @author RoseTec
 * @param <T> The class for the values that this obect represent.
 * @see SelectionGroupSelectable
 */
public class SelectionGroup<T> {

    private List<ChangeListener> changeListeners = new ArrayList<>();

    private Map<T, SelectionGroupSelectable<T>> selectors = new HashMap<>();

    /**
     * The object, that is currentlc selected.
     */
    private T selectedValue;

    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    /**
     * Adds the given obects for the given value to the selection.
     *
     * @param selector
     * @param value
     * @return the old value for the given value.
     */
    public SelectionGroupSelectable<T> addToSelection(SelectionGroupSelectable<T> selector, T value) {
        selector.setSelectionValue(value);
        selector.setSelectionGroup(this);
        return selectors.put(value, selector);
    }

    /**
     *
     * @return The currently selected value.
     */
    public T getSelectedValue() {
        return selectedValue;
    }

    /**
     * Check if the given selector is currently selected in this group.
     *
     * @param selector
     * @return
     */
    public boolean isSelected(SelectionGroupSelectable<T> selector) {
        return selectors.get(selectedValue) == selector;
    }

    /**
     * Selects the given object.
     *
     * @param value
     * @return The old value that was selected.
     */
    public T setSelectedValue(T value) {
        if (selectors.get(value) == null) {
            return null;
        } else {
            T oldValue = selectedValue;

            selectedValue = value;
            fireChangeListeners();
            return oldValue;
        }

    }

    private void fireChangeListeners() {
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

}
