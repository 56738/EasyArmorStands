package me.m56738.easyarmorstands.config.version.game;

import me.m56738.easyarmorstands.config.version.Version;
import me.m56738.easyarmorstands.config.version.game.v1_13.ItemStackTransformAction_v1_13;
import org.bukkit.Bukkit;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;
import org.spongepowered.configurate.transformation.TransformAction;

import java.util.ArrayList;
import java.util.List;

import static org.spongepowered.configurate.NodePath.path;

public class GameVersionTransformation implements ConfigurationTransformation {
    private static final Version MAX_TARGET_VERSION = new Version(1, 21, 3);

    private final List<Entry> entries;
    private final Version targetVersion = getTargetVersion();

    private GameVersionTransformation(List<Entry> entries) {
        this.entries = entries;
    }

    public static GameVersionTransformation config() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(new Version(1, 13, 0), ConfigurationTransformation.builder()
                .addAction(path("editor", "menu", "background", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .build()));
        return new GameVersionTransformation(entries);
    }

    public static GameVersionTransformation properties() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(new Version(1, 13, 0), ConfigurationTransformation.builder()
                .addAction(path("easyarmorstands:armor_stand/base_plate", "button"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("easyarmorstands:armor_stand/lock", "button"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("easyarmorstands:armor_stand/marker", "button"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("easyarmorstands:armor_stand/size", "button"),
                        new ItemStackTransformAction_v1_13())
                .build()));
        return new GameVersionTransformation(entries);
    }

    public static GameVersionTransformation menu() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(new Version(1, 9, 0), ConfigurationTransformation.builder()
                .addAction(path("slots", "easyarmorstands:entity/equipment/off_hand", "enabled"),
                        TransformAction.remove())
                .build()));
        entries.add(new Entry(new Version(1, 12, 0), ConfigurationTransformation.builder()
                .addAction(path("slots", "easyarmorstands:color_picker/red/decrement", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .addAction(path("slots", "easyarmorstands:color_picker/red", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .addAction(path("slots", "easyarmorstands:color_picker/red/increment", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .addAction(path("slots", "easyarmorstands:color_picker/green/decrement", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .addAction(path("slots", "easyarmorstands:color_picker/green", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .addAction(path("slots", "easyarmorstands:color_picker/green/increment", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .addAction(path("slots", "easyarmorstands:color_picker/blue/decrement", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .addAction(path("slots", "easyarmorstands:color_picker/blue", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .addAction(path("slots", "easyarmorstands:color_picker/blue/increment", "config", "item", "type"),
                        ReplaceTransformAction.replaceString("wool", "concrete"))
                .build()));
        entries.add(new Entry(new Version(1, 13, 0), ConfigurationTransformation.builder()
                .addAction(path("slots", "easyarmorstands:color_picker/red/decrement", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/red", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/red/increment", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/green/decrement", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/green", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/green/increment", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/blue/decrement", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/blue", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/blue/increment", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/white", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/orange", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/magenta", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/light_blue", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/yellow", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/lime", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/pink", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/gray", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/silver", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/cyan", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/purple", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/blue", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/brown", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/green", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/red", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:color_picker/preset/black", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:armor_stand/part/head", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .addAction(path("slots", "easyarmorstands:headdatabase", "config", "item"),
                        new ItemStackTransformAction_v1_13())
                .build()));
        return new GameVersionTransformation(entries);
    }

    @Override
    public void apply(ConfigurationNode node) throws ConfigurateException {
        ConfigurationNode gameVersionNode = node.node("_version", "game");
        String gameVersionRaw = gameVersionNode.getString();
        if (gameVersionRaw == null) {
            // assume latest
            gameVersionNode.raw(targetVersion.toString());
            return;
        }

        Version gameVersion = Version.parse(gameVersionRaw);
        if (gameVersion.compareTo(targetVersion) >= 0) {
            // don't update config generated by a future version
            return;
        }

        for (Entry entry : entries) {
            if (entry.getVersion().compareTo(targetVersion) > 0) {
                // don't update config to a future version
                continue;
            }

            if (entry.getVersion().compareTo(gameVersion) > 0) {
                entry.transformation.apply(node);
            }
        }

        gameVersionNode.raw(targetVersion.toString());
    }

    private static class Entry {
        private final Version version;
        private final ConfigurationTransformation transformation;

        private Entry(Version version, ConfigurationTransformation transformation) {
            this.version = version;
            this.transformation = transformation;
        }

        public Version getVersion() {
            return version;
        }
    }

    private static Version getTargetVersion() {
        Version serverVersion = getServerVersion();
        if (serverVersion.compareTo(MAX_TARGET_VERSION) > 0) {
            return MAX_TARGET_VERSION;
        } else {
            return serverVersion;
        }
    }

    private static Version getServerVersion() {
        String bukkitVersion = Bukkit.getBukkitVersion();
        String version = bukkitVersion.substring(0, bukkitVersion.indexOf('-'));
        return Version.parse(version);
    }
}
