package me.m56738.easyarmorstands.paper.property.mannequin;

import me.m56738.easyarmorstands.api.Hand;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.MainHand;

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
        return Hand.valueOf(entity.getMainHand().name());
    }

    @Override
    public boolean setValue(Hand value) {
        entity.setMainHand(MainHand.valueOf(value.name()));
        return true;
    }
}
