package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.editor.node.InteractionRootNode;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;

public class InteractionElement extends SimpleEntityElement<Interaction> {
    private final Interaction entity;

    public InteractionElement(Interaction entity, SimpleEntityElementType<Interaction> type) {
        super(entity, type);
        this.entity = entity;
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        PropertyContainer properties = getProperties();
        return new BoundingBoxButton(session, this,
                new EntityPositionProvider(properties),
                RotationProvider.identity());
    }

    @Override
    public @NotNull Node createNode(@NotNull Session session) {
        return new InteractionRootNode(session, this);
    }

    @Override
    public @NotNull InteractionToolProvider getTools(@NotNull PropertyContainer properties) {
        return new InteractionToolProvider(properties);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3d position = Util.toVector3d(entity.getLocation());
        double width = entity.getInteractionWidth();
        double height = entity.getInteractionHeight();
        return BoundingBox.of(position, width, height);
    }
}
