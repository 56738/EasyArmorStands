package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandButton;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

public class ArmorStandElement extends SimpleEntityElement<ArmorStand> {
    private final ArmorStand entity;

    public ArmorStandElement(ArmorStand entity, SimpleEntityElementType<ArmorStand> type) {
        super(entity, type);
        this.entity = entity;
    }

    @Override
    public Button createButton(Session session) {
        return new ArmorStandButton(session, this);
    }

    @Override
    public ElementNode createNode(Session session) {
        return new ArmorStandRootNode(session, entity, this);
    }

    @Override
    public @NotNull ToolProvider getTools(PropertyContainer properties) {
        return new ArmorStandToolProvider(properties);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        ArmorStandSize size = ArmorStandSize.get(entity);
        Vector3d position = Util.toVector3d(entity.getLocation());
        double width = size.getWidth();
        double height = size.getHeight();
        return BoundingBox.of(position, width, height);
    }
}
