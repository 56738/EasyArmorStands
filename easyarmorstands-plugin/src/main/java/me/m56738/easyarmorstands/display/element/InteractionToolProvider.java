package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.editor.box.InteractionBoxEditor;
import me.m56738.easyarmorstands.editor.box.BoxCenterPositionProvider;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InteractionToolProvider implements ToolProvider {
    private final PropertyContainer properties;
    private final PositionProvider positionProvider;
    private final Property<Location> locationProperty;

    public InteractionToolProvider(PropertyContainer properties) {
        this.properties = properties;
        this.positionProvider = new BoxCenterPositionProvider(new InteractionBoxEditor(properties));
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @NotNull PositionProvider position() {
        return positionProvider;
    }

    @Override
    public @NotNull RotationProvider rotation() {
        return RotationProvider.identity();
    }

    @Override
    public @Nullable MoveTool move(@NotNull ToolContext context) {
        return MoveTool.of(context, properties, locationProperty);
    }
}
