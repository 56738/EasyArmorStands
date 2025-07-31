package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.editor.DisplayOffsetProvider;
import me.m56738.easyarmorstands.common.editor.DisplayRotationProvider;
import me.m56738.easyarmorstands.common.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.common.editor.node.DisplayRootNode;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class DisplayElement extends SimpleEntityElement {
    private final EasyArmorStandsCommon eas;
    private final Entity entity;

    public DisplayElement(EasyArmorStandsCommon eas, Entity entity, SimpleEntityElementType type) {
        super(eas, entity, type);
        this.eas = eas;
        this.entity = entity;
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        PropertyContainer properties = getProperties();
        return new BoundingBoxButton(session, this,
                new EntityPositionProvider(properties, new DisplayOffsetProvider(properties)),
                new DisplayRotationProvider(properties));
    }

    @Override
    public @NotNull Node createNode(@NotNull Session session) {
        return new DisplayRootNode(session, this);
    }

    @Override
    public @NotNull DisplayToolProvider getTools(@NotNull ChangeContext context) {
        return new DisplayToolProvider(eas, this, context);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3dc position = entity.getLocation().position();
        double width = getProperties().get(DisplayPropertyTypes.BOX_WIDTH).getValue();
        double height = getProperties().get(DisplayPropertyTypes.BOX_HEIGHT).getValue();
        return BoundingBox.of(position, width, height);
    }

    @Override
    public void openMenu(@NotNull Player player) {
        // TODO
    }
}
