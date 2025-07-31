package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.editor.node.SimpleEntityNode;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

import java.util.Objects;

public class SimpleEntityElement extends AbstractEntityElement implements DefaultEntityElement, SelectableElement, MenuElement, DestroyableElement, EditableElement {
    private final EasyArmorStandsCommon eas;
    private final Entity entity;
    private final SimpleEntityElementType type;
    private final PropertyRegistry properties = new PropertyRegistry();

    public SimpleEntityElement(EasyArmorStandsCommon eas, Entity entity, SimpleEntityElementType type) {
        super(eas, type, entity);
        this.eas = eas;
        this.entity = entity;
        this.type = type;
    }

    @Override
    public @NotNull Entity getEntity() {
        return entity;
    }

    public @NotNull SimpleEntityElementType getType() {
        return type;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public @NotNull PropertyRegistry getProperties() {
        return properties;
    }

    @Override
    public @NotNull ToolProvider getTools(@NotNull ChangeContext context) {
        return new SimpleEntityToolProvider(eas, this, context);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3dc position = entity.getLocation().position();
        double width = entity.getWidth();
        double height = entity.getHeight();
        return BoundingBox.of(position, width, height);
    }

    public double getScale() {
        Property<Double> scaleProperty = properties.getOrNull(EntityPropertyTypes.SCALE);
        if (scaleProperty != null) {
            return scaleProperty.getValue();
        } else {
            return 1;
        }
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        return new BoundingBoxButton(session, this,
                PositionProvider.of(properties.get(EntityPropertyTypes.LOCATION)),
                RotationProvider.identity());
    }

    @Override
    public @NotNull Node createNode(@NotNull Session session) {
        return new SimpleEntityNode(session, this);
    }

    @Override
    public void openMenu(@NotNull Player player) {
        // TODO
    }

    @Override
    public void destroy() {
        entity.remove();
    }

    @Override
    public boolean canEdit(@NotNull Player player) {
        return player.hasPermission(Permissions.entityType(Permissions.EDIT, type.getEntityType()));
    }

    @Override
    public boolean canDestroy(@NotNull Player player) {
        return player.hasPermission(Permissions.entityType(Permissions.DESTROY, type.getEntityType()));
    }

    @Override
    public @NotNull Component getName() {
        return Component.text(Util.getId(entity.getUniqueId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleEntityElement that = (SimpleEntityElement) o;
        return Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }
}
