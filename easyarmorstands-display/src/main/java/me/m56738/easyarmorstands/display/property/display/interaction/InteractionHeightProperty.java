package me.m56738.easyarmorstands.display.property.display.interaction;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;

public class InteractionHeightProperty implements Property<Float> {
    private final Interaction entity;

    public InteractionHeightProperty(Interaction entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Float> getType() {
        return DisplayPropertyTypes.BOX_HEIGHT;
    }

    @Override
    public @NotNull Float getValue() {
        return entity.getInteractionHeight();
    }

    @Override
    public boolean setValue(@NotNull Float value) {
        entity.setInteractionHeight(value);
        return true;
    }
}
