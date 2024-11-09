package me.m56738.easyarmorstands.config.override;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class Version implements Comparable<Version> {
    private static final Comparator<Version> COMPARATOR =
            Comparator.comparingInt(Version::getMajor)
                    .thenComparingInt(Version::getMinor)
                    .thenComparingInt(Version::getPatch);

    private final int major;
    private final int minor;
    private final int patch;

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public static Version parse(String version) {
        String[] parts = version.split("\\D", 4);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid version: " + version);
        }

        int major = parseIntOrZero(parts[0]);
        int minor = parseIntOrZero(parts[1]);
        int patch;
        if (minor != 0 && parts.length >= 3) {
            patch = parseIntOrZero(parts[2]);
        } else {
            patch = 0;
        }
        return new Version(major, minor, patch);
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    @Override
    public int compareTo(@NotNull Version other) {
        return COMPARATOR.compare(this, other);
    }

    @Override
    public String toString() {
        if (patch != 0) {
            return major + "." + minor + "." + patch;
        } else {
            return major + "." + minor;
        }
    }

    private static int parseIntOrZero(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
