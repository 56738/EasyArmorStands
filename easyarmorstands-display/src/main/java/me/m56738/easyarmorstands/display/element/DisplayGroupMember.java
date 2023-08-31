package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.group.GroupRotateTool;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.element.SimpleEntityGroupMember;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayGroupMember<E extends Display> extends SimpleEntityGroupMember<E> {
    private final DisplayElement<E> element;
    private final PropertyContainer properties;

    public DisplayGroupMember(DisplayElement<E> element, PropertyContainer properties) {
        super(element, properties);
        this.element = element;
        this.properties = properties;
    }

    @NotNull
    @Override
    public DisplayElement<E> getElement() {
        return element;
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Display entity = element.getEntity();
        Vector3dc position = Util.toVector3d(entity.getLocation());
        float width = entity.getDisplayWidth();
        float height = entity.getDisplayHeight();
        return BoundingBox.of(
                position.sub(width / 2, 0, width / 2, new Vector3d()),
                position.add(width / 2, height, width / 2, new Vector3d()));
    }

    @Override
    public @Nullable GroupRotateTool rotate(@NotNull Vector3dc anchor, @NotNull Axis axis) {
        return new DisplayGroupRotateTool(properties, anchor, axis);
    }
}
