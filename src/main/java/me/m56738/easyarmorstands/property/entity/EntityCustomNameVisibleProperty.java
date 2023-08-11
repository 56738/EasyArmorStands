package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.property.BooleanPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;

public class EntityCustomNameVisibleProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final Entity entity;

    public EntityCustomNameVisibleProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return entity.isCustomNameVisible();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setCustomNameVisible(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements BooleanPropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.name.visible";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("custom name visible");
        }

        @Override
        public Component getValueComponent(Boolean value) {
            return value
                    ? Component.text("visible", NamedTextColor.GREEN)
                    : Component.text("invisible", NamedTextColor.RED);
        }
    }
}
