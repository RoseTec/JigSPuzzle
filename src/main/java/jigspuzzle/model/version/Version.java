package jigspuzzle.model.version;

/**
 * This class represents a version of this program.
 *
 * @author RoseTec
 */
public class Version {

    /**
     * The String in the version string that signals, that this a snapshot.
     */
    static final String SNAPSHOT_TAG = "-SNAPSHOT";

    /**
     * The String in the version string that stands for "version".
     */
    static final String VERSION_TAG = "v";

    private String versionString;

    private boolean isSnapshotVersion;

    public Version(String versionString) {
        if (!isVersionStringValid(versionString)) {
            throw new IllegalArgumentException("Given version-string is not a valid version");
        }
        this.setVersion(versionString);
    }

    public String getVersionString() {
        return isSnapshotVersion ? versionString + SNAPSHOT_TAG : versionString;
    }

    /**
     * Checks, wheather the given version is older then this version.
     *
     * @param otherVersion
     * @return
     */
    public boolean isOlderThan(Version otherVersion) {
        String[] thisParts = this.versionString.split("\\.");
        String[] otherParts = otherVersion.versionString.split("\\.");
        int i;

        for (i = 0; i < thisParts.length && i < otherParts.length; i++) {
            if (Integer.parseInt(thisParts[i]) < Integer.parseInt(otherParts[i])) {
                return true;
            } else if (Integer.parseInt(thisParts[i]) > Integer.parseInt(otherParts[i])) {
                return false;
            }
        }

        if (thisParts.length == otherParts.length) {
            if (this.isSnapshotVersion == otherVersion.isSnapshotVersion) {
                return false;
            } else if (otherVersion.isSnapshotVersion) {
                return false;
            } else {
                return true;
            }
        } else if (thisParts.length > otherParts.length) {
            return false;
        } else {//if (thisParts.length < otherParts.length) {
            for (int i2 = i; i2 < otherParts.length; i2++) {
                if (Integer.parseInt(otherParts[i2]) > 0) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean setVersion(String version) {
        // check version
        if (isVersionStringValid(version)) {
            this.versionString = version.startsWith(VERSION_TAG) ? version.substring(1) : version;
            this.isSnapshotVersion = false;

            if (this.versionString.endsWith(SNAPSHOT_TAG)) {
                this.versionString = this.versionString.substring(0, this.versionString.length() - SNAPSHOT_TAG.length());
                this.isSnapshotVersion = true;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the given version is a correct String-representation of a
     * version.
     *
     * @param version
     * @return
     */
    private boolean isVersionStringValid(String version) {
        String versionString;

        if (version.startsWith(VERSION_TAG)) {
            versionString = version.substring(1);
        } else {
            versionString = version;
        }
        if (versionString.endsWith(SNAPSHOT_TAG)) {
            versionString = versionString.substring(0, versionString.length() - SNAPSHOT_TAG.length());
        }

        // Check for format d(.d)*
        String[] subVersions = versionString.split("\\.");

        if (subVersions.length == 0) {
            return false;
        }
        for (String subersion : subVersions) {
            if (!isInt(subersion)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given String is a number.
     *
     * @param string
     * @return
     */
    private boolean isInt(String string) {
        //TODO: is there no pre-defined method for this?
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ex) {
            // yes, I know this is a bad solution... But it's easy...
            return false;
        }
    }

}
