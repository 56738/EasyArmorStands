package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EntityCustomNameProperty implements Property<Optional<Component>> {
    private final Entity entity;

    public EntityCustomNameProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Optional<Component>> getType() {
        return EntityPropertyTypes.CUSTOM_NAME;
    }

    @Override
    public @NotNull Optional<Component> getValue() {
        return Optional.ofNullable(entity.customName());
    }

    @Override
    public boolean setValue(@NotNull Optional<Component> value) {
        entity.customName(value.orElse(null));
        return true;
    }
}
