package jigspuzzle.model.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jigspuzzle.JigSPuzzleResources;
import jigspuzzle.model.Savable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * A controller for all kinds of handeling queries for the current language of
 * the program.
 *
 * @author RoseTec
 * @see #getText(int, int)
 */
public class LanguageSettings extends Observable implements Savable {

    /**
     * The name of the directory, that contains all language files.
     */
    private static final String LANGUAGE_DIR_NAME = "/languages/";

    /**
     * The default language, when the current language has no results for one
     * queried text.
     */
    private final String DEFAULT_LANGUAGE = "english";

    /**
     * The current language.
     */
    private String currentLanguage = "english";

    /**
     * A list where all loaded languages are saved
     */
    private HashMap<String, Language> languagesLoaded = new HashMap<>();

    public LanguageSettings() {
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromFile(Element settingsNode) throws IOException {
        Node thisSettingsNode = settingsNode.getElementsByTagName("language").item(0);
        NodeList list;

        if (thisSettingsNode == null) {
            return;
        }
        list = thisSettingsNode.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            switch (node.getNodeName()) {
                case "current-language":
                    setCurrentLanguage(node.getTextContent());
            }
        }

        setChanged();
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveToFile(Document doc, Element rootElement) throws IOException {
        Element settingsElement = doc.createElement("language");
        Element tmpElement;

        rootElement.appendChild(settingsElement);

        tmpElement = doc.createElement("current-language");
        tmpElement.setTextContent(currentLanguage);
        settingsElement.appendChild(tmpElement);
    }

    /**
     * Gets all available languages.
     *
     * @return
     */
    public String[] getAvailableLanguages() {
        List<String> files = JigSPuzzleResources.getResourcesInPath(LANGUAGE_DIR_NAME);
        List<String> languages = new ArrayList<>();

        for (String file : files) {
            if (file.endsWith(".xml")) {
                String name;

                name = file.substring(0, file.length() - ".xml".length());
                languages.add(name);
            }
        }

        String[] ret = new String[languages.size()];
        for (int i = 0; i < languages.size(); i++) {
            ret[i] = languages.get(i);
        }

        return ret;
    }

    /**
     * Gets the current language.
     *
     * @return
     */
    public String getCurrentLanguage() {
        return currentLanguage;
    }

    /**
     * Sets the new current language. If the given language is not available,
     * nothing happens.
     *
     * @param newLanguage
     */
    public void setCurrentLanguage(String newLanguage) {
        if (currentLanguage == null ? newLanguage != null : !currentLanguage.equals(newLanguage)) {
            currentLanguage = newLanguage;
            Locale newLocale = Locale.forLanguageTag(getLanguage(currentLanguage).getTag());

            Locale.setDefault(newLocale);

            setChanged();
            notifyObservers();
        }
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
     * @param variableMapping A mapping for variables used in the text for the
     * language. It map the variable to a given value.
     *
     * Variables are used in the text as follows:
     * <code>text {{variable}} text</code>.
     * @return
     */
    public String getText(int pageId, int textId, Map<String, String> variableMapping) {
        String text;

        try {
            text = getLanguage(currentLanguage).getText(pageId, textId);
            if (text != null) {
                text = replaceVariablesInText(text, variableMapping);
                return text;
            }
        } catch (NullPointerException ex) {
        }

        // nothing found in current language => search in english language
        try {
            text = getLanguage(DEFAULT_LANGUAGE).getText(pageId, textId);
            if (text != null) {
                text = replaceVariablesInText(text, variableMapping);
                return text;
            }
        } catch (NullPointerException ex) {
        }

        // also nothing found in english language...
        return "readText-" + pageId + "-" + textId;
    }

    /**
     * Gets the Language-object of the given string.
     *
     * If no language is loaded up to now, it loads the language and reads the
     * texts of the given language.
     *
     * @param languageName
     * @return
     */
    private Language getLanguage(String languageName) {
        // it is already loaded?
        if (languagesLoaded.containsKey(languageName)) {
            return languagesLoaded.get(languageName);
        }
        // not loaded => load it now

        // read file
        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;
        Document doc;
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();

            dBuilder.setEntityResolver((String publicId, String systemId) -> {
                if (systemId.contains("language.dtd")) {
                    InputStream dtdStream = JigSPuzzleResources.getResource(LANGUAGE_DIR_NAME + "language.dtd").openStream();
                    return new InputSource(dtdStream);
                } else {
                    return null;
                }
            });
            doc = dBuilder.parse(getLanguageStream(languageName));
            doc.getDocumentElement().normalize();
        } catch (Exception ex) {
            return null;
        }

        // extract texts
        Node languageNode = doc.getElementsByTagName("language").item(0);
        NodeList pages = languageNode.getChildNodes();
        Language language;
        HashMap<Integer, HashMap<Integer, String>> texts = new HashMap<>();

        language = new Language();
        language.setTag(languageNode.getAttributes().getNamedItem("tag").getTextContent());

        for (int i1 = 0; i1 < pages.getLength(); i1++) {
            Node nodePage = pages.item(i1);
            if ("page".equals(nodePage.getNodeName())) {
                NodeList ts = nodePage.getChildNodes();

                int pageId;
                HashMap<Integer, String> pageMap = new HashMap<>();

                try {
                    pageId = Integer.parseInt(nodePage.getAttributes().getNamedItem("id").getTextContent());
                } catch (NumberFormatException | NullPointerException ex) {
                    continue;
                }
                for (int i2 = 0; i2 < ts.getLength(); i2++) {
                    Node nodeT = ts.item(i2);
                    if ("t".equals(nodeT.getNodeName())) {
                        int tId;

                        try {
                            tId = Integer.parseInt(nodeT.getAttributes().getNamedItem("id").getTextContent());
                        } catch (NumberFormatException | NullPointerException ex) {
                            continue;
                        }
                        pageMap.put(tId, nodeT.getTextContent());
                    }
                }
                texts.put(pageId, pageMap);
            }
        }
        language.setTexts(texts);

        languagesLoaded.put(languageName, language);
        return language;
    }

    /**
     * Returns the stream to to that file, that contains all language texts of
     * the given language.
     *
     * @return
     */
    private InputStream getLanguageStream(String language) {
        try {
            return JigSPuzzleResources.getResource(LANGUAGE_DIR_NAME + language + ".xml").openStream();
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Replaces the variables in the text with the value from the map. If the
     * map does not contain the variable, then nothing happens and the variable
     * in the string stays as it is. Means: <code>text {{variable}} text</code>
     * will not be changed.
     *
     * @param text
     * @param variableMapping
     * @return
     */
    private String replaceVariablesInText(String text, Map<String, String> variableMapping) {
        if (variableMapping == null) {
            return text;
        } else {
            for (String variable : variableMapping.keySet()) {
                text = text.replaceAll("\\{\\{" + variable + "\\}\\}", variableMapping.get(variable));
            }
            return text;
        }
    }

    /**
     * This class represents a language, that can be used in this program. It
     * consists of several texts, tht can be usen as texts in the ui.
     *
     * A language is given by the name. The same name is used as the
     * <code>tag</code> in cass <code>Locale</code>.
     *
     * @author RoseTec
     * @see Locale#forLanguageTag(java.lang.String)
     */
    private class Language {

        /**
         * the tag of the this language.
         *
         * @see Locale#forLanguageTag(java.lang.String)
         */
        private String tag = null;

        /**
         * Here the texts of the current language are saved.
         */
        private HashMap<Integer, HashMap<Integer, String>> texts;

        public String getTag() {
            return tag;
        }

        /**
         * Gets the text in the given ids.
         *
         * @param pageId
         * @param textId
         * @return <code>null</code>, if no text is found.
         */
        public String getText(int pageId, int textId) {
            String text;

            try {
                text = texts.get(pageId).get(textId);
                if (text != null) {
                    return text;
                }
            } catch (NullPointerException ex) {
            }

            return null;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public void setTexts(HashMap<Integer, HashMap<Integer, String>> texts) {
            this.texts = texts;
        }

    }

}
