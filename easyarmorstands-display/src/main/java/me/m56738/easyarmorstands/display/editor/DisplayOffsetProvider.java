package me.m56738.easyarmorstands.display.editor;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.lib.joml.Quaterniond;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.joml.Vector3fc;
import me.m56738.easyarmorstands.util.EasMath;
import org.bukkit.Location;

public class DisplayOffsetProvider implements OffsetProvider {
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Quaterniond currentRotation = new Quaterniond();
    private final Vector3d currentOffset = new Vector3d();

    public DisplayOffsetProvider(PropertyContainer properties) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
    }

    @Override
    public Vector3dc getOffset() {
        Location location = locationProperty.getValue();
        return currentOffset.set(translationProperty.getValue()).rotate(
                EasMath.getEntityRotation(location.getYaw(), location.getPitch(), currentRotation));
    }
}
