package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.Component;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

public class EntityCustomNameProperty extends EntityProperty<Entity, Optional<Component>> {
    private final MinecraftServerAudiences adventure;

    public EntityCustomNameProperty(Entity entity, MinecraftServerAudiences adventure) {
        super(entity);
        this.adventure = adventure;
    }

    @Override
    public PropertyType<Optional<Component>> getType() {
        return EntityPropertyTypes.CUSTOM_NAME;
    }

    @Override
    public Optional<Component> getValue() {
        return Optional.ofNullable(entity.getCustomName()).map(adventure::asAdventure);
    }

    @Override
    public boolean setValue(Optional<Component> value) {
        entity.setCustomName(value.map(adventure::asNative).orElse(null));
        return true;
    }
}
