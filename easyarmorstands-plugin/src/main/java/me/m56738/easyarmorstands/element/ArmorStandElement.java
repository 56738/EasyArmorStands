package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandRotationProvider;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandElement extends SimpleEntityElement<ArmorStand> {
    private final ArmorStand entity;

    public ArmorStandElement(ArmorStand entity, SimpleEntityElementType<ArmorStand> type) {
        super(entity, type);
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
        return new ArmorStandRootNode(session, entity, this);
    }

    @Override
    public @NotNull ToolProvider getTools(@NotNull PropertyContainer properties) {
        return new ArmorStandToolProvider(properties);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        ArmorStandSize size = ArmorStandSize.get(entity);
        Vector3d position = Util.toVector3d(entity.getLocation());
        double scale = getScale();
        double width = size.getWidth() * scale;
        double height = size.getHeight() * scale;
        return BoundingBox.of(position, width, height);
    }
}
