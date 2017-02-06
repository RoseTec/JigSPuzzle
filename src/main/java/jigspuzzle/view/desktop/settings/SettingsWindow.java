package jigspuzzle.view.desktop.settings;

import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.model.puzzle.ConnectorShapeFactory;
import jigspuzzle.model.settings.PuzzleSettings;
import jigspuzzle.view.ImageGetter;
import jigspuzzle.view.desktop.swing.ErrorMessageDialog;
import jigspuzzle.view.desktop.swing.IconListRenderer;
import jigspuzzle.view.desktop.swing.ImageJPanel;
import jigspuzzle.view.desktop.swing.JButton;
import jigspuzzle.view.desktop.swing.JComboBox;
import jigspuzzle.view.desktop.swing.JRadioButton;
import jigspuzzle.view.desktop.swing.JScrollPane;
import jigspuzzle.view.desktop.swing.JTabbedPane;
import jigspuzzle.view.util.SelectionGroup;

/**
 * A Window, on which the User can change the settings for the game.
 *
 * @author RoseTec
 */
public class SettingsWindow extends javax.swing.JDialog {

    /**
     * The JLabel that is used on the ticking for the slider for the
     * snap-distance. This is the tick for the smallest value (5).
     */
    private JLabel sliderSnapDistanceSmall;

    /**
     * The JLabel that is used on the ticking for the slider for the
     * snap-distance. This is the tick for the highest value (100).
     */
    private JLabel sliderSnapDistanceBig;

    /**
     * The selection group for the shape of the puzzlepiece connectors.
     */
    private final SelectionGroup<Integer> selectionGroupConnectorShape;

    /**
     * The selection group for selecting monitors.
     */
    private final SelectionGroup<Integer> selectionGroupFullscreen;

    /**
     * The panel, that is used as viewport in the scoll pane for selection
     * monitors for fullscreen mode. Available monitors are added to thi panel
     * instead of the scroll pane directly.
     */
    JPanel jScrollPaneFullscreenMonitorsPanel;

    /**
     * Creates new form SettingsWindow
     */
    public SettingsWindow() {
//        super(parent, modal);
        initComponents();

        // modify the ticks of the sliders
        Hashtable<Integer, JLabel> tableWindowSize = new Hashtable<>();
        for (int i : new int[]{25, 50, 75, 100}) {
            tableWindowSize.put(i, new JLabel(i + " %"));
        }
        jSlider1.setLabelTable(tableWindowSize);

        Hashtable<Integer, JLabel> tableLockSize = new Hashtable<>();
        // real text is loaded in 'loadLanguageTexts()'.
        // Only here for initial setting of the sizes for the label in the slider.
        sliderSnapDistanceSmall = new JLabel("<html>5 %<br/>small</html>");
        sliderSnapDistanceBig = new JLabel("<html>100 %<br/>big</html>");
        tableLockSize.put(jSlider3.getMinimum(), sliderSnapDistanceSmall);
        tableLockSize.put(jSlider3.getMaximum(), sliderSnapDistanceBig);
        for (int i = 25; i < 100; i += 25) {
            tableLockSize.put(i, new JLabel(i + " %"));
        }
        jSlider3.setLabelTable(tableLockSize);

        // make the available languages choosable
        String[] allLanguages = SettingsController.getInstance().getAvailableLanguages();
        Map<String, Icon> comboboxLanguages = new HashMap<>();

        for (String lang : allLanguages) {
            jComboBox1.addItem(lang);
            comboboxLanguages.put(lang, new ImageIcon(ImageGetter.getInstance().getImageForLanguage(lang, 20)));
        }
        jComboBox1.setRenderer(new IconListRenderer(comboboxLanguages));
        jComboBox1.setSelectedItem(SettingsController.getInstance().getCurrentLanguage());

        // add listerner that updates the language based on the user choise of language
        jComboBox1.addActionListener((ActionEvent evt) -> {
            String newLanguage = (String) jComboBox1.getSelectedItem();

            SettingsController.getInstance().setCurrentLanguage(newLanguage);
        });

        // load language texts
        SettingsController.getInstance().addLanguageSettingsObserver((Observable o, Object arg) -> {
            loadLanguageTexts();
        });
        loadLanguageTexts();

        // Slider for puzzlepiece-number: automatically update the textfield and vise versa
        jSlider2.addChangeListener((ChangeEvent e) -> {
            SettingsController.getInstance().setPuzzlepieceNumber(jSlider2.getValue());
        });
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT
                        || e.getKeyCode() == KeyEvent.VK_RIGHT
                        || e.getKeyCode() == KeyEvent.VK_UP
                        || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    return;
                }
                try {
                    int newValue = Integer.parseInt(jTextField1.getText());
                    SettingsController.getInstance().setPuzzlepieceNumber(newValue);
                } catch (NumberFormatException ex) {
                }
            }

        });

        SettingsController.getInstance().addPuzzleSettingsObserver((Observable o, Object arg) -> {
            PuzzleSettings ps = (PuzzleSettings) o;
            jSlider2.setValue(ps.getPuzzlepieceNumber());
            jTextField1.setText(ps.getPuzzlepieceNumber() + "");
        });

        // make jColorChooser look not that bad
        jColorChooser1.setPreviewPanel(new JPanel());
        for (AbstractColorChooserPanel panel : jColorChooser1.getChooserPanels()) {
            if (!"HSL".equals(panel.getDisplayName())) {
                jColorChooser1.removeChooserPanel(panel);
            }
        }
        jColorChooser1.getSelectionModel().addChangeListener((ChangeEvent e) -> {
            // when selecting a color in the clorChooser, the clor should be visible in the preview
            changeBackgroundColor();
        });

        // register observer for setting the background color in the main window for live preview
        SettingsController.getInstance().addPuzzleareaSettingsObserver((Observable o, Object arg) -> {
            jPanel8.setBackground(jColorChooser1.getColor());
        });

        // button group for appearance of puzzlepiece connectors
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setSelected(true);

        // add puzzlepieces for scrollPane for showing puzzlepiece connectors
        JPanel jScrollPaneShapeAppearancePanel = new JPanel();
        List<Integer> allIds = ConnectorShapeFactory.getInstance().getAllConnectorShapeIds();
        selectionGroupConnectorShape = new SelectionGroup<>();

        jScrollPaneShapeAppearancePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        jScrollPaneShapeAppearance.setViewportView(jScrollPaneShapeAppearancePanel);

        for (int shapeId : allIds) {
            SettingsPuzzlepiece newPiece = new SettingsPuzzlepiece().withConnectorShape(shapeId);

            jScrollPaneShapeAppearancePanel.add(newPiece);
            selectionGroupConnectorShape.addToSelectionGroup(newPiece, shapeId);
        }
        selectionGroupConnectorShape.setSelectedValue(allIds.get(0));

        // add listener for changing the selectd value of the selection group for connector shapes
        selectionGroupConnectorShape.addChangeListener((ChangeEvent e) -> {
            SettingsController.getInstance().setPuzzlepieceConnectorShapeId(selectionGroupConnectorShape.getSelectedValues().get(0));
        });

        // adjust scroll panel for fullscreen selection
        jScrollPaneFullscreenMonitorsPanel = new JPanel();

        jScrollPaneFullscreenMonitorsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        jScrollPaneFullscreenMonitors.setViewportView(jScrollPaneFullscreenMonitorsPanel);

        // make monitors for fullsceen selectable
        selectionGroupFullscreen = new SelectionGroup<>();

        selectionGroupFullscreen.setOnlyOneValueSelectable(false);
        selectionGroupFullscreen.addChangeListener((ChangeEvent e) -> {
            SettingsController.getInstance().setMonitorForFullscreen(selectionGroupFullscreen.getSelectedValues());
        });
        SettingsController.getInstance().addPuzzleareaSettingsObserver((Observable o, Object arg) -> {
            List<Integer> selectedMonitors = SettingsController.getInstance().getMonitorsForFullscreen();

            selectionGroupFullscreen.setSelectedValues(selectedMonitors.toArray(new Integer[0]), true);
            jScrollPaneFullscreenMonitors.repaint();
        });
    }

    /**
     * Shows the settings window.
     *
     * Also sets the view in foreground where the user can change the appearence
     * of the UI.
     */
    public void showUiSettings() {
        jTabbedPane1.setSelectedIndex(0);
        if (!this.isVisible()) {
            beforeBeeingVisible();
            this.setVisible(true);
            this.setLocationRelativeTo(this.getParent());
        }
        this.requestFocus();
    }

    /**
     * Shows the settings window.
     *
     * Also sets the view in foreground where the user can change the settings
     * of the puzzles.
     */
    public void showPuzzleSettings() {
        jTabbedPane1.setSelectedIndex(1);
        if (!this.isVisible()) {
            beforeBeeingVisible();
            this.setVisible(true);
            this.setLocationRelativeTo(this.getParent());
        }
        this.requestFocus();
    }

    /**
     * This method is called before this window is beeing visible.
     */
    private void beforeBeeingVisible() {
        // renew information about available monitors for fullsceen
        int numberOfMonitorsAvalable = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;

        while (numberOfMonitorsAvalable != jScrollPaneFullscreenMonitorsPanel.getComponentCount()) {
            if (numberOfMonitorsAvalable < jScrollPaneFullscreenMonitorsPanel.getComponentCount()) {
                ImageJPanel monitorToRemove = (ImageJPanel) jScrollPaneFullscreenMonitorsPanel.getComponents()[jScrollPaneFullscreenMonitorsPanel.getComponentCount() - 1];
                jScrollPaneFullscreenMonitorsPanel.remove(monitorToRemove);
                selectionGroupFullscreen.removeFromSelectionGroup(monitorToRemove);
            } else if (numberOfMonitorsAvalable > jScrollPaneFullscreenMonitorsPanel.getComponentCount()) {
                ImageJPanel newPanel = new ImageJPanel(ImageGetter.getInstance().getMonitorImage());
                int marginTopButtom = 5;
                int marginLeftRight = 5;

                newPanel.setMargin(marginTopButtom, marginTopButtom, marginLeftRight, marginLeftRight);
                jScrollPaneFullscreenMonitorsPanel.add(newPanel);
                selectionGroupFullscreen.addToSelectionGroup(newPanel, jScrollPaneFullscreenMonitorsPanel.getComponentCount() - 1);
            }
        }

        // load settings
        loadSettings();
    }

    /**
     * Loads all texts from the settings in the current language.
     */
    private void loadLanguageTexts() {
        this.setTitle(SettingsController.getInstance().getLanguageText(10, 1));
        jButton1.setText(SettingsController.getInstance().getLanguageText(10, 2));
        jButton2.setText(SettingsController.getInstance().getLanguageText(10, 3));

        jLabel7.setText(SettingsController.getInstance().getLanguageText(10, 101));

        jTabbedPane1.setTitleAt(0, SettingsController.getInstance().getLanguageText(10, 11));
        jTabbedPane1.setTitleAt(1, SettingsController.getInstance().getLanguageText(10, 12));

        jLabel4.setText(SettingsController.getInstance().getLanguageText(10, 121));

        jCheckBox1.setText(SettingsController.getInstance().getLanguageText(10, 141));

        jLabel1.setText(SettingsController.getInstance().getLanguageText(10, 161));
        jCheckBox2.setText(SettingsController.getInstance().getLanguageText(10, 164));
        jCheckBox4.setText(SettingsController.getInstance().getLanguageText(10, 167));
        jCheckBox3.setText(SettingsController.getInstance().getLanguageText(10, 170));

        jLabel2.setText(SettingsController.getInstance().getLanguageText(10, 181));
        jLabel3.setText(SettingsController.getInstance().getLanguageText(10, 182));
        jLabel5.setText(SettingsController.getInstance().getLanguageText(10, 183));

        jLabel6.setText(SettingsController.getInstance().getLanguageText(10, 201));
        sliderSnapDistanceSmall.setText("<html><center>" + jSlider3.getMinimum() + " %<br/>"
                + SettingsController.getInstance().getLanguageText(10, 202) + "</center></html>");
        sliderSnapDistanceBig.setText("<html><center>" + jSlider3.getMaximum() + " %<br/>"
                + SettingsController.getInstance().getLanguageText(10, 203) + "</center></html>");

        jCheckBox5.setText(SettingsController.getInstance().getLanguageText(10, 222));

        jRadioButton1.setText(SettingsController.getInstance().getLanguageText(10, 241));
        jRadioButton2.setText(SettingsController.getInstance().getLanguageText(10, 245));

        jLabel8.setText(SettingsController.getInstance().getLanguageText(10, 261));
        jLabel10.setText(SettingsController.getInstance().getLanguageText(10, 262));
        jLabel9.setText(SettingsController.getInstance().getLanguageText(10, 265));

        repaint();
    }

    /**
     * Opens the settings for changing the background color
     */
    private void changeBackgroundColor() {
        // open color chooser

        // chnge the color in the settings
        SettingsController.getInstance().setPuzzleareaBackgroundColor(jColorChooser1.getColor());
    }

    /**
     * Loads the settings from the model. This method should be allways called,
     * when showing the window.
     */
    private void loadSettings() {
        try {
            // save the settings first, so we have a file from that we can load if the user cancels.
            SettingsController.getInstance().saveSettingsToFile();
        } catch (IOException ex) {
        }

        // background color
        jColorChooser1.setColor(SettingsController.getInstance().getPuzzleareaBackgroundColor());
        jPanel8.setBackground(jColorChooser1.getColor());

        // monitors used for fullscreen
        List<Integer> selectedMonitors = SettingsController.getInstance().getMonitorsForFullscreen();

        selectionGroupFullscreen.setSelectedValues(selectedMonitors.toArray(new Integer[0]), true);

        // number of puzzlepieces
        jSlider2.setValue(SettingsController.getInstance().getPuzzlepieceNumber());
        jTextField1.setText(SettingsController.getInstance().getPuzzlepieceNumber() + "");

        // distance for snapping for puzzlepieces
        jSlider3.setValue(SettingsController.getInstance().getPuzzlepieceSnapDistancePercent());

        // size of the puzzle
        jSlider1.setValue((int) (SettingsController.getInstance().getUsedSizeOfPuzzlearea() * 100));
        jCheckBox3.setSelected(SettingsController.getInstance().getEnlargePuzzleAutomatically());
        jCheckBox4.setSelected(SettingsController.getInstance().getDecreasePuzzleAutomatically());

        // preview of the puzzle
        jCheckBox1.setSelected(SettingsController.getInstance().getShowPuzzlePreview());

        // play sounds
        jCheckBox5.setSelected(SettingsController.getInstance().getPlaySounds());

        // shape of the puzzlepiece connectors
        jRadioButton2.setSelected(SettingsController.getInstance().getUseRandomConnectorShape());
        jRadioButton1.setSelected(!SettingsController.getInstance().getUseRandomConnectorShape());
        selectionGroupConnectorShape.setSelectedValue(SettingsController.getInstance().getPuzzlepieceConnectorShapeId());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new JTabbedPane();
        jScrollPane1 = new JScrollPane();
        jPanel2 = new SettingViewPanel();
        jPanel13 = new SettingsCategoryPanel(10, 100);
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new JComboBox<>();
        jPanel4 = new SettingsCategoryPanel(10, 120);
        jColorChooser1 = new javax.swing.JColorChooser();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new SettingsCategoryPanel(10, 220);
        jCheckBox5 = new javax.swing.JCheckBox();
        jPanel5 = new SettingsCategoryPanel(10, 140);
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel16 = new SettingsCategoryPanel(10, 260);
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new ExplainingJLabel();
        jScrollPaneFullscreenMonitors = new JScrollPane();
        jLabel9 = new ExplainingJLabel();
        jScrollPane2 = new JScrollPane();
        jPanel3 = new SettingViewPanel();
        jPanel6 = new SettingsCategoryPanel(10, 160);
        jLabel1 = new ExplainingJLabel();
        jSlider1 = new javax.swing.JSlider();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel11 = new SettingsCategoryPanel(10, 180);
        jSlider2 = new javax.swing.JSlider();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new ExplainingJLabel();
        jLabel5 = new ExplainingJLabel();
        jPanel12 = new SettingsCategoryPanel(10, 200);
        jLabel6 = new ExplainingJLabel();
        jSlider3 = new javax.swing.JSlider();
        jPanel15 = new SettingsCategoryPanel(10, 240);
        jRadioButton1 = new JRadioButton();
        jScrollPaneShapeAppearance = new JScrollPane();
        jRadioButton2 = new JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Einstellungen");
        setAlwaysOnTop(true);
        setIconImage(ImageGetter.getInstance().getJigSPuzzleImage());
        setMinimumSize(new java.awt.Dimension(400, 300));
        setName("settings"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 650));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setName("main-tabbed-pane"); // NOI18N

        jPanel2.setPreferredSize(new java.awt.Dimension(600, 348));

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setText("Used Language:");
        jPanel14.add(jLabel7);

        jComboBox1.setName("language-select"); // NOI18N
        jPanel14.add(jComboBox1);

        jPanel13.add(jPanel14);

        jPanel2.add(jPanel13);

        jColorChooser1.setName(""); // NOI18N
        jPanel4.add(jColorChooser1);

        jLabel4.setText("current color:");
        jPanel9.add(jLabel4);

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel8.setMaximumSize(new java.awt.Dimension(100, 40));
        jPanel8.setMinimumSize(new java.awt.Dimension(100, 40));
        jPanel8.setName("puzzelarea-background-color"); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });
        jPanel9.add(jPanel8);

        jPanel4.add(jPanel9);

        jPanel2.add(jPanel4);

        jCheckBox5.setText("Play Sounds");
        jCheckBox5.setName(""); // NOI18N
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        jPanel10.add(jCheckBox5);

        jPanel2.add(jPanel10);

        jCheckBox1.setText("Show a preview of the complete puzzle");
        jCheckBox1.setName("show-puzzle-preview"); // NOI18N
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel5.add(jCheckBox1);

        jPanel2.add(jPanel5);

        jLabel8.setText("jLabel8");
        jPanel16.add(jLabel8);

        jLabel10.setText("jLabel10");
        jPanel16.add(jLabel10);
        jPanel16.add(jScrollPaneFullscreenMonitors);

        jLabel9.setText("jLabel9");
        jPanel16.add(jLabel9);

        jPanel2.add(jPanel16);

        jScrollPane1.setViewportView(jPanel2);

        jTabbedPane1.addTab("Appearance", jScrollPane1);

        jPanel3.setPreferredSize(jPanel2.getPreferredSize());

        jLabel1.setText("Used Size of the puzzleare:");
        jPanel6.add(jLabel1);

        jSlider1.setMajorTickSpacing(25);
        jSlider1.setMinimum(25);
        jSlider1.setMinorTickSpacing(5);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setValue(100);
        jSlider1.setName("size-of-puzzleare"); // NOI18N
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        jPanel6.add(jSlider1);

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Keep aspect ratio");
        jCheckBox2.setEnabled(false);
        jPanel6.add(jCheckBox2);

        jCheckBox4.setSelected(true);
        jCheckBox4.setText("Decrease automatically");
        jCheckBox4.setEnabled(false);
        jCheckBox4.setName("puzzlearea-make-smaller"); // NOI18N
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel6.add(jCheckBox4);

        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Enlarge automatically");
        jCheckBox3.setName("puzzlearea-make-larger"); // NOI18N
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel6.add(jCheckBox3);

        jPanel3.add(jPanel6);

        jSlider2.setMajorTickSpacing(200);
        jSlider2.setMaximum(1000);
        jSlider2.setMinorTickSpacing(100);
        jSlider2.setPaintLabels(true);
        jSlider2.setPaintTicks(true);
        jSlider2.setValue(200);
        jSlider2.setName("puzzlepiece-number-slider"); // NOI18N
        jPanel11.add(jSlider2);

        jLabel2.setText("Number of puzzlepieces:");
        jPanel7.add(jLabel2);

        jTextField1.setColumns(5);
        jTextField1.setText("200");
        jTextField1.setName("puzzlepiece-number-textfield"); // NOI18N
        jPanel7.add(jTextField1);

        jPanel11.add(jPanel7);

        jLabel3.setText("The number of puzzlepieces is an approximate value. It will be tried to divide the puzzle in the given number of pieces. Is this not possible, a number very near to this number will be choosen.");
        jPanel11.add(jLabel3);

        jLabel5.setText("Changing this value is only visible when starting a new puzzle.");
        jPanel11.add(jLabel5);

        jPanel3.add(jPanel11);

        jLabel6.setText("The distance that two puzzlepieces must have in order to let the puzzlepieces snap together.");
        jPanel12.add(jLabel6);

        jSlider3.setMajorTickSpacing(5);
        jSlider3.setMinimum(5);
        jSlider3.setPaintLabels(true);
        jSlider3.setPaintTicks(true);
        jSlider3.setSnapToTicks(true);
        jSlider3.setValue(20);
        jSlider3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider3StateChanged(evt);
            }
        });
        jPanel12.add(jSlider3);

        jPanel3.add(jPanel12);

        jRadioButton1.setText("<html>All connections have the same shape.<br/>The following shape will be used for the connections:</html>");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel15.add(jRadioButton1);

        jScrollPaneShapeAppearance.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPaneShapeAppearance.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jPanel15.add(jScrollPaneShapeAppearance);

        jRadioButton2.setText("They are choosen randomly from the available shapes.");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jPanel15.add(jRadioButton2);

        jPanel3.add(jPanel15);

        jScrollPane2.setViewportView(jPanel3);

        jTabbedPane1.addTab("Puzzle", jScrollPane2);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName("");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Speichern");
        jButton1.setName("settings-save"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 20);
        jPanel1.add(jButton1, gridBagConstraints);

        jButton2.setText("Abbrechen");
        jButton2.setName("settings-cancel"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel1.add(jButton2, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        this.changeBackgroundColor();
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            SettingsController.getInstance().saveSettingsToFile();

            this.dispose();
        } catch (IOException ex) {
            new ErrorMessageDialog(SettingsController.getInstance().getLanguageText(10, 85),
                    SettingsController.getInstance().getLanguageText(10, 86),
                    ex.getMessage()).showDialog(this);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            SettingsController.getInstance().loadSettingsFromFile();

            this.dispose();
        } catch (IOException ex) {
            new ErrorMessageDialog(SettingsController.getInstance().getLanguageText(10, 87),
                    SettingsController.getInstance().getLanguageText(10, 88),
                    ex.getMessage()).showDialog(this);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            SettingsController.getInstance().loadSettingsFromFile();
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_formWindowClosed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        SettingsController.getInstance().setDecreasePuzzleAutomatically(jCheckBox4.isSelected());
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        SettingsController.getInstance().setUsedSizeOfPuzzlearea(jSlider1.getValue() / (double) 100);
    }//GEN-LAST:event_jSlider1StateChanged

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        SettingsController.getInstance().setEnlargePuzzleAutomatically(jCheckBox3.isSelected());
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        SettingsController.getInstance().setShowPuzzlePreview(jCheckBox1.isSelected());
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        SettingsController.getInstance().setPlaySounds(jCheckBox5.isSelected());
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jSlider3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider3StateChanged
        SettingsController.getInstance().setPuzzlepieceSnapDistancePercent(jSlider3.getValue());
    }//GEN-LAST:event_jSlider3StateChanged

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        SettingsController.getInstance().setUseRandomConnectorShape(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        SettingsController.getInstance().setUseRandomConnectorShape(true);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneFullscreenMonitors;
    private javax.swing.JScrollPane jScrollPaneShapeAppearance;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JSlider jSlider3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
