package jigspuzzle.view.desktop.swing;

import java.awt.Component;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

/**
 * A class for a nicer JComboBox
 *
 * @author RoseTec
 */
public class JComboBox<E> extends javax.swing.JComboBox<E> {

    public JComboBox(ComboBoxModel aModel) {
        super(aModel);
        init();
    }

    public JComboBox(E[] items) {
        super(items);
        init();
    }

    public JComboBox(Vector items) {
        super(items);
        init();
    }

    public JComboBox() {
        init();
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Dimension getPreferredSize() {
//        String currentText;
//        if (getSelectedItem() == null) {
//
//            currentText = "";
//        } else {
//            currentText = getSelectedItem().toString();
//        }
//        return new Dimension(currentText.length() * 6 + 25/*'arrow down' on right side*/ + 15/*some padding*/, 30);
//    }
    private void init() {
//        this.setBorder(new EmptyBorder(0, 7, 0, 0));

        // padding for the cells
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (comp instanceof JLabel) {
                    int padding = 6;

                    ((JLabel) comp).setBorder(new EmptyBorder(padding, padding, padding, 0));
                }
                return comp;
            }

        });
    }

}
