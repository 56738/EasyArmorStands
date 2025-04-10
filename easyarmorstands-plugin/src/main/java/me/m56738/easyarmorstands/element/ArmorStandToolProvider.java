package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityYawRotationProvider;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmorStandToolProvider extends SimpleEntityToolProvider {
    private final Property<Location> locationProperty;

    public ArmorStandToolProvider(PropertyContainer properties) {
        super(properties);
        rotationProvider = new EntityYawRotationProvider(properties);
        locationProperty = properties.get(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        if (axis == Axis.Y && (context.rotation() == RotationProvider.identity() || context.rotation() == rotation())) {
            // armor stands have no pitch, yaw tool also works in local mode
            return AxisRotateTool.ofYaw(context, properties, locationProperty);
        }
        return null;
    }
}
