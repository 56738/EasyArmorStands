package me.m56738.easyarmorstands.config.version.override;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeforeMinorVersionCondition implements VersionOverrideCondition {
    private static final Pattern PATTERN = Pattern.compile("^1\\.(\\d+).*");
    private final int minor;

    public BeforeMinorVersionCondition(int minor) {
        this.minor = minor;
    }

    @Override
    public boolean testCondition() {
        Matcher matcher = PATTERN.matcher(Bukkit.getBukkitVersion());
        if (matcher.matches()) {
            int currentMinor = Integer.parseInt(matcher.group(1));
            return currentMinor < minor;
        }
        return false;
    }
}
