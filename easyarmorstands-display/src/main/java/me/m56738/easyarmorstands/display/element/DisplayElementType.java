package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNodeFactory;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
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
    public void applyDefaultProperties(PropertyMap properties) {
        super.applyDefaultProperties(properties);
        Property<Location> locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        Location location = locationProperty.getValue().clone();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
    }
}
