package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.editor.EntityYawRotationProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ArmorStandToolProvider extends SimpleEntityToolProvider {
    private final Property<Location> locationProperty;

    public ArmorStandToolProvider(EasyArmorStandsCommon eas, ArmorStandElement element, ChangeContext context) {
        super(eas, element, context);
        rotationProvider = new EntityYawRotationProvider(properties);
        locationProperty = properties.get(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        if (axis == Axis.Y && (context.rotation() == RotationProvider.identity() || context.rotation() == rotationProvider)) {
            // armor stands have no pitch, yaw tool also works in local mode
            return AxisRotateTool.ofYaw(context, changeContext, locationProperty);
        }
        return null;
    }
}
