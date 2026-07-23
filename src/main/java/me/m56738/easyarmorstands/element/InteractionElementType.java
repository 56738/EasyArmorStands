package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.platform.entity.Interaction;
import me.m56738.easyarmorstands.registry.EntityTypeKeys;
import org.jetbrains.annotations.NotNull;

public class InteractionElementType extends SimpleEntityElementType<Interaction> {
    public InteractionElementType(EasyArmorStandsCommon eas) {
        super(eas, eas.platform().getEntityType(EntityTypeKeys.INTERACTION), Interaction.class);
    }

    @Override
    protected InteractionElement createInstance(Interaction entity) {
        return new InteractionElement(eas, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        properties.put(DisplayPropertyTypes.BOX_WIDTH, 1f);
        properties.put(DisplayPropertyTypes.BOX_HEIGHT, 1f);
        properties.put(InteractionPropertyTypes.RESPONSIVE, true);
    }
}
