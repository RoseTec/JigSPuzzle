package jigspuzzle;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import jigspuzzle.controller.*;
import jigspuzzle.view.IPuzzleWindow;
import jigspuzzle.view.PuzzleWindow;

/**
 * The main class to start JigSPuzzle
 *
 * @author RoseTec
 */
public class JigSPuzzle {

    private static JigSPuzzle instance;

    public static JigSPuzzle getInstance() {
        if (instance == null) {
            instance = new JigSPuzzle();
        }
        return instance;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        getInstance().startGame();
    }

    /**
     * Gets a list of all files in the given path. It does not matter, if it is
     * cntained in a jar-file or not.
     *
     * @param path
     * @return
     */
    public static List<String> getFilesInPath(String path) {
        try {
            return Arrays.asList(getResourceListing(JigSPuzzle.class, path));
        } catch (URISyntaxException | IOException ex) {
            return new ArrayList<>();
        }
    }

    /**
     * The main window, in which the user can play
     */
    IPuzzleWindow puzzleWindow;

    private JigSPuzzle() {
        puzzleWindow = new PuzzleWindow();
    }

    /**
     * Exits the complete program
     */
    public void exitProgram() {
        try {
            System.exit(0);
        } catch (Exception ex) {
            // in the assertJ - tests it is not possible to exit...
            // instead, we 'reset' this class and the controller.
            instance = null;
            PuzzleController.getInstance().resetInstance();
            SettingsController.getInstance().resetInstance();
        }
    }

    /**
     * Returns the window on that the user can puzzle.
     *
     * @return
     */
    public IPuzzleWindow getPuzzleWindow() {
        return puzzleWindow;
    }

    /**
     * Sets the window on that the user can puzzle.
     *
     * Use only, in special cases, e.g. tests.
     *
     * @param puzzleWindow
     */
    public void setPuzzleWindow(IPuzzleWindow puzzleWindow) {
        this.puzzleWindow = puzzleWindow;
    }

    /**
     * Starts the game and shows the user the UI.
     */
    private void startGame() {
        puzzleWindow.showPuzzleWindow();
    }

    /**
     * List directory contents for a resource folder. Not recursive. This is
     * basically a brute-force implementation. Works for regular files and also
     * JARs.
     *
     * Source:
     * http://stackoverflow.com/questions/6247144/how-to-load-a-folder-from-a-jar
     *
     * @author Greg Briggs
     * @param clazz Any java class that lives in the same place as the resources
     * you want.
     * @param path Should end with "/" and start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException
     * @throws IOException
     */
    private static String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            /* A file path: easy enough */
            return new File(dirURL.toURI()).list();
        }

        path = path.substring(1);
        if (dirURL == null) {
            /*
         * In case of a jar file, we can't actually find a directory.
         * Have to assume the same jar as clazz.
             */
            String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
            /* A JAR path */
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(path)) { //filter according to the path
                    String entry = name.substring(path.length());
                    int checkSubdir = entry.indexOf("/");
                    if (checkSubdir >= 0) {
                        // if it is a subdirectory, we just return the directory name
                        entry = entry.substring(0, checkSubdir);
                    }
                    result.add(entry);
                }
            }
            return result.toArray(new String[result.size()]);
        }

        throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }
}
