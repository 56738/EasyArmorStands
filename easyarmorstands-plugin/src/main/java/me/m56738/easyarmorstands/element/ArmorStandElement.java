package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandRotationProvider;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandRootNode;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class ArmorStandElement extends SimpleEntityElement {
    private final Entity entity;

    public ArmorStandElement(Platform platform, Entity entity, SimpleEntityElementType type) {
        super(platform, entity, type);
        this.entity = entity;
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        PropertyContainer properties = getProperties();
        return new BoundingBoxButton(session, this,
                new EntityPositionProvider(properties, new ArmorStandOffsetProvider(this, properties)),
                new ArmorStandRotationProvider(properties));
    }

    @Override
    public @NotNull Node createNode(@NotNull Session session) {
        return new ArmorStandRootNode(session, this);
    }

    @Override
    public @NotNull ToolProvider getTools(@NotNull ChangeContext context) {
        return new ArmorStandToolProvider(this, context);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        ArmorStandSize size = getProperties().get(ArmorStandPropertyTypes.SIZE).getValue();
        Vector3dc position = entity.getLocation().position();
        double scale = getScale();
        double width = size.getWidth() * scale;
        double height = size.getHeight() * scale;
        return BoundingBox.of(position, width, height);
    }
}
