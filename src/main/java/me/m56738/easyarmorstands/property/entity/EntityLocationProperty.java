package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.key.PropertyKey;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityLocationProperty implements Property<Location> {
    public static final PropertyKey<Location> KEY = PropertyKey.of(EasyArmorStands.key("entity_location"));
    private final Entity entity;

    public EntityLocationProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Location getValue() {
        return entity.getLocation();
    }

    @Override
    public void setValue(Location value) {
        entity.teleport(value);
    }

    @Override
    public Action createChangeAction(Location oldValue, Location value) {
        return new EntityPropertyAction<>(entity, EntityLocationProperty::new, oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("position");
    }

    @Override
    public @NotNull Component getValueComponent(Location value) {
        return Util.formatLocation(value);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.location";
    }
}
