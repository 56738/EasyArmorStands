package me.m56738.easyarmorstands.config.version.game;

import me.m56738.easyarmorstands.lib.configurate.ConfigurateException;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.NodePath;
import me.m56738.easyarmorstands.lib.configurate.transformation.TransformAction;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;

import java.util.Objects;

public class ReplaceTransformAction<T> implements TransformAction {
    private final TypeToken<T> type;
    private final T from;
    private final T to;

    private ReplaceTransformAction(TypeToken<T> type, T from, T to) {
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public static <T> ReplaceTransformAction<T> replace(TypeToken<T> type, T from, T to) {
        return new ReplaceTransformAction<>(type, from, to);
    }

    public static <T> ReplaceTransformAction<T> replace(Class<T> type, T from, T to) {
        return replace(TypeToken.get(type), from, to);
    }

    public static ReplaceTransformAction<String> replaceString(String from, String to) {
        return replace(String.class, from, to);
    }

    @Override
    public Object[] visitPath(NodePath path, ConfigurationNode node) throws ConfigurateException {
        if (Objects.equals(node.get(type), from)) {
            node.set(type, to);
        }
        return null;
    }
}
