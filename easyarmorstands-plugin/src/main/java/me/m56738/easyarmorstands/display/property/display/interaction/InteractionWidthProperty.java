package me.m56738.easyarmorstands.display.property.display.interaction;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;

public class InteractionWidthProperty implements Property<Float> {
    private final Interaction entity;

    public InteractionWidthProperty(Interaction entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Float> getType() {
        return DisplayPropertyTypes.BOX_WIDTH;
    }

    @Override
    public @NotNull Float getValue() {
        return entity.getInteractionWidth();
    }

    @Override
    public boolean setValue(@NotNull Float value) {
        entity.setInteractionWidth(value);
        return true;
    }
}
