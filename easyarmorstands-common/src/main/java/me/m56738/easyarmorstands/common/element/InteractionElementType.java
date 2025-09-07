package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import org.jetbrains.annotations.NotNull;

public class InteractionElementType extends SimpleEntityElementType {
    private final EasyArmorStandsCommon eas;

    public InteractionElementType(EasyArmorStandsCommon eas) {
        super(eas, eas.getPlatform().getInteractionType());
        this.eas = eas;
    }

    @Override
    protected InteractionElement createInstance(Entity entity) {
        return new InteractionElement(eas, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        properties.put(DisplayPropertyTypes.BOX_WIDTH, 1f);
        properties.put(DisplayPropertyTypes.BOX_HEIGHT, 1f);
        properties.put(InteractionPropertyTypes.RESPONSIVE, true);
    }
}
