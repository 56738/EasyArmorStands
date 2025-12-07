package me.m56738.easyarmorstands.paper.property.mannequin;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Mannequin;
import org.jetbrains.annotations.NotNull;

public class MannequinImmovableProperty implements Property<Boolean> {
    private final Mannequin entity;

    public MannequinImmovableProperty(Mannequin entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return MannequinPropertyTypes.IMMOVABLE;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.isImmovable();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setImmovable(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
