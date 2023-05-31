package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.property.ComponentEntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCustomNameProperty extends ComponentEntityProperty<Entity> {
    private final ComponentCapability componentCapability;

    public EntityCustomNameProperty(ComponentCapability componentCapability) {
        this.componentCapability = componentCapability;
    }

    @Override
    public Component getValue(Entity entity) {
        Component name = componentCapability.getCustomName(entity);
        if (name == null) {
            name = Component.empty();
        }
        return name;
    }

    @Override
    public void setValue(Entity entity, Component value) {
        componentCapability.setCustomName(entity, value);
    }

    @Override
    public @NotNull String getName() {
        return "name";
    }

    @Override
    public @NotNull Class<Entity> getEntityType() {
        return Entity.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("custom name");
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.name";
    }
}
