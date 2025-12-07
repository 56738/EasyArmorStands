package me.m56738.easyarmorstands.modded.property.mannequin;

import me.m56738.easyarmorstands.api.Hand;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.decoration.Mannequin;

public class MannequinMainHandProperty extends EntityProperty<Mannequin, Hand> {
    public MannequinMainHandProperty(Mannequin entity) {
        super(entity);
    }

    @Override
    public PropertyType<Hand> getType() {
        return MannequinPropertyTypes.MAIN_HAND;
    }

    @Override
    public Hand getValue() {
        return Hand.valueOf(entity.getMainArm().name());
    }

    @Override
    public boolean setValue(Hand value) {
        entity.setMainArm(HumanoidArm.valueOf(value.name()));
        return true;
    }
}
