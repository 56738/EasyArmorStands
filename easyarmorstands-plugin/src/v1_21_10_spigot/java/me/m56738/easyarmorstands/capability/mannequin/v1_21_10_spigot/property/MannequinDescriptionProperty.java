package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot.property;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.lib.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
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
        if (entity.isHideDescription()) {
            return Optional.empty();
        }
        return Optional.ofNullable(entity.getDescripion()).map(BukkitComponentSerializer.legacy()::deserialize);
    }

    @Override
    public boolean setValue(@NotNull Optional<Component> value) {
        value.map(BukkitComponentSerializer.legacy()::serialize).ifPresent(entity::setDescription);
        entity.setHideDescription(!value.isPresent());
        return true;
    }
}
