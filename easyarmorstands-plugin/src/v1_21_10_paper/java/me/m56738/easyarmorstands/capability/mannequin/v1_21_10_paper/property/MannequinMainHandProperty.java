package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property;

import me.m56738.easyarmorstands.api.Hand;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.MainHand;
import org.jetbrains.annotations.NotNull;

public class MannequinMainHandProperty implements Property<Hand> {
    private final Mannequin entity;

    public MannequinMainHandProperty(Mannequin entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Hand> getType() {
        return MannequinPropertyTypes.MAIN_HAND;
    }

    @Override
    public @NotNull Hand getValue() {
        return Hand.valueOf(entity.getMainHand().name());
    }

    @Override
    public boolean setValue(@NotNull Hand value) {
        entity.setMainHand(MainHand.valueOf(value.name()));
        return true;
    }
}
