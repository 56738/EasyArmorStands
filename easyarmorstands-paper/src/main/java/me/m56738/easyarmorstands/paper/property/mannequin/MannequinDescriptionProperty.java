package me.m56738.easyarmorstands.paper.property.mannequin;

import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Mannequin;

import java.util.Optional;

public class MannequinDescriptionProperty extends EntityProperty<Mannequin, Optional<Component>> {
    public MannequinDescriptionProperty(Mannequin entity) {
        super(entity);
    }

    @Override
    public PropertyType<Optional<Component>> getType() {
        return MannequinPropertyTypes.DESCRIPTION;
    }

    @Override
    public Optional<Component> getValue() {
        return Optional.ofNullable(entity.getDescription());
    }

    @Override
    public boolean setValue(Optional<Component> value) {
        entity.setDescription(value.orElse(null));
        return true;
    }
}
