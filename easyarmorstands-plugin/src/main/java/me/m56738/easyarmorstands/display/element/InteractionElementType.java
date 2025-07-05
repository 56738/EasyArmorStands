package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;

public class InteractionElementType extends SimpleEntityElementType<Interaction> {
    public InteractionElementType() {
        super(EntityType.INTERACTION, Interaction.class);
    }

    @Override
    protected InteractionElement createInstance(Interaction entity) {
        return new InteractionElement(entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        properties.put(DisplayPropertyTypes.BOX_WIDTH, 1f);
        properties.put(DisplayPropertyTypes.BOX_HEIGHT, 1f);
        properties.put(InteractionPropertyTypes.RESPONSIVE, true);
    }
}
