package me.m56738.easyarmorstands.config.version.game.v1_13;

import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.NodePath;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.configurate.transformation.TransformAction;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ItemStackTransformAction_v1_13 implements TransformAction {
    private static final String[] COLORS = new String[]{
            "white",
            "orange",
            "magenta",
            "light_blue",
            "yellow",
            "lime",
            "pink",
            "gray",
            "light_gray",
            "cyan",
            "purple",
            "blue",
            "brown",
            "green",
            "red",
            "black"
    };

    private static final List<String> COLORED_TYPES = Arrays.asList(
            "banner",
            "wool",
            "concrete",
            "stained_glass",
            "stained_glass_pane"
    );

    @Override
    public Object[] visitPath(NodePath path, ConfigurationNode value) throws SerializationException {
        ConfigurationNode typeNode = value.node("type");
        ConfigurationNode dataNode = value.node("data");

        if (processColored(typeNode, dataNode)) {
            return null;
        }

        if (processBoneMeal(typeNode, dataNode)) {
            return null;
        }

        if (processPlayerHead(typeNode, dataNode)) {
            return null;
        }

        if (processReplacement(typeNode, "step", "stone_slab")) {
            return null;
        }

        if (processReplacement(typeNode, "iron_fence", "iron_bars")) {
            return null;
        }

        if (processReplacement(typeNode, "double_plant", "sunflower")) {
            return null;
        }

        return null;
    }

    private boolean processColored(ConfigurationNode typeNode, ConfigurationNode dataNode) {
        String type = typeNode.getString();
        if (dataNode.virtual()) {
            return false;
        }
        int data = dataNode.getInt();
        if (type != null && data >= 0 && data < COLORS.length &&
                COLORED_TYPES.contains(type.toLowerCase(Locale.ROOT))) {
            String nameWithColor = COLORS[data] + "_" + type;
            typeNode.raw(nameWithColor.toLowerCase(Locale.ROOT));
            dataNode.raw(null);
            return true;
        }
        return false;
    }

    private boolean processBoneMeal(ConfigurationNode typeNode, ConfigurationNode dataNode) throws SerializationException {
        String type = typeNode.getString();
        if (dataNode.virtual()) {
            return false;
        }
        int data = dataNode.getInt();
        if (type != null && type.equalsIgnoreCase("ink_sack") && data == 15) {
            typeNode.raw("bone_meal");
            dataNode.raw(null);
            return true;
        }
        return false;
    }

    private boolean processPlayerHead(ConfigurationNode typeNode, ConfigurationNode dataNode) throws SerializationException {
        String type = typeNode.getString();
        if (dataNode.virtual()) {
            return false;
        }
        int data = dataNode.getInt();
        if (type != null && type.equalsIgnoreCase("skull_item") && data == 3) {
            typeNode.raw("player_head");
            dataNode.raw(null);
            return true;
        }
        return false;
    }

    private boolean processReplacement(ConfigurationNode node, String from, String to) throws SerializationException {
        String value = node.getString();
        if (Objects.equals(value, from)) {
            node.set(to);
            return true;
        }
        return false;
    }
}
