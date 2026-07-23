package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.util.Location;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class SimpleEntityElementType<E extends Entity> implements EntityElementType<E> {
    protected final EasyArmorStandsCommon eas;
    private final EntityType entityType;
    private final Class<E> entityClass;
    private final Component displayName;

    public SimpleEntityElementType(EasyArmorStandsCommon eas, EntityType entityType, Class<E> entityClass) {
        this.eas = eas;
        this.entityType = entityType;
        this.entityClass = entityClass;
        this.displayName = Component.translatable(entityType);
    }

    @Override
    public @NotNull Key key() {
        return entityType.key();
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
        return new SimpleEntityElement<>(eas, entity, this);
    }

    @Override
    public SimpleEntityElement<E> getElement(@NotNull E entity) {
        SimpleEntityElement<E> element = createInstance(entity);
        new EntityElementPopulator(eas.platform()).registerProperties(element);
        eas.eventDispatcher().dispatchEntityElementInitialize(element);
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
        @SuppressWarnings("unchecked")
        E entity = (E) location.world().spawn(location, entityType, e -> configurator.accept((E) e));
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
            // TODO
//            PaperEntity.toNative(entity).getPersistentDataContainer().set(EntityElementKeys.ELEMENT_TYPE, PersistentDataType.STRING, SimpleEntityElementType.this.key().asString());
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
