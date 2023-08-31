package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandGroupMember extends SimpleEntityGroupMember<ArmorStand> {
    private final ArmorStandElement element;

    public ArmorStandGroupMember(ArmorStandElement element, PropertyContainer properties) {
        super(element, properties);
        this.element = element;
    }

    @NotNull
    @Override
    public ArmorStandElement getElement() {
        return element;
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        ArmorStand entity = element.getEntity();
        Vector3dc position = Util.toVector3d(entity.getLocation());
        ArmorStandSize size = ArmorStandSize.get(entity);
        double width = size.getWidth();
        double height = size.getHeight();
        return BoundingBox.of(
                position.sub(width / 2, 0, width / 2, new Vector3d()),
                position.add(width / 2, height, width / 2, new Vector3d()));
    }
}
