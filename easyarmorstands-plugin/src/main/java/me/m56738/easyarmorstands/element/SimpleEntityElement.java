package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.node.SimpleEntityNode;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.util.Objects;

public class SimpleEntityElement<E extends Entity> implements ConfigurableEntityElement<E>, SelectableElement, MenuElement, DestroyableElement, EditableElement {
    private final E entity;
    private final SimpleEntityElementType<E> type;
    private final PropertyRegistry properties = new PropertyRegistry();

    public SimpleEntityElement(E entity, SimpleEntityElementType<E> type) {
        this.entity = entity;
        this.type = type;
    }

    @Override
    public @NotNull E getEntity() {
        return entity;
    }

    @Override
    public @NotNull SimpleEntityElementType<E> getType() {
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
        return new SimpleEntityToolProvider(this, context);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3d position = Util.toVector3d(entity.getLocation());
        double width = entity.getWidth();
        double height = entity.getHeight();
        return BoundingBox.of(position, width, height);
    }

    public double getScale() {
        if (entity instanceof Attributable attributable) {
            AttributeInstance attribute = attributable.getAttribute(Attribute.SCALE);
            if (attribute != null) {
                return attribute.getValue();
            }
        }
        return 1;
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        return new BoundingBoxButton(session, this,
                new EntityPositionProvider(properties),
                RotationProvider.identity());
    }

    @Override
    public @NotNull Node createNode(@NotNull Session session) {
        return new SimpleEntityNode(session, this);
    }

    @Override
    public void openMenu(@NotNull Player player) {
        Session session = EasyArmorStandsPlugin.getInstance().sessionManager().getSession(player);
        EasyArmorStandsPlugin.getInstance().openEntityMenu(player, session, this);
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
        SimpleEntityElement<?> that = (SimpleEntityElement<?>) o;
        return Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }
}
