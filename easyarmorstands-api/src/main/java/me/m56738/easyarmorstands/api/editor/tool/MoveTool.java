package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface MoveTool extends PositionedTool<MoveToolSession>, OrientedTool<MoveToolSession> {
    static @NotNull MoveTool of(
            @NotNull ToolContext context,
            @NotNull ChangeContext changeContext,
            @NotNull Property<Location> locationProperty) {
        return new LocationMoveTool(context, changeContext, locationProperty);
    }
}
