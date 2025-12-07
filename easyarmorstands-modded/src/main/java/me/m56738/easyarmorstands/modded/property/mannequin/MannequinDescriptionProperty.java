package me.m56738.easyarmorstands.modded.property.mannequin;

import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.Component;
import net.minecraft.world.entity.decoration.Mannequin;

import java.util.Optional;

public class MannequinDescriptionProperty extends EntityProperty<Mannequin, Optional<Component>> {
    private final MinecraftServerAudiences adventure;

    public MannequinDescriptionProperty(Mannequin entity, MinecraftServerAudiences adventure) {
        super(entity);
        this.adventure = adventure;
    }

    @Override
    public PropertyType<Optional<Component>> getType() {
        return MannequinPropertyTypes.DESCRIPTION;
    }

    @Override
    public Optional<Component> getValue() {
        return Optional.ofNullable(entity.getDescription()).map(adventure::asAdventure);
    }

    @Override
    public boolean setValue(Optional<Component> value) {
        value.map(adventure::asNative).ifPresent(entity::setDescription);
        entity.setHideDescription(value.isEmpty());
        return true;
    }
}
