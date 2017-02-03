package jigspuzzle.view.util;

import java.util.ArrayList;
import java.util.Arrays;
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

    private boolean onlyOneValueSelectable = true;

    private Map<T, SelectionGroupSelectable<T>> selectors = new HashMap<>();

    /**
     * The objects, that are currently selected.
     */
    private Map<T, Boolean> selectedValues = new HashMap<>();

    /**
     * Adds a listener to this group. This listener will be called, when a
     * selected value changed.
     *
     * @param listener
     */
    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    /**
     * Adds the given obects for the given value to the selection group.
     *
     * @param selector
     * @param value
     * @return the old value for the given value.
     */
    public SelectionGroupSelectable<T> addToSelectionGroup(SelectionGroupSelectable<T> selector, T value) {
        selector.setSelectionValue(value);
        selector.setSelectionGroup(this);
        selectedValues.put(value, false);
        return selectors.put(value, selector);
    }

    /**
     * Chnge the selected value for the given object.
     *
     * If the object is selected, the object will not be selected any more after
     * calling this method.<br>
     * If the object is not selected, the object will be selected after calling
     * this method.
     *
     * @param object
     */
    public void changeSelectedValue(SelectionGroupSelectable<T> object) {
        T value = object.getSelectionValue();

        if (selectors.get(value) != null) {
            selectedValues.put(value, !isValueSelected(value));
            checkOnlyOneSelected(value);
            fireChangeListeners();
        }
    }

    /**
     * Gets the value, wheather this group can only selects one value at a time.
     *
     * If only one value can be selected by this group and another value is
     * seleted, the currently selected value will no longer be selected.
     *
     * @return <code>true</code>, when this selection group can only select one
     * value at a time. <code>false</code> otherwise.
     *
     * Standard value is <code>true</code>.
     */
    public boolean getOnlyOneValueSelectable() {
        return onlyOneValueSelectable;
    }

    /**
     *
     * @return The currently selected value.
     */
    public List<T> getSelectedValues() {
        List<T> selectedList = new ArrayList<>();

        for (T key : selectedValues.keySet()) {
            if (isValueSelected(key)) {
                selectedList.add(key);
            }
        }
        return selectedList;
    }

    /**
     * Check if the given selector is currently selected in this group.
     *
     * @param selector
     * @return
     */
    public boolean isSelected(SelectionGroupSelectable<T> selector) {
        return isValueSelected(selector.getSelectionValue());
    }

    /**
     * Removes the given obects for the given value from the selection group.
     *
     * @param selector
     * @return the old value for the given value.
     */
    public SelectionGroupSelectable<T> removeFromSelectionGroup(SelectionGroupSelectable<T> selector) {
        T value = selector.getSelectionValue();

        selector.setSelectionGroup(null);
        selectedValues.remove(value);
        return selectors.remove(value);
    }

    /**
     * @see #getOnlyOneValueSelectable()
     */
    public void setOnlyOneValueSelectable(boolean onlyOneValueSelectable) {
        this.onlyOneValueSelectable = onlyOneValueSelectable;
    }

    /**
     * Selects the given object. After this call, the given object will be
     * selected.
     *
     * @param value
     * @see #changeSelectedValue(java.lang.Object)
     */
    public void setSelectedValue(T value) {
        if (selectors.get(value) == null) {
        } else {
            selectedValues.put(value, true);
            checkOnlyOneSelected(value);
            fireChangeListeners();
        }
    }

    /**
     * Selects the given values. After this call, the given values will be
     * selected.
     *
     * @param values
     * @param forceUnselectOther <code>true</code> if the the other values in
     * this group should be forced to unselected.
     */
    public void setSelectedValues(T[] values, boolean forceUnselectOther) {
        boolean hasChanged = false;
        List<T> keySet = new ArrayList<>(selectedValues.keySet());
        List<T> valueList = new ArrayList<>(Arrays.asList(values));

        for (int i = 0; i < keySet.size(); i++) {
            if (valueList.contains(keySet.get(i))) {
                if (selectedValues.put(keySet.get(i), true) == false) {
                    hasChanged = true;
                }
            } else if (!valueList.contains(keySet.get(i)) && forceUnselectOther) {
                if (selectedValues.put(keySet.get(i), false) == true) {
                    hasChanged = true;
                }
            }
        }
        if (hasChanged) {
            checkOnlyOneSelected(null);
            fireChangeListeners();
        }
    }

    /**
     * Checks, if it is true that only one value is selected after the given
     * value has changed. This option does nothing, when
     * <code>onlyOneValueSelectable == false</code>.
     *
     * @param value
     */
    private void checkOnlyOneSelected(T value) {
        if (!onlyOneValueSelectable) {
            return;
        }
        if (value == null && getSelectedValues().size() > 0) {
            value = getSelectedValues().get(0);
        }

        for (T object : selectedValues.keySet()) {
            if (value != object && isValueSelected(object)) {
                selectedValues.put(object, false);
            }
        }
    }

    private void fireChangeListeners() {
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    private boolean isValueSelected(T object) {
        Boolean value = selectedValues.get(object);
        return value != null && value.equals(true);
    }

}
