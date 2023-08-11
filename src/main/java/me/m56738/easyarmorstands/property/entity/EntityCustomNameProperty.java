package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.property.ComponentPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCustomNameProperty implements Property<@Nullable Component> {
    public static final PropertyType<Component> TYPE = new Type();
    private final Entity entity;
    private final ComponentCapability componentCapability;

    public EntityCustomNameProperty(Entity entity, ComponentCapability componentCapability) {
        this.entity = entity;
        this.componentCapability = componentCapability;
    }

    @Override
    public PropertyType<Component> getType() {
        return TYPE;
    }

    @Override
    public @Nullable Component getValue() {
        return componentCapability.getCustomName(entity);
    }

    @Override
    public boolean setValue(@Nullable Component value) {
        componentCapability.setCustomName(entity, value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements ComponentPropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.name";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("custom name");
        }
    }
}
