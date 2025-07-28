package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.paper.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.paper.permission.PaperPermissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SimpleEntityElementType implements EntityElementType {
    private final Platform platform;
    private final EntityType entityType;

    public SimpleEntityElementType(Platform platform, EntityType entityType) {
        this.platform = platform;
        this.entityType = entityType;
    }

    public @NotNull EntityType getEntityType() {
        return entityType;
    }

    protected SimpleEntityElement createInstance(Entity entity) {
        return new SimpleEntityElement(platform, entity, this);
    }

    public SimpleEntityElement getElement(@NotNull Entity entity) {
        SimpleEntityElement element = createInstance(entity);
        Bukkit.getPluginManager().callEvent(new EntityElementInitializeEvent(element));
        return element;
    }

    @Override
    public @Nullable SimpleEntityElement createElement(@NotNull PropertyContainer properties) {
        Property<Location> locationProperty = properties.getOrNull(EntityPropertyTypes.LOCATION);
        if (locationProperty == null) {
            return null;
        }
        Location location = locationProperty.getValue();
        SpawnedEntityConfigurator configurator = new SpawnedEntityConfigurator(properties);
        Entity entity = platform.spawnEntity(entityType, location, configurator);
        SimpleEntityElement element = configurator.getElement();
        if (element != null) {
            entity.setLocation(location);
        } else {
            entity.remove();
        }
        return element;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return entityType.asComponent();
    }

    @Override
    public boolean canSpawn(@NotNull Player player) {
        return player.hasPermission(PaperPermissions.entityType(Permissions.SPAWN, entityType));
    }

    private class SpawnedEntityConfigurator implements Consumer<Entity> {
        private final PropertyContainer properties;
        private SimpleEntityElement element;

        private SpawnedEntityConfigurator(PropertyContainer properties) {
            this.properties = properties;
        }

        @Override
        public void accept(Entity entity) {
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

        public @Nullable SimpleEntityElement getElement() {
            return element;
        }
    }
}
