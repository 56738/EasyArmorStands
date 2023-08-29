package me.m56738.easyarmorstands.editor.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.editor.RotationProvider;
import me.m56738.easyarmorstands.util.EasMath;
import org.bukkit.Location;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class EntityYawRotationProvider implements RotationProvider {
    private final Property<Location> property;
    private final Quaterniond temp = new Quaterniond();

    public EntityYawRotationProvider(Property<Location> property) {
        this.property = property;
    }

    @Override
    public Quaterniondc getRotation() {
        Location location = property.getValue();
        return EasMath.getEntityRotation(location.getYaw(), 0, temp);
    }
}
