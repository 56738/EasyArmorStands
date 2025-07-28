package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.editor.node.InteractionRootNode;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class InteractionElement extends SimpleEntityElement {
    public InteractionElement(Platform platform, Entity entity, SimpleEntityElementType type) {
        super(platform, entity, type);
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
    public @NotNull InteractionToolProvider getTools(@NotNull ChangeContext changeContext) {
        return new InteractionToolProvider(this, changeContext);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3dc position = getProperties().get(EntityPropertyTypes.LOCATION).getValue().position();
        double width = getProperties().get(DisplayPropertyTypes.BOX_WIDTH).getValue();
        double height = getProperties().get(DisplayPropertyTypes.BOX_HEIGHT).getValue();
        return BoundingBox.of(position, width, height);
    }
}
