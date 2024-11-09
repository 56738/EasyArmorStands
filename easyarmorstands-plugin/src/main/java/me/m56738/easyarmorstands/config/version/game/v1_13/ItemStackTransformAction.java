package me.m56738.easyarmorstands.config.version.game.v1_13;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.NodePath;
import org.spongepowered.configurate.transformation.TransformAction;

import java.util.Locale;

public class ItemStackTransformAction implements TransformAction {
    @Override
    public Object[] visitPath(NodePath path, ConfigurationNode value) {
        ConfigurationNode typeNode = value.node("type");
        String type = typeNode.getString();
        if (type == null) {
            return null;
        }

        ConfigurationNode dataNode = value.node("data");
        if (dataNode.virtual()) {
            return null;
        }

        @SuppressWarnings("deprecation")
        DyeColor color = DyeColor.getByWoolData((byte) dataNode.getInt());
        if (color == null) {
            return null;
        }

        String nameWithColor = color.name() + "_" + type;
        Material material = Material.matchMaterial(nameWithColor);
        if (material == null) {
            return null;
        }

        typeNode.raw(material.toString().toLowerCase(Locale.ROOT));
        dataNode.raw(null);
        return null;
    }
}
