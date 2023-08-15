package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.event.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.function.Consumer;

public class SimpleEntityElementType<E extends Entity> implements EntityElementType<E> {
    private final Class<E> entityType;
    private final Component displayName;

    public SimpleEntityElementType(Class<E> entityType, Component displayName) {
        this.entityType = entityType;
        this.displayName = displayName;
    }

    @Override
    public Class<E> getEntityType() {
        return entityType;
    }

    protected SimpleEntityElement<E> createInstance(E entity) {
        return new SimpleEntityElement<>(entity, this);
    }

    @Override
    public SimpleEntityElement<E> getElement(E entity) {
        SimpleEntityElement<E> element = createInstance(entity);
        Bukkit.getPluginManager().callEvent(new EntityElementInitializeEvent(element));
        return element;
    }

    @Override
    public SimpleEntityElement<E> createElement(PropertyContainer properties) {
        Location location = properties.get(EntityLocationProperty.TYPE).getValue();
        SpawnedEntityConfigurator configurator = new SpawnedEntityConfigurator(properties);
        E entity = EasyArmorStands.getInstance().getCapability(SpawnCapability.class).spawnEntity(location, entityType, configurator);
        entity.teleport(location);
        return configurator.getElement();
    }

    @Override
    public Component getDisplayName() {
        return displayName;
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
            copyProperties(properties, element.getProperties());
        }

        private void copyProperties(PropertyContainer source, PropertyContainer target) {
            source.forEach(p -> copyProperty(p, target));
        }

        private <T> void copyProperty(Property<T> sourceProperty, PropertyContainer target) {
            PropertyType<T> type = sourceProperty.getType();
            if (type == EntityLocationProperty.TYPE) {
                // Ignore the location property, we spawned the entity at that location
                return;
            }
            Property<T> targetProperty = target.getOrNull(type);
            if (targetProperty != null) {
                targetProperty.setValue(sourceProperty.getValue());
            }
        }

        public SimpleEntityElement<E> getElement() {
            return element;
        }
    }
}
