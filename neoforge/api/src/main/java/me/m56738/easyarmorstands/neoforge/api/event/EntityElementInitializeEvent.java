package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class EntityElementInitializeEvent extends EntityEvent {
    private final ConfigurableEntityElement<?> element;

    public EntityElementInitializeEvent(Entity entity, ConfigurableEntityElement<?> element) {
        super(entity);
        this.element = element;
    }

    public ConfigurableEntityElement<?> getElement() {
        return element;
    }
}
