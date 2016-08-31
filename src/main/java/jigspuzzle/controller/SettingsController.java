package jigspuzzle.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jigspuzzle.model.settings.LanguageSettings;
import jigspuzzle.model.settings.PuzzleSettings;
import jigspuzzle.model.settings.PuzzleareaSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * A class for handeling all actions for settings.
 *
 * @author RoseTec
 */
public class SettingsController extends AbstractController {

    private static SettingsController instance;

    public static SettingsController getInstance() {
        if (instance == null) {
            instance = new SettingsController();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetInstance() {
        instance = null;
    }

    /**
     * The settings for the current language.
     */
    private LanguageSettings languageSettings;

    /**
     * The settings for the puzzle itself.
     */
    private PuzzleSettings puzzleSettings;

    /**
     * The settings for the puzzlearea.
     */
    private PuzzleareaSettings puzzleareaSettings;

    /**
     * The filename for storing the settings in a file.
     */
    public final static String SETTINGS_FILE_NAME = "settings.xml";

    private SettingsController() {
        puzzleSettings = new PuzzleSettings();
        puzzleareaSettings = new PuzzleareaSettings();
        languageSettings = new LanguageSettings();

        try {
            loadSettingsFromFile();
        } catch (IOException ex) {
        }
    }

    /**
     * Loads the stored settings from the settings-file. The file was previously
     * created, when saving the settings.
     *
     * If the file is not availible, an IOException is thrown.
     *
     * @throws java.io.IOException
     */
    public void loadSettingsFromFile() throws IOException {
        File file = new File(SETTINGS_FILE_NAME);

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            Node settingsNode = doc.getElementsByTagName("settings").item(0);
            if (settingsNode != null) {
                languageSettings.loadFromFile((Element) settingsNode);
                puzzleSettings.loadFromFile((Element) settingsNode);
                puzzleareaSettings.loadFromFile((Element) settingsNode);
            }
        } catch (SAXException | ParserConfigurationException ex) {
        }
    }

    /**
     * Saves the current settings into the settings-file.
     *
     * If the something wents wrong while saving, an IOException is thrown.
     *
     * @throws IOException
     */
    public void saveSettingsToFile() throws IOException {
        File file = new File(SETTINGS_FILE_NAME);

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            doc.appendChild(doc.createComment("This file is created, when saving the settings of JigSPuzzle."));
            Element root = doc.createElement("settings");
            doc.appendChild(root);
            languageSettings.saveToFile(doc, root);
            puzzleSettings.saveToFile(doc, root);
            puzzleareaSettings.saveToFile(doc, root);

            // write the content into xml file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
        } catch (TransformerConfigurationException ex) {
        } catch (TransformerException ex) {
        }
    }

    /**
     * Gets all available languages, that can be used for the user interface.
     *
     * @return
     */
    public String[] getAvailableLanguages() {
        return languageSettings.getAvailableLanguages();
    }

    /**
     * Gets the current language.
     *
     * @return
     */
    public String getCurrentLanguage() {
        return languageSettings.getCurrentLanguage();
    }

    /**
     * Sets the new current language. If the given language is not available,
     * nothing happens.
     *
     * @param newLanguage
     */
    public void setCurrentLanguage(String newLanguage) {
        languageSettings.setCurrentLanguage(newLanguage);
    }

    /**
     * Gets the value for automatically decrease the size of the puzzle, if it
     * does not fit into the puzzleare, which displays the puzzle.
     *
     * @return
     * @see #getPuzzlepieceSize(int, int)
     */
    public boolean getDecreasePuzzleAutomatically() {
        return puzzleareaSettings.getDecreasePuzzleAutomatically();
    }

    /**
     * Sets the value for automatically decrease the size of the puzzle, if it
     * does not fit into the puzzleare, which displays the puzzle.
     *
     * @param value
     * @see #getPuzzlepieceSize(int, int)
     */
    public void setDecreasePuzzleAutomatically(boolean value) {
        puzzleareaSettings.setDecreasePuzzleAutomatically(value);
    }

    /**
     * Gets the value for automatically enlarge the size of the puzzle, if it
     * does not fit into the puzzleare, which displays the puzzle.
     *
     * @return
     * @see #getPuzzlepieceSize(int, int)
     */
    public boolean getEnlargePuzzleAutomatically() {
        return puzzleareaSettings.getEnlargePuzzleAutomatically();
    }

    /**
     * Sets the value for automatically enlarge the size of the puzzle, if it
     * does not fit into the puzzleare, which displays the puzzle.
     *
     * @param value
     * @see #getPuzzlepieceSize(int, int)
     */
    public void setEnlargePuzzleAutomatically(boolean value) {
        puzzleareaSettings.setEnlargePuzzleAutomatically(value);
    }

    /**
     * Gets the text in the current language for the given pageId and textId.
     *
     * If no text in the current language is given in the file for the language,
     * then the english text is returned. If also the english text is not
     * availibe, then the text <code>readText-[pageId]-[textId]</code> is
     * returned.
     *
     * @param pageId
     * @param textId
     * @return
     */
    public String getLanguageText(int pageId, int textId) {
        return languageSettings.getText(pageId, textId);
    }

    /**
     * Gets the bckground color for the puzzlearea where the player playes
     * around with the puzzlepieces.
     *
     * @return
     */
    public Color getPuzzleareaBackgroundColor() {
        return puzzleareaSettings.getPuzzleareaBackgroundColor();
    }

    /**
     * Sets the bckground color for the puzzlearea where the player playes
     * around with the puzzlepieces.
     *
     * @param newColor
     */
    public void setPuzzleareaBackgroundColor(Color newColor) {
        puzzleareaSettings.setPuzzleareaBackgroundColor(newColor);
    }

    /**
     * Gets the numbr of puzzlepieces that a new puzzle should have.
     *
     * @return
     */
    public int getPuzzlepieceNumber() {
        return puzzleSettings.getPuzzlepieceNumber();
    }

    /**
     * Sets the numbr of puzzlepieces that a new puzzle should have.
     *
     * @param newNumber
     */
    public void setPuzzlepieceNumber(int newNumber) {
        puzzleSettings.setPuzzlepieceNumber(newNumber);
    }

    /**
     * Returns the height and width that one puzzlepiece should have, with the
     * given size of the puzzlearea. The settings for modifiing the size of a
     * puzzlepiece is considered in here.
     *
     * @param puzzleareaHeight
     * @param puzzleareaWidth
     * @return
     */
    public Dimension getPuzzlepieceSize(int puzzleareaHeight, int puzzleareaWidth) {
        return getPuzzlepieceSize(puzzleareaHeight,
                puzzleareaWidth,
                PuzzleController.getInstance().getPuzzleHeight(),
                PuzzleController.getInstance().getPuzzleWidth(),
                PuzzleController.getInstance().getPuzzlepieceRowCount(),
                PuzzleController.getInstance().getPuzzlepieceColumnCount());
    }

    /**
     * Returns the height and width that one puzzlepiece should have, with the
     * given size of the puzzlearea. The settings for modifiing the size of a
     * puzzlepiece is considered in here.
     *
     * @param puzzleareaHeight
     * @param puzzleareaWidth
     * @param puzzleHeight
     * @param puzzleWidth
     * @param puzzleRows
     * @param puzzleColumns
     * @return
     * @see #getPuzzlepieceSize(int, int)
     */
    Dimension getPuzzlepieceSize(int puzzleareaHeight, int puzzleareaWidth, double puzzleHeight, double puzzleWidth, int puzzleRows, int puzzleColumns) {
        // resize puzzlearea depending on setting for size of puzzlearea
        puzzleareaWidth *= puzzleareaSettings.getUsedSizeOfPuzzleare();
        puzzleareaHeight *= puzzleareaSettings.getUsedSizeOfPuzzleare();

        // Gets the dimension, that restricts the puzzlesie more
        boolean topRestricsMoreThanLeft;

        topRestricsMoreThanLeft = (puzzleHeight / puzzleareaHeight > puzzleWidth / puzzleareaWidth);

        // resize the puzzlepiece-size depending of the size of puzzlearea
        int resizedHeight = (int) (puzzleHeight);
        int resizedWidth = (int) (puzzleWidth);

        if (puzzleareaSettings.getDecreasePuzzleAutomatically()
                && (puzzleareaHeight < puzzleHeight || puzzleareaWidth < puzzleWidth)) {
            if (topRestricsMoreThanLeft && puzzleareaHeight < puzzleHeight) {
                resizedHeight = puzzleareaHeight;
                resizedWidth = (int) (resizedHeight * puzzleWidth / puzzleHeight);
            } else if (!topRestricsMoreThanLeft && puzzleareaWidth < puzzleWidth) {
                resizedWidth = puzzleareaWidth;
                resizedHeight = (int) (resizedWidth * puzzleHeight / puzzleWidth);
            }
        } else if (puzzleareaSettings.getEnlargePuzzleAutomatically()
                && (puzzleareaHeight > puzzleHeight || puzzleareaWidth > puzzleWidth)) {
            if (topRestricsMoreThanLeft && puzzleareaHeight > puzzleHeight) {
                resizedHeight = puzzleareaHeight;
                resizedWidth = (int) (resizedHeight * puzzleWidth / puzzleHeight);
            } else if (!topRestricsMoreThanLeft && puzzleareaWidth > puzzleWidth) {
                resizedWidth = puzzleareaWidth;
                resizedHeight = (int) (resizedWidth * puzzleHeight / puzzleWidth);
            }
        }

        return new Dimension(resizedWidth / puzzleColumns, resizedHeight / puzzleRows);
    }

    /**
     * Sets the number in percent, how much of the puzzleare should be used for
     * puzzeling. A number of 0.5 for instance means, that only 50% of the
     * puzzelarea should be used for the final puzzle.
     *
     * @param number
     * @see #getPuzzlepieceSize(int, int)
     */
    public void setUsedSizeOfPuzzleare(double number) {
        puzzleareaSettings.setUsedSizeOfPuzzleare(number);
    }

    /**
     * Gets the number in percent, how much of the puzzleare should be used for
     * puzzeling. A number of 0.5 for instance means, that only 50% of the
     * puzzelarea should be used for the final puzzle.
     *
     * @return
     * @see #getPuzzlepieceSize(int, int)
     */
    public double getUsedSizeOfPuzzleare() {
        return puzzleareaSettings.getUsedSizeOfPuzzleare();
    }

    /**
     * Adds an observer for the LanguageSettings
     *
     * @param o
     * @see Observable#addObserver(java.util.Observer)
     * @see PuzzleSettings
     */
    public synchronized void addLanguageSettingsObserver(Observer o) {
        languageSettings.addObserver(o);
    }

    /**
     * Delets an observer for the LanguageSettings
     *
     * @param o
     * @see Observable#deleteObserver(java.util.Observer)
     * @see PuzzleSettings
     */
    public synchronized void deleteLanguageSettingsObserver(Observer o) {
        languageSettings.deleteObserver(o);
    }

    /**
     * Adds an observer for the PuzzleSettings
     *
     * @param o
     * @see Observable#addObserver(java.util.Observer)
     * @see PuzzleSettings
     */
    public synchronized void addPuzzleSettingsObserver(Observer o) {
        puzzleSettings.addObserver(o);
    }

    /**
     * Delets an observer for the PuzzleSettings
     *
     * @param o
     * @see Observable#deleteObserver(java.util.Observer)
     * @see PuzzleSettings
     */
    public synchronized void deletePuzzleSettingsObserver(Observer o) {
        puzzleSettings.deleteObserver(o);
    }

    /**
     * Adds an observer for the PuzzleAreaSettings
     *
     * @param o
     * @see Observable#addObserver(java.util.Observer)
     * @see PuzzleareaSettings
     */
    public synchronized void addPuzzleareaSettingsObserver(Observer o) {
        puzzleareaSettings.addObserver(o);
    }

    /**
     * Delets an observer for the PuzzleAreaSettings
     *
     * @param o
     * @see Observable#deleteObserver(java.util.Observer)
     * @see PuzzleareaSettings
     */
    public synchronized void deletePuzzleareaSettingsObserver(Observer o) {
        puzzleareaSettings.deleteObserver(o);
    }

}
