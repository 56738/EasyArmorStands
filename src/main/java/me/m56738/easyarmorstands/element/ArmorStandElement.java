package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandRotationProvider;
import me.m56738.easyarmorstands.editor.armorstand.layer.ArmorStandRootLayer;
import me.m56738.easyarmorstands.editor.node.ArmorStandNode;
import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class ArmorStandElement extends SimpleEntityElement<ArmorStand> {
    private final ArmorStand entity;

    public ArmorStandElement(EasyArmorStandsCommon eas, ArmorStand entity, SimpleEntityElementType<ArmorStand> type) {
        super(eas, entity, type);
        this.entity = entity;
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        PropertyRegistry properties = getProperties();
        return new BoundingBoxButton(session, this,
                new EntityPositionProvider(properties, new ArmorStandOffsetProvider(this, properties)),
                new ArmorStandRotationProvider(properties));
    }

    @Override
    public @NotNull Node createNode(@NotNull Session session) {
        if (eas.getConfiguration().editor.flattenArmorStands) {
            return new ArmorStandNode(eas, session, this);
        }
        return super.createNode(session);
    }

    @Override
    public @NotNull Layer createLayer(@NotNull Session session) {
        return new ArmorStandRootLayer(eas, session, entity, this);
    }

    @Override
    public @NotNull ToolProvider getTools(@NotNull PropertyContainer properties) {
        return new ArmorStandToolProvider(eas, properties);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        ArmorStandSize size = ArmorStandSize.get(entity);
        Vector3dc position = entity.location().position();
        double scale = getScale();
        double width = size.getWidth() * scale;
        double height = size.getHeight() * scale;
        return BoundingBox.of(position, width, height);
    }
}
