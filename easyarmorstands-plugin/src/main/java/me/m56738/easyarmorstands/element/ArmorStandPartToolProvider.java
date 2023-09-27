package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandPartOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandPartRotationProvider;
import me.m56738.easyarmorstands.editor.armorstand.tool.ArmorStandPoseTool;
import me.m56738.easyarmorstands.editor.tool.DelegateToolProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmorStandPartToolProvider extends DelegateToolProvider {
    private final PropertyContainer properties;
    private final ArmorStandPart part;

    public ArmorStandPartToolProvider(PropertyContainer properties, ArmorStandPart part, ToolProvider toolProvider) {
        super(toolProvider,
                new EntityPositionProvider(properties, new ArmorStandPartOffsetProvider(properties, part)),
                new ArmorStandPartRotationProvider(properties, part));
        this.properties = properties;
        this.part = part;
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        return new ArmorStandPoseTool(properties, part, positionProvider, rotationProvider, axis);
    }
}
