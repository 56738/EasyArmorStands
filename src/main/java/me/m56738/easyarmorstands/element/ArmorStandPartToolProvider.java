package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
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

    public ArmorStandPartToolProvider(PropertyContainer properties, ArmorStandPart part, ArmorStandElement element, ToolProvider toolProvider) {
        super(toolProvider,
                new EntityPositionProvider(properties, new ArmorStandPartOffsetProvider(properties, part, element)),
                new ArmorStandPartRotationProvider(properties, part));
        this.properties = properties;
        this.part = part;
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        return new ArmorStandPoseTool(context, properties, part, axis);
    }
}
