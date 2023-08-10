package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.BooleanProperty;
import me.m56738.easyarmorstands.property.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCustomNameVisibleProperty implements BooleanProperty {
    public static final Key<EntityCustomNameVisibleProperty> KEY = Key.of(EntityCustomNameVisibleProperty.class);
    private final Entity entity;

    public EntityCustomNameVisibleProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Boolean getValue() {
        return entity.isCustomNameVisible();
    }

    @Override
    public void setValue(Boolean value) {
        entity.setCustomNameVisible(value);
    }

    @Override
    public Action createChangeAction(Boolean oldValue, Boolean value) {
        return new EntityPropertyAction<>(entity, EntityCustomNameVisibleProperty::new, oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("custom name visible");
    }

    @Override
    public @NotNull Component getValueComponent(Boolean value) {
        return value
                ? Component.text("visible", NamedTextColor.GREEN)
                : Component.text("invisible", NamedTextColor.RED);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.name.visible";
    }
}
