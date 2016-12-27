package jigspuzzle.view.desktop.puzzle;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Observable;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.controller.PuzzleController;
import jigspuzzle.controller.SettingsController;
import jigspuzzle.model.puzzle.Puzzle;
import jigspuzzle.model.puzzle.PuzzlepieceGroup;
import jigspuzzle.model.settings.PuzzleareaSettings;
import jigspuzzle.view.IPuzzleWindow;
import jigspuzzle.view.ImageGetter;
import jigspuzzle.view.desktop.DesktopPuzzleWindow;
import jigspuzzle.view.desktop.settings.SettingsWindow;
import jigspuzzle.view.desktop.swing.ErrorMessageDialog;
import jigspuzzle.view.desktop.swing.JMenu;
import jigspuzzle.view.desktop.swing.JMenuItem;

/**
 *
 * @author RoseTec
 */
public class DesktopPuzzleMainWindow extends javax.swing.JFrame implements IPuzzleWindow {

    /**
     * the area, where the user can play with puzzlepieces
     */
    private final Puzzlearea puzzlearea;

    /**
     * The class that contains all references to the windows for the desktop
     * version.
     */
    private final DesktopPuzzleWindow desktopPuzzleWindow;

    /**
     * The file to that the last time the puzzle was saved to. If this is set,
     * there will be no dialog to select the file to save to when saving.
     *
     * The file is set to <code>null</code> when a new puzzle is created.
     */
    private File lastSavedFile = null;

    /**
     * Creates new form PuzzleWindow
     */
    public DesktopPuzzleMainWindow(DesktopPuzzleWindow desktopPuzzleWindow) {
        this.desktopPuzzleWindow = desktopPuzzleWindow;
        initComponents();

        // create Puzzlearea
        puzzlearea = new Puzzlearea();

        jPanel1.add(puzzlearea);

        // load language texts
        SettingsController.getInstance().addLanguageSettingsObserver((Observable o, Object arg) -> {
            loadLanguageTexts();
        });
        loadLanguageTexts();

        // register observer for setting the background color in the main window for live preview
        SettingsController.getInstance().addPuzzleareaSettingsObserver((Observable o, Object arg) -> {
            PuzzleareaSettings settings = (PuzzleareaSettings) o;
            DesktopPuzzleMainWindow.this.jPanel1.setBackground(settings.getPuzzleareaBackgroundColor());
        });
        this.getContentPane().setBackground(SettingsController.getInstance().getPuzzleareaBackgroundColor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bringToFront(PuzzlepieceGroup puzzlepieceGroup) {
        puzzlearea.bringToFront(puzzlepieceGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPuzzlepieceHeight() {
        return puzzlearea.getPuzzlepieceHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPuzzlepieceWidth() {
        return puzzlearea.getPuzzlepieceWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPuzzleWindow() {
        this.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNewPuzzle(Puzzle puzzle) {
        lastSavedFile = null;
        Thread thread = new Thread(() -> {
            puzzlearea.setNewPuzzle(puzzle);
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Loads all texts from the settings in the current language.
     */
    private void loadLanguageTexts() {
        this.setTitle(SettingsController.getInstance().getLanguageText(1, 1));
        jMenu1.setText(SettingsController.getInstance().getLanguageText(1, 100));
        jMenuItem1.setText(SettingsController.getInstance().getLanguageText(1, 110));
        jMenuItem2.setText(SettingsController.getInstance().getLanguageText(1, 120));
        jMenuItem3.setText(SettingsController.getInstance().getLanguageText(1, 130));
        jMenuItem4.setText(SettingsController.getInstance().getLanguageText(1, 150));
        jMenuItem5.setText(SettingsController.getInstance().getLanguageText(1, 160));
        jMenuItem6.setText(SettingsController.getInstance().getLanguageText(1, 180));

        jMenu2.setText(SettingsController.getInstance().getLanguageText(1, 300));
        jMenuItem8.setText(SettingsController.getInstance().getLanguageText(1, 310));
        jMenuItem7.setText(SettingsController.getInstance().getLanguageText(1, 320));

        jMenu3.setText(SettingsController.getInstance().getLanguageText(1, 500));
        jMenuItem9.setText(SettingsController.getInstance().getLanguageText(1, 510));
        jMenuItem10.setText(SettingsController.getInstance().getLanguageText(1, 520));

        // also set the default locale of the swing components
        JComponent.setDefaultLocale(Locale.getDefault());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        jMenuItem3 = new JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new JMenuItem();
        jMenuItem5 = new JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new JMenuItem();
        jMenu2 = new JMenu();
        jMenuItem8 = new JMenuItem();
        jMenuItem7 = new JMenuItem();
        jMenu3 = new JMenu();
        jMenuItem9 = new JMenuItem();
        jMenuItem10 = new JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JigSPuzzle");
        setIconImage(ImageGetter.getInstance().getJigSPuzzleImage());
        setPreferredSize(new java.awt.Dimension(1200, 800));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setName("puzzlearea-panel"); // NOI18N
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(jPanel1);

        jMenuBar1.setName("main-menu"); // NOI18N

        jMenu1.setText("Puzzle");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Neues Puzzle Erstellen");
        jMenuItem1.setName("puzzle-create"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Altes Puzzle Laden");
        jMenuItem2.setName("puzzle-load"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Dieses Puzzle Speichern");
        jMenuItem3.setName("puzzle-save"); // NOI18N
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator1);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Puzzle Durchmischen");
        jMenuItem4.setName("puzzle-shuffle"); // NOI18N
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Puzzle Neu Starten");
        jMenuItem5.setName("puzzle-restart"); // NOI18N
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);
        jMenu1.add(jSeparator2);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("JigSPuzzle Beenden");
        jMenuItem6.setName("exit"); // NOI18N
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Einstellungen");
        jMenu2.setName("settings"); // NOI18N

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Darstellungs-Einstellungen");
        jMenuItem8.setName("appearance-settings"); // NOI18N
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Puzzle-Optionen");
        jMenuItem7.setContentAreaFilled(false);
        jMenuItem7.setName("puzzle-settings"); // NOI18N
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Über");

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem9.setText("Über JigSPuzzle");
        jMenuItem9.setEnabled(false);
        jMenu3.add(jMenuItem9);

        jMenuItem10.setText("Auf Neue Version Prüfen");
        jMenuItem10.setEnabled(false);
        jMenu3.add(jMenuItem10);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        JigSPuzzle.getInstance().exitProgram();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        this.desktopPuzzleWindow.showPuzzleSettings();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        this.desktopPuzzleWindow.showAppearanceSettings();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // show window for selecting a picture
        JFileChooser fileChooser = new JFileChooser();
        File selectedFile;

        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            // user canceled
            return;
        }
        selectedFile = fileChooser.getSelectedFile();

        // make a puzzle out of the file
        new Thread(() -> {
            try {
                PuzzleController.getInstance().newPuzzle(selectedFile, puzzlearea.getHeight(), puzzlearea.getWidth());
            } catch (IOException ex) {
                new ErrorMessageDialog(SettingsController.getInstance().getLanguageText(1, 55),
                        SettingsController.getInstance().getLanguageText(1, 56),
                        ex.getMessage()).showDialog(this);
            }
        }).start();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Dimension puzzleDim = SettingsController.getInstance().getPuzzlepieceSize(puzzlearea.getHeight(), puzzlearea.getWidth());
        int pieceWidth = puzzleDim.width;
        int pieceHeight = puzzleDim.height;

        new Thread(() -> {
            PuzzleController.getInstance().shufflePuzzlepieces(puzzlearea.getWidth() - pieceWidth, puzzlearea.getHeight() - pieceHeight);
        }).start();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // show window for selecting
        JFileChooser fileChooser = new JFileChooser();
        File selectedFile;

        if (lastSavedFile == null) {
            if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                // user canceled
                return;
            }
            selectedFile = fileChooser.getSelectedFile();
            lastSavedFile = selectedFile;
        } else {
            selectedFile = lastSavedFile;
        }

        try {
            PuzzleController.getInstance().savePuzzle(selectedFile);
        } catch (IOException ex) {
            new ErrorMessageDialog(SettingsController.getInstance().getLanguageText(1, 51),
                    SettingsController.getInstance().getLanguageText(1, 52),
                    ex.getMessage()).showDialog(this);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // show window for selecting
        JFileChooser fileChooser = new JFileChooser();
        File selectedFile;

        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            // user canceled
            return;
        }
        selectedFile = fileChooser.getSelectedFile();

        try {
            PuzzleController.getInstance().loadPuzzle(selectedFile);
        } catch (IOException ex) {
            new ErrorMessageDialog(SettingsController.getInstance().getLanguageText(1, 53),
                    SettingsController.getInstance().getLanguageText(1, 54),
                    ex.getMessage()).showDialog(this);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        new Thread(() -> {
            try {
                PuzzleController.getInstance().restartPuzzle(puzzlearea.getHeight(), puzzlearea.getWidth());
            } catch (IOException ex) {
                new ErrorMessageDialog(SettingsController.getInstance().getLanguageText(1, 57),
                        SettingsController.getInstance().getLanguageText(1, 58),
                        ex.getMessage()).showDialog(this);
            }
        }).start();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables

}