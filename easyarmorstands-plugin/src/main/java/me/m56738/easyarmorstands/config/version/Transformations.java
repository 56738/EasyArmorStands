package me.m56738.easyarmorstands.config.version;

import me.m56738.easyarmorstands.config.MessageConfig;
import me.m56738.easyarmorstands.config.version.game.GameVersionTransformation;
import me.m56738.easyarmorstands.config.version.game.ReplaceTransformAction;
import me.m56738.easyarmorstands.config.version.game.v1_13.ItemStackTransformAction_v1_13;
import org.spongepowered.configurate.NodePath;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;
import org.spongepowered.configurate.transformation.TransformAction;

import static org.spongepowered.configurate.NodePath.path;

public class Transformations {
    public static ConfigurationTransformation.Versioned config() {
        return ConfigurationTransformation.versionedBuilder()
                .versionKey("_version", "config")
                .addVersion(0, initialTransform())
                .build();
    }

    public static ConfigurationTransformation.Versioned properties() {
        return ConfigurationTransformation.versionedBuilder()
                .versionKey("_version", "config")
                .addVersion(0, ConfigurationTransformation.builder()
                        .addAction(path("easyarmorstands:armor_stand/base_plate", "button", "data"),
                                ReplaceTransformAction.replace(int.class, -1, null))
                        .addAction(path("easyarmorstands:armor_stand/lock", "button", "data"),
                                ReplaceTransformAction.replace(int.class, -1, null))
                        .addAction(path("easyarmorstands:armor_stand/marker", "button", "data"),
                                ReplaceTransformAction.replace(int.class, -1, null))
                        .addAction(path("easyarmorstands:armor_stand/size", "button", "data"),
                                ReplaceTransformAction.replace(int.class, -1, null))
                        .build())
                .build();
    }

    public static ConfigurationTransformation initialTransform() {
        return ConfigurationTransformation.builder()
                .addAction(NodePath.path("update-check"),
                        (path, value) -> new Object[]{"update-check", "enabled"})
                .addAction(NodePath.path("tool"),
                        (path, value) -> new Object[]{"editor", "tool"})
                .addAction(NodePath.path("editor-scale-min-distance"),
                        (path, value) -> new Object[]{"editor", "scale", "min-distance"})
                .addAction(NodePath.path("editor-scale-max-distance"),
                        (path, value) -> new Object[]{"editor", "scale", "max-distance"})
                .addAction(NodePath.path("editor-range"),
                        (path, value) -> new Object[]{"editor", "button", "range"})
                .addAction(NodePath.path("editor-selection-range"),
                        (path, value) -> new Object[]{"editor", "selection", "range"})
                .addAction(NodePath.path("editor-selection-limit"),
                        (path, value) -> new Object[]{"editor", "selection", "group", "limit"})
                .addAction(NodePath.path("editor-selection-distance"),
                        (path, value) -> new Object[]{"editor", "selection", "group", "range"})
                .addAction(NodePath.path("editor-button-limit"),
                        (path, value) -> new Object[]{"editor", "discovery", "limit"})
                .addAction(NodePath.path("editor-look-threshold"),
                        (path, value) -> new Object[]{"editor", "button", "threshold"})
                .addAction(NodePath.path("interpolation-ticks"),
                        (path, value) -> new Object[]{"editor", "button", "interpolation-ticks"})
                .addAction(NodePath.path("menu-background"),
                        (path, value) -> new Object[]{"editor", "menu", "background"})
                .addAction(NodePath.path("message-formats"),
                        (path, value) -> {
                            value.set(MessageConfig.formatType(), value.get(MessageConfig.formatType()));
                            return new Object[]{"message", "format"};
                        })
                .addAction(NodePath.path("server-side-translation"),
                        (path, value) -> new Object[]{"message", "server-side-translation"})
                .addAction(NodePath.path("swap-hands-button"), TransformAction.remove())
                .build();
    }
}
