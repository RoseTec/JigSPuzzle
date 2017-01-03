package jigspuzzle.view.desktop.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A class for a nicer JTabbedPane.
 *
 * @author RoseTec
 */
public class JTabbedPane extends javax.swing.JTabbedPane {

    public JTabbedPane() {
        init();
    }

    public JTabbedPane(int tabPlacement) {
        super(tabPlacement);
        init();
    }

    public JTabbedPane(int tabPlacement, int tabLayoutPolicy) {
        super(tabPlacement, tabLayoutPolicy);
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTab(String title, Component component) {
        this.addTab(title, null, component, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTab(String title, Icon icon, Component component) {
        this.addTab(title, icon, component, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);

        this.setTabComponentAt(getTabCount() - 1, createTabTitle(title, icon));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitleAt(int index, String title) {
        JPanel panel = (JPanel) this.getTabComponentAt(index);

        ((JLabel) panel.getComponents()[1]).setText(title);
    }

    private void init() {
    }

    private Component createTabTitle(String title, Icon icon) {
        // create nice looking tab
        JPanel rendererComponent = new JPanel(new BorderLayout());
        rendererComponent.setOpaque(false);

        rendererComponent.add(new JLabel(icon), BorderLayout.LINE_START);

        JLabel textLabel = new JLabel(title, SwingConstants.LEFT);
        textLabel.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        rendererComponent.add(textLabel, BorderLayout.CENTER);

        rendererComponent.setPreferredSize(new Dimension(100, 35));

        return rendererComponent;
    }

}
