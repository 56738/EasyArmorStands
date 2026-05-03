package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface ScaleTool extends PositionedTool<ScaleToolSession>, OrientedTool<ScaleToolSession> {
    static @NotNull ScaleTool of(
            @NotNull ToolContext context,
            @NotNull PropertyContainer properties,
            @NotNull Property<Location> locationProperty,
            @NotNull Property<Double> scaleProperty,
            double minScale,
            double maxScale) {
        return new ScalarScaleTool(context, properties, locationProperty, scaleProperty, minScale, maxScale);
    }
}
