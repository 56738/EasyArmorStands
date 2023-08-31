package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.group.GroupTool;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import org.bukkit.Location;

public abstract class SimpleEntityGroupTool implements GroupTool {
    private final PropertyContainer properties;
    private final Property<Location> property;
    private final Location originalLocation;

    public SimpleEntityGroupTool(PropertyContainer properties) {
        this.properties = properties;
        this.property = properties.get(EntityPropertyTypes.LOCATION);
        this.originalLocation = property.getValue().clone();
    }

    protected Location getOriginalLocation() {
        return originalLocation.clone();
    }

    protected void setLocation(Location location) {
        property.setValue(location);
    }

    @Override
    public void revert() {
        property.setValue(originalLocation);
    }

    @Override
    public void commit() {
        properties.commit();
    }

    @Override
    public boolean isValid() {
        return properties.isValid();
    }
}
