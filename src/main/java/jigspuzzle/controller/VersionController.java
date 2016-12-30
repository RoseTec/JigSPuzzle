package jigspuzzle.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Observable;
import java.util.Observer;
import jigspuzzle.JigSPuzzle;
import jigspuzzle.JigSPuzzleResources;
import jigspuzzle.model.version.Version;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.json.JSONObject;

/**
 * A controller for all kinds of buissniss with versions.
 *
 * It is possible to get the current version or query the newest version from
 * the web.
 *
 * @author RoseTec
 */
public class VersionController extends AbstractController {

    private static VersionController instance;

    public static VersionController getInstance() {
        if (instance == null) {
            instance = new VersionController();
        }
        return instance;
    }

    /**
     * The version that is currently available on the web. This is the newest
     * version available there.
     *
     * <b>Note:</b> It cannot be garanteed, that this variable a valid version.
     */
    private Version webVersion;

    /**
     * A Object that serves the purpose to be observed by the obervers, that
     * wait for a newer web-version.
     */
    private final WebVersionObservable webVersionObserverable;

    /**
     * The version of this instance.
     */
    private Version thisVersion;

    private VersionController() {
        webVersionObserverable = new WebVersionObservable();

        try {
            thisVersion = new Version(getCurrentVersionString());
            webVersion = new Version("0");
        } catch (IOException | IllegalArgumentException ex) {
            JigSPuzzle.getInstance().getPuzzleWindow().displayFatalError("Could not get the current version of the program.");
            JigSPuzzle.getInstance().exitProgram();
        }
    }

    /**
     * Adds a Observer that will be called, when a version is found on the
     * internet. The found version does not need to be a newer version than the
     * current one.
     *
     * For finding out, if the version on the internet in newer, use this
     * code:<br/>
     * <pre><code>
     * VersionController.getInstance().addNewVersionObserver((Observable o, Object arg) -> {
     *    Version newVersion = (Version) arg;
     *    if (VersionController.getInstance().getCurrentVersion().isOlderThan(newVersion)) {
     *       // the version on the internet is newer.
     *    }
     * });
     * </code></pre>
     *
     * @param o
     * @see #checkForNewVersion()
     */
    public void addNewVersionObserver(Observer o) {
        webVersionObserverable.addObserver(o);
    }

    /**
     * Checks, if a new version on the internet is avalable. For this a
     * connection to GitHub is established and the newest version will be
     * queried.
     *
     * This method will not terminate directly but wait for the html-request to
     * executed.
     *
     * For handeling a newer version, have a look at the method
     * <code>addNewVersionObserver()</code>.
     *
     * @throws java.io.IOException
     * @see #addNewVersionObserver(java.util.Observer)
     */
    public void checkForNewVersion() throws IOException {
        InputStream is = null;
        JSONObject newVersionJson;

        // create connection and read response from GitHub
        try {
            URL url = new URL("https://api.github.com/repos/RoseTec/JigSPuzzle/releases/latest");
            is = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();

            newVersionJson = new JSONObject(jsonText);
        } finally {
            is.close();
        }

        // make new version in this program instance available
        String newVersion = newVersionJson.getString("tag_name");

        webVersion = new Version(newVersion);
        webVersionObserverable.setChanged();
        webVersionObserverable.notifyObservers(webVersion);
    }

    /**
     * Gets the current version of this execution of the program. The current
     * version is derived from the pom file.
     *
     * @return
     */
    public Version getCurrentVersion() {
        return thisVersion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetInstance() {
        instance = null;
    }

    /**
     * Gets the current version from the pom file.
     *
     * @return
     * @throws IOException
     */
    private String getCurrentVersionString() throws IOException {
        // get pom-file
        URL url = JigSPuzzleResources.getResource("/META-INF/maven/RoseTec/JigSPuzzle/pom.xml");
        if (url == null) {
            // debug-mode
            url = JigSPuzzleResources.getResource("/../pom.xml");
        }

        // read pom-file
        File pomfile;
        Model model = null;
        FileReader reader = null;
        MavenXpp3Reader mavenreader = new MavenXpp3Reader();

        try {
            pomfile = new File(url.toURI());
            reader = new FileReader(pomfile); // <-- pomfile is your pom.xml
            model = mavenreader.read(reader);
            model.setPomFile(pomfile);
        } catch (URISyntaxException ex) {
            throw new IOException(ex);
        } catch (XmlPullParserException ex) {
            throw new IOException(ex);
        }

        MavenProject project = new MavenProject(model);
        return project.getVersion();
    }

    private class WebVersionObservable extends Observable {

        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }

    }

}
