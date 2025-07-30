package me.m56738.easyarmorstands.common.platform;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.platform.world.World;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.gizmo.api.GizmoFactory;
import org.incendo.cloud.parser.ParserDescriptor;

import java.io.Closeable;
import java.util.Collection;

public interface CommonPlatform extends Platform, Closeable {
    Configuration getConfiguration();

    GizmoFactory getGizmoFactory(Player player);

    @Override
    void close();

    <C> ParserDescriptor<C, Location> getLocationParser();

    boolean isIgnored(Entity entity);

    Collection<Entity> getNearbyEntities(Location location, double deltaX, double deltaY, double deltaZ);

    Collection<Entity> getTaggedEntities(World world, String tag);

    Collection<Entity> getAllEntities(World world);

    Collection<EntityType> getAllEntityTypes();

    void openSpawnMenu(Player player);

    boolean canDiscoverElement(Player player, EditableElement element);

    boolean canSelectElement(Player player, EditableElement element);

    boolean canCreateElement(Player player, ElementType type, PropertyContainer properties);

    boolean canDestroyElement(Player player, DestroyableElement element);

    <T> boolean canChangeProperty(Player player, Element element, Property<T> property, T value);
}
