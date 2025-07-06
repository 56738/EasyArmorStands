package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface ScaleTool extends PositionedTool<ScaleToolSession>, OrientedTool<ScaleToolSession> {
    static @NotNull ScaleTool of(
            @NotNull ToolContext context,
            @NotNull ChangeContext changeContext,
            @NotNull Property<Location> locationProperty,
            @NotNull Property<Double> scaleProperty,
            double minScale,
            double maxScale) {
        return new ScalarScaleTool(context, changeContext, locationProperty, scaleProperty, minScale, maxScale);
    }
}
