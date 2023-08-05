package me.m56738.easyarmorstands.property.entity;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.property.LegacyEntityPropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCustomNameVisibleProperty implements LegacyEntityPropertyType<Entity, Boolean> {
    @Override
    public Boolean getValue(Entity entity) {
        return entity.isCustomNameVisible();
    }

    @Override
    public TypeToken<Boolean> getValueType() {
        return TypeToken.get(Boolean.class);
    }

    @Override
    public void setValue(Entity entity, Boolean value) {
        entity.setCustomNameVisible(value);
    }

    @Override
    public @NotNull String getName() {
        return "namevisible";
    }

    @Override
    public @NotNull Class<Entity> getEntityType() {
        return Entity.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("custom name visible");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return Component.text(value);
    }

    @Override
    public @NotNull String getValueClipboardContent(Boolean value) {
        return Boolean.toString(value);
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.name.visible";
    }
}
