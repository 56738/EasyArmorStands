package me.m56738.easyarmorstands.property;

import io.leangen.geantyref.TypeToken;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public abstract class ComponentEntityProperty<E extends Entity> implements LegacyEntityPropertyType<E, Component> {
    @Override
    public TypeToken<Component> getValueType() {
        return TypeToken.get(Component.class);
    }

    @Override
    public @NotNull Component getValueName(Component value) {
        return value;
    }

    @Override
    public @NotNull String getValueClipboardContent(Component value) {
        return MiniMessage.miniMessage().serialize(value);
    }
}
