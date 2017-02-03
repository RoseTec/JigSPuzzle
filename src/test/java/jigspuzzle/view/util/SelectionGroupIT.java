package jigspuzzle.view.util;

import java.util.Arrays;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    public void testAddEmptyListener() {
        SelectionGroup<Integer> instance = new SelectionGroup<>();
        SelectionGroupSelectable<Integer> object1 = new DummySelectionGroupSelectable<>();
        SelectionGroupSelectable<Integer> object2 = new DummySelectionGroupSelectable<>();

        instance.setOnlyOneValueSelectable(false);
        instance.addToSelectionGroup(object1, 1);
        instance.addToSelectionGroup(object2, 2);
        instance.changeSelectedValue(object1);
        instance.changeSelectedValue(object2);

        instance.addEmptyListener((ChangeEvent e) -> {
            // change 2
            assertEquals(0, instance.getSelectedValues().size());
            instance.addToSelectionGroup(object2, 2);
            assertFalse(instance.isSelected(object2));
            instance.changeSelectedValue(object2);
        });
        instance.addChangeListener((ChangeEvent e) -> {
            // change 1 and
            // change 2 after adding something in the empty listener
            assertEquals(1, instance.getSelectedValues().size());
        });
        instance.changeSelectedValue(object1);
        instance.changeSelectedValue(object2);

        assertEquals(1, instance.getSelectedValues().size());
        assertTrue(instance.isSelected(object2));
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

}
