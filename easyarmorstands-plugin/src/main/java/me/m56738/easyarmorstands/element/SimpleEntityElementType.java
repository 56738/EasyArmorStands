package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        EntityTypeCapability entityTypeCapability = EasyArmorStandsPlugin.getInstance().getCapability(EntityTypeCapability.class);
        this.entityType = entityType;
        this.entityClass = entityClass;
        this.displayName = entityTypeCapability.getName(entityType);
    }

    @Override
    public @NotNull EntityType getEntityType() {
        return entityType;
    }

    @Override
    public @NotNull Class<E> getEntityClass() {
        return entityClass;
    }

    protected SimpleEntityElement<E> createInstance(E entity) {
        return new SimpleEntityElement<>(entity, this);
    }

    @Override
    public SimpleEntityElement<E> getElement(@NotNull E entity) {
        SimpleEntityElement<E> element = createInstance(entity);
        Bukkit.getPluginManager().callEvent(new EntityElementInitializeEvent(element));
        return element;
    }

    @Override
    public @Nullable SimpleEntityElement<E> createElement(@NotNull PropertyContainer properties) {
        Location location = properties.get(EntityPropertyTypes.LOCATION).getValue();
        SpawnedEntityConfigurator configurator = new SpawnedEntityConfigurator(properties);
        E entity = EasyArmorStandsPlugin.getInstance().getCapability(SpawnCapability.class).spawnEntity(location, entityClass, configurator);
        if (entity == null) {
            return null;
        }
        SimpleEntityElement<E> element = configurator.getElement();
        if (element != null) {
            entity.teleport(location);
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
        return player.hasPermission(Permissions.entityType(Permissions.SPAWN, entityType));
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
