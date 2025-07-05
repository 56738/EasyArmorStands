package me.m56738.easyarmorstands.display.property.display.interaction;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;

public class InteractionResponsiveProperty implements Property<Boolean> {
    private final Interaction entity;

    public InteractionResponsiveProperty(Interaction entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return InteractionPropertyTypes.RESPONSIVE;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.isResponsive();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setResponsive(value);
        return true;
    }
}
