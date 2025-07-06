package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.Location;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface AxisRotateTool extends AxisTool<AxisRotateToolSession> {
    static AxisRotateTool ofYaw(ToolContext context, ChangeContext changeContext, Property<Location> locationProperty) {
        return new LocationYawRotateTool(context, changeContext, locationProperty);
    }

    static AxisRotateTool ofPitch(ToolContext context, ChangeContext changeContext, Property<Location> locationProperty) {
        return new LocationPitchRotateTool(context, changeContext, locationProperty);
    }
}
