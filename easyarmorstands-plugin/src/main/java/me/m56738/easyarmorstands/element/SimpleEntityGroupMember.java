package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.group.GroupMember;
import me.m56738.easyarmorstands.api.group.GroupMoveTool;
import me.m56738.easyarmorstands.api.group.GroupRotateTool;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public class SimpleEntityGroupMember<E extends Entity> implements GroupMember {
    private final SimpleEntityElement<E> element;
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;

    public SimpleEntityGroupMember(SimpleEntityElement<E> element, PropertyContainer properties) {
        this.element = element;
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @NotNull SimpleEntityElement<E> getElement() {
        return element;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return Util.toVector3d(locationProperty.getValue());
    }

    @Override
    public @Nullable GroupMoveTool move() {
        return new SimpleEntityGroupMoveTool(properties);
    }

    @Override
    public @Nullable GroupRotateTool rotate(@NotNull Vector3dc anchor, @NotNull Axis axis) {
        if (axis == Axis.Y) {
            return new SimpleEntityGroupYawTool(properties, anchor, axis);
        }
        return GroupMember.super.rotate(anchor, axis);
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
