package jigspuzzle.view.util;

import java.util.Arrays;
import java.util.List;
import jigspuzzle.testutils.mockups.DummySelectionGroupSelectable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SelectionGroupIT {

    public SelectionGroupIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testChangeSelectedValue() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object = new DummySelectionGroupSelectable<>();

        instance.addToSelectionGroup(object, 2);
        assertFalse(instance.isSelected(object));

        instance.changeSelectedValue(object);
        assertTrue(instance.isSelected(object));

        instance.changeSelectedValue(object);
        assertFalse(instance.isSelected(object));
    }

    @Test
    public void testGetSelectedValues1() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object1 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object2 = new DummySelectionGroupSelectable<>();

        instance.addToSelectionGroup(object1, 1);
        instance.addToSelectionGroup(object2, 2);
        instance.changeSelectedValue(object1);
        instance.changeSelectedValue(object2); // this unselects the 1st value

        List expList = Arrays.asList(2);
        List result = instance.getSelectedValues();
        assertEquals(expList, result);

        assertFalse(instance.isSelected(object1));
        assertTrue(instance.isSelected(object2));
    }

    @Test
    public void testGetSelectedValues2() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object1 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object2 = new DummySelectionGroupSelectable<>();

        instance.setOnlyOneValueSelectable(false);
        instance.addToSelectionGroup(object1, 1);
        instance.addToSelectionGroup(object2, 2);
        instance.changeSelectedValue(object1);
        instance.changeSelectedValue(object2);

        List expList = Arrays.asList(1, 2);
        List result = instance.getSelectedValues();
        assertEquals(expList, result);
    }

    @Test
    public void testRemoveFromSelectionGroup() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object = new DummySelectionGroupSelectable<>();

        instance.addToSelectionGroup(object, 1);
        instance.setSelectedValue(1);
        SelectionGroupSelectable result = instance.removeFromSelectionGroup(object);

        assertFalse(instance.isSelected(object));
        assertEquals(object, result);
    }

    @Test
    public void testSetSelectedValue() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object = new DummySelectionGroupSelectable<>();
        Integer value = 1;

        instance.addToSelectionGroup(object, value);
        instance.setSelectedValue(value);

        assertTrue(instance.isSelected(object));

        instance.setSelectedValue(value);
        assertTrue(instance.isSelected(object));
    }

    @Test
    public void testSetSelectedValues1() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object1 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object2 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object3 = new DummySelectionGroupSelectable<>();

        instance.setOnlyOneValueSelectable(false);
        instance.addToSelectionGroup(object1, 1);
        instance.addToSelectionGroup(object2, 2);
        instance.addToSelectionGroup(object3, 3);
        instance.setSelectedValue(2);
        instance.setSelectedValues(new Integer[]{1, 3}, true);

        assertTrue(instance.isSelected(object1));
        assertFalse(instance.isSelected(object2));
        assertTrue(instance.isSelected(object3));
    }

    @Test
    public void testSetSelectedValues2() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object1 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object2 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object3 = new DummySelectionGroupSelectable<>();

        instance.addToSelectionGroup(object1, 1);
        instance.addToSelectionGroup(object2, 2);
        instance.addToSelectionGroup(object3, 3);
        instance.setSelectedValue(2);
        instance.setSelectedValues(new Integer[]{1, 3}, true);

        assertTrue(instance.isSelected(object1));
        assertFalse(instance.isSelected(object2));
        assertFalse(instance.isSelected(object3));
    }

    @Test
    public void testSetSelectedValues3() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object1 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object2 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object3 = new DummySelectionGroupSelectable<>();

        instance.setOnlyOneValueSelectable(false);
        instance.addToSelectionGroup(object1, 1);
        instance.addToSelectionGroup(object2, 2);
        instance.addToSelectionGroup(object3, 3);
        instance.setSelectedValues(new Integer[]{2}, true);

        assertFalse(instance.isSelected(object1));
        assertTrue(instance.isSelected(object2));
        assertFalse(instance.isSelected(object3));
    }

}
