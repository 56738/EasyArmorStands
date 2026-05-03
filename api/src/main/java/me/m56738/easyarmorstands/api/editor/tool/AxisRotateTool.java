package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface AxisRotateTool extends AxisTool<AxisRotateToolSession> {
    static @NotNull AxisRotateTool ofYaw(@NotNull ToolContext context, @NotNull PropertyContainer properties, @NotNull Property<Location> locationProperty) {
        return new LocationYawRotateTool(context, properties, locationProperty);
    }

    static @NotNull AxisRotateTool ofPitch(@NotNull ToolContext context, @NotNull PropertyContainer properties, @NotNull Property<Location> locationProperty) {
        return new LocationPitchRotateTool(context, properties, locationProperty);
    }
}
