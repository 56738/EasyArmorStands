package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.paper.api.element.EntityElementType;
import me.m56738.easyarmorstands.paper.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperLocationAdapter;
import me.m56738.easyarmorstands.paper.permission.PaperPermissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SimpleEntityElementType<E extends Entity> implements EntityElementType<E> {
    private final EntityType entityType;
    private final Class<E> entityClass;
    private final Component displayName;

    public SimpleEntityElementType(EntityType entityType, Class<E> entityClass) {
        this.entityType = entityType;
        this.entityClass = entityClass;
        this.displayName = Component.translatable(entityType.translationKey());
    }

    public @NotNull EntityType getEntityType() {
        return entityType;
    }

    public @NotNull Class<E> getEntityClass() {
        return entityClass;
    }

    protected SimpleEntityElement<E> createInstance(E entity) {
        return new SimpleEntityElement<>(entity, this);
    }

    public SimpleEntityElement<E> getElement(@NotNull E entity) {
        SimpleEntityElement<E> element = createInstance(entity);
        Bukkit.getPluginManager().callEvent(new EntityElementInitializeEvent(element));
        return element;
    }

    @Override
    public @Nullable SimpleEntityElement<E> createElement(@NotNull PropertyContainer properties) {
        Property<Location> locationProperty = properties.getOrNull(EntityPropertyTypes.LOCATION);
        if (locationProperty == null) {
            return null;
        }
        Location location = locationProperty.getValue();
        SpawnedEntityConfigurator configurator = new SpawnedEntityConfigurator(properties);
        E entity;
        org.bukkit.Location nativeLocation = PaperLocationAdapter.toNative(location);
        try {
            entity = nativeLocation.getWorld().spawn(nativeLocation, entityClass, configurator);
        } catch (IllegalArgumentException e) {
            return null;
        }
        SimpleEntityElement<E> element = configurator.getElement();
        if (element != null) {
            entity.teleport(nativeLocation);
        } else {
            entity.remove();
        }
        return element;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return displayName;
    }

    @Override
    public boolean canSpawn(@NotNull Player player) {
        return player.hasPermission(PaperPermissions.entityType(Permissions.SPAWN, entityType));
    }

    private class SpawnedEntityConfigurator implements Consumer<E> {
        private final PropertyContainer properties;
        private SimpleEntityElement<E> element;

        private SpawnedEntityConfigurator(PropertyContainer properties) {
            this.properties = properties;
        }

        @Override
        public void accept(E entity) {
            element = SimpleEntityElementType.this.getElement(entity);
            if (element != null) {
                copyProperties(properties, element.getProperties());
            }
        }

        private void copyProperties(PropertyContainer source, PropertyContainer target) {
            source.forEach(p -> copyProperty(p, target));
        }

        private <T> void copyProperty(Property<T> sourceProperty, PropertyContainer target) {
            PropertyType<T> type = sourceProperty.getType();
            if (type == EntityPropertyTypes.LOCATION) {
                // Ignore the location property, we spawned the entity at that location
                return;
            }
            Property<T> targetProperty = target.getOrNull(type);
            if (targetProperty != null) {
                targetProperty.setValue(sourceProperty.getValue());
            }
        }

        public @Nullable SimpleEntityElement<E> getElement() {
            return element;
        }
    }
}
