package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.easyarmorstands.common.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.common.editor.EntityRotationProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleEntityToolProvider implements ToolProvider {
    private final EasyArmorStandsCommon eas;
    protected final ChangeContext changeContext;
    protected final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final @Nullable Property<Double> scaleProperty;
    protected PositionProvider positionProvider;
    protected RotationProvider rotationProvider;

    public SimpleEntityToolProvider(EasyArmorStandsCommon eas, Element element, ChangeContext changeContext) {
        this.eas = eas;
        this.changeContext = changeContext;
        this.properties = changeContext.getProperties(element);
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.scaleProperty = properties.getOrNull(EntityPropertyTypes.SCALE);
        this.positionProvider = new EntityPositionProvider(properties);
        this.rotationProvider = new EntityRotationProvider(properties);
    }

    @Override
    public @NotNull ToolContext context() {
        return ToolContext.of(positionProvider, rotationProvider);
    }

    @Override
    public @Nullable MoveTool move(@NotNull ToolContext context) {
        return MoveTool.of(context, changeContext, locationProperty);
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        if (axis == Axis.Y && context.rotation() == RotationProvider.identity()) {
            return AxisRotateTool.ofYaw(context, changeContext, locationProperty);
        }
        if (axis == Axis.X && context.rotation() == rotationProvider) {
            return AxisRotateTool.ofPitch(context, changeContext, locationProperty);
        }
        return null;
    }

    @Override
    public @Nullable ScaleTool scale(@NotNull ToolContext context) {
        if (scaleProperty != null) {
            Configuration configuration = eas.platform().getConfiguration();
            return ScaleTool.of(context, changeContext, locationProperty, scaleProperty, configuration.getMinEntityScale(), configuration.getMaxEntityScale());
        }
        return ToolProvider.super.scale(context);
    }
}
