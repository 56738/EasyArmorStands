package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.util.NativeComponentMapper;
import org.bukkit.entity.Mannequin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MannequinDescriptionProperty implements Property<Optional<Component>> {
    private final Mannequin entity;
    private final NativeComponentMapper mapper;

    public MannequinDescriptionProperty(Mannequin entity, NativeComponentMapper mapper) {
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public @NotNull PropertyType<Optional<Component>> getType() {
        return MannequinPropertyTypes.DESCRIPTION;
    }

    @Override
    public @NotNull Optional<Component> getValue() {
        return Optional.ofNullable(entity.getDescription()).map(mapper::convertFromNative);
    }

    @Override
    public boolean setValue(@NotNull Optional<Component> value) {
        entity.setDescription((net.kyori.adventure.text.Component) value.map(mapper::convertToNative).orElse(null));
        return true;
    }
}
