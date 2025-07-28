package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import me.m56738.easyarmorstands.common.editor.tool.DelegateToolProvider;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandPartOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandPartRotationProvider;
import me.m56738.easyarmorstands.editor.armorstand.tool.ArmorStandPoseTool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmorStandPartToolProvider extends DelegateToolProvider {
    private final PropertyContainer properties;
    private final ArmorStandPart part;
    private final ChangeContext changeContext;

    public ArmorStandPartToolProvider(ArmorStandElement element, ArmorStandPart part, ChangeContext changeContext, ToolProvider toolProvider) {
        super(toolProvider, toolProvider.context()
                .withPosition(new EntityPositionProvider(element.getProperties(), new ArmorStandPartOffsetProvider(element.getProperties(), part, element)))
                .withRotation(new ArmorStandPartRotationProvider(element.getProperties(), part)));
        this.changeContext = changeContext;
        this.properties = changeContext.getProperties(element);
        this.part = part;
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        Property<Location> locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        Property<EulerAngles> poseProperty = properties.get(ArmorStandPropertyTypes.POSE.get(part));
        return new ArmorStandPoseTool(context, changeContext, locationProperty, poseProperty, axis);
    }
}
