package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.interaction.layer.InteractionRootLayer;
import me.m56738.easyarmorstands.platform.entity.Interaction;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class InteractionElement extends SimpleEntityElement<Interaction> {
    private final Interaction entity;

    public InteractionElement(EasyArmorStandsCommon eas, Interaction entity, SimpleEntityElementType<Interaction> type) {
        super(eas, entity, type);
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
    public @NotNull Layer createLayer(@NotNull Session session) {
        return new InteractionRootLayer(session, this);
    }

    @Override
    public @NotNull InteractionToolProvider getTools(@NotNull PropertyContainer properties) {
        return new InteractionToolProvider(properties);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3dc position = entity.location().position();
        double width = entity.getInteractionWidth();
        double height = entity.getInteractionHeight();
        return BoundingBox.of(position, width, height);
    }
}
