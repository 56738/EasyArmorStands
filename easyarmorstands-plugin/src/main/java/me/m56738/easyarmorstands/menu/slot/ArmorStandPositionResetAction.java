package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import org.bukkit.Location;

import java.util.function.Consumer;

public class ArmorStandPositionResetAction implements Consumer<MenuClick> {
    private final Property<Location> property;
    private final PropertyContainer container;

    public ArmorStandPositionResetAction(PropertyContainer properties) {
        this.property = properties.get(EntityPropertyTypes.LOCATION);
        this.container = properties;
    }

    @Override
    public void accept(MenuClick click) {
        Location location = property.getValue();
        location.setYaw(0);
        location.setPitch(0);
        property.setValue(location);
        container.commit();
    }
}
