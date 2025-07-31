package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.common.editor.box.BoxCenterPositionProvider;
import me.m56738.easyarmorstands.common.editor.box.InteractionBoxEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InteractionToolProvider implements ToolProvider {
    private final ChangeContext changeContext;
    private final PositionProvider positionProvider;
    private final Property<Location> locationProperty;

    public InteractionToolProvider(InteractionElement element, ChangeContext changeContext) {
        this.changeContext = changeContext;
        PropertyContainer properties = changeContext.getProperties(element);
        this.positionProvider = new BoxCenterPositionProvider(new InteractionBoxEditor(changeContext, properties));
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @NotNull ToolContext context() {
        return ToolContext.of(positionProvider, RotationProvider.identity());
    }

    @Override
    public @Nullable MoveTool move(@NotNull ToolContext context) {
        return MoveTool.of(context, changeContext, locationProperty);
    }
}
