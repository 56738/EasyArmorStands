package me.m56738.easyarmorstands.paper.property.mannequin;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Mannequin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MannequinDescriptionProperty implements Property<Optional<Component>> {
    private final Mannequin entity;

    public MannequinDescriptionProperty(Mannequin entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Optional<Component>> getType() {
        return MannequinPropertyTypes.DESCRIPTION;
    }

    @Override
    public @NotNull Optional<Component> getValue() {
        return Optional.ofNullable(entity.getDescription());
    }

    @Override
    public boolean setValue(@NotNull Optional<Component> value) {
        entity.setDescription(value.orElse(null));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
