package jigspuzzle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A class for managing access to the recources of JigSPuzzle. All access to
 * resources should be reasised by this class to provide a central place to work
 * with resources.
 *
 * @author RoseTec
 */
public class JigSPuzzleResources {

    /**
     * Gets the resourse in the given path
     *
     * @param resourcePath The path for the resource. It should start width a
     * <code>/</code>
     * @return
     */
    public static URL getResource(String resourcePath) {
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }

        URL ret = JigSPuzzleResources.class.getResource(resourcePath);
        if (ret != null) {
            return ret;
        }

        // seach also for files
        File file = new File(resourcePath.substring(1));
        if (file.exists()) {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException ex) {
            }
        }
        return null;
    }

    /**
     * Gets a stream of the the resourse in the given path.
     *
     * @param resourcePath The path for the resource. It should start width a
     * <code>/</code>
     * @return
     */
    public static InputStream getResourceAsStream(String resourcePath) {
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }

        InputStream ret = JigSPuzzleResources.class.getResourceAsStream(resourcePath);
        if (ret != null) {
            return ret;
        }

        // seach also for files
        File file = new File(resourcePath.substring(1));
        if (file.exists()) {
            try {
                return file.toURI().toURL().openStream();
            } catch (MalformedURLException ex) {
            } catch (IOException ex) {
            }
        }
        return null;
    }

    /**
     * Gets a list of all files in the given path. It does not matter, if it is
     * cntained in a jar-file or not.
     *
     * @param path
     * @return
     */
    public static List<String> getResourcesInPath(String path) {
        try {
            return Arrays.asList(getResourceListing(JigSPuzzle.class, path));
        } catch (URISyntaxException | IOException ex) {
            return new ArrayList<>();
        }
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
