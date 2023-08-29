package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.config.BeforeMinorVersionCondition;
import me.m56738.easyarmorstands.config.VersionOverrideCondition;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class VersionOverrideConditionSerializer implements TypeSerializer<VersionOverrideCondition> {
    @Override
    public VersionOverrideCondition deserialize(Type type, ConfigurationNode node) throws SerializationException {
        String conditionType = node.node("type").getString();
        if (conditionType == null) {
            return null;
        }
        if (conditionType.equals("before-minor-version")) {
            return new BeforeMinorVersionCondition(node.node("value").getInt());
        }
        throw new SerializationException("Unknown version override condition type: " + conditionType);
    }

    @Override
    public void serialize(Type type, @Nullable VersionOverrideCondition obj, ConfigurationNode node) throws SerializationException {
        throw new SerializationException(new UnsupportedOperationException());
    }
}
