package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.platform.entity.Mannequin;
import me.m56738.easyarmorstands.registry.EntityTypeKeys;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MannequinElementType extends SimpleEntityElementType<Mannequin> {
    public MannequinElementType(EasyArmorStandsCommon eas) {
        super(eas, eas.platform().getEntityType(EntityTypeKeys.MANNEQUIN), Mannequin.class);
    }

    @Override
    protected MannequinElement createInstance(Mannequin entity) {
        return new MannequinElement(eas, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(MannequinPropertyTypes.DESCRIPTION, Optional.empty());
        properties.put(MannequinPropertyTypes.IMMOVABLE, true);
    }

    @Override
    public boolean isSpawnedAtEyeHeight() {
        return false;
    }
}
