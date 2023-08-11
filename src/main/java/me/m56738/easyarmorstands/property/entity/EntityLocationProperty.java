package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntityLocationProperty implements Property<Location> {
    public static final PropertyType<Location> TYPE = new Type();
    private final Entity entity;

    public EntityLocationProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Location> getType() {
        return TYPE;
    }

    @Override
    public Location getValue() {
        return entity.getLocation();
    }

    @Override
    public boolean setValue(Location value) {
        return entity.teleport(value);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements PropertyType<Location> {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.location";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("position");
        }

        @Override
        public Component getValueComponent(Location value) {
            return Util.formatLocation(value);
        }
    }
}
