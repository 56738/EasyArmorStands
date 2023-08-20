package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Display;

public class DisplayElementType<E extends Display> extends SimpleEntityElementType<E> {
    private final DisplayRootNodeFactory<E> factory;

    public DisplayElementType(Class<E> entityType, Component displayName, DisplayRootNodeFactory<E> factory) {
        super(entityType, displayName);
        this.factory = factory;
    }

    @Override
    protected SimpleEntityElement<E> createInstance(E entity) {
        return new DisplayElement<>(entity, this, factory);
    }

    @Override
    public void applyDefaultProperties(PropertyRegistry properties) {
        super.applyDefaultProperties(properties);
        Property<Location> locationProperty = properties.get(PropertyTypes.ENTITY_LOCATION);
        Location location = locationProperty.getValue().clone();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
    }
}
