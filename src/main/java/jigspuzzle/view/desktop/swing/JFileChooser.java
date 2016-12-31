package jigspuzzle.view.desktop.swing;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.filechooser.FileSystemView;

/**
 * A class for a nicer JFileChooser that also remembers the directory that was
 * choosen the last time to display this initially.
 *
 * @author RoseTec
 */
public class JFileChooser extends javax.swing.JFileChooser {

    /**
     * The name of that file, that contains the directoy in that the last
     * choosen file was.
     *
     * This is used to set the initial directory to.
     */
    private static final String SAVE_FILENAME_FOR_LAST_BROWSE_DIRECTORY = "filechooser";

    public JFileChooser() {
        init();
    }

    public JFileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);
        init();
    }

    public JFileChooser(File currentDirectory) {
        super(currentDirectory);
        init();
    }

    public JFileChooser(FileSystemView fsv) {
        super(fsv);
        init();
    }

    public JFileChooser(File currentDirectory, FileSystemView fsv) {
        super(currentDirectory, fsv);
        init();
    }

    public JFileChooser(String currentDirectoryPath, FileSystemView fsv) {
        super(currentDirectoryPath, fsv);
        init();
    }

    private void init() {
        loadCurrentDirectoy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int showDialog(Component parent, String approveButtonText) throws HeadlessException {
        int result = super.showDialog(parent, approveButtonText);

        if (result == JFileChooser.APPROVE_OPTION) {
            this.saveCurrentDirectoy();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int showSaveDialog(Component parent) throws HeadlessException {
        int result = super.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            this.saveCurrentDirectoy();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int showOpenDialog(Component parent) throws HeadlessException {
        int result = super.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            this.saveCurrentDirectoy();
        }
        return result;
    }

    /**
     * Loades the current directoy of this filechooser. This way, it is possible
     * to display the folder, in that the last time something was selected.
     *
     * @see #saveCurrentDirectoy()
     */
    private void loadCurrentDirectoy() {
        File file = new File(SAVE_FILENAME_FOR_LAST_BROWSE_DIRECTORY);

        if (file.exists()) {
            Scanner s = null;

            try {
                File oldDir;

                s = new Scanner(file);
                oldDir = new File(s.next());
                if (oldDir.isDirectory()) {
                    this.setCurrentDirectory(oldDir);
                }
            } catch (FileNotFoundException ex) {
            } finally {
                if (s != null) {
                    s.close();
                }
            }
        }
    }

    /**
     * Saves the current directoy of this filechooser. This way, it is possible
     * to load it the next time, when a new filecooser is created.
     *
     * @see #loadCurrentDirectoy()
     */
    private void saveCurrentDirectoy() {
        PrintWriter writer = null;
        try {
            File file = new File(SAVE_FILENAME_FOR_LAST_BROWSE_DIRECTORY);

            writer = new PrintWriter(file);
            writer.print(this.getCurrentDirectory().getAbsolutePath());
        } catch (FileNotFoundException ex) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
