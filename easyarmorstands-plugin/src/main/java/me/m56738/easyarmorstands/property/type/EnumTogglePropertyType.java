package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class EnumTogglePropertyType<T extends Enum<T>> extends EnumPropertyType<T> {
    public EnumTogglePropertyType(@NotNull Key key, Class<T> type) {
        super(key, type);
    }
}
