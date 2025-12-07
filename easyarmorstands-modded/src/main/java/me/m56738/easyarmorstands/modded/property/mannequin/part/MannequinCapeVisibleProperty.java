package me.m56738.easyarmorstands.modded.property.mannequin.part;

import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.entity.player.PlayerModelPart;

public class MannequinCapeVisibleProperty extends AbstractMannequinSkinPartVisibleProperty {
    public MannequinCapeVisibleProperty(Mannequin entity) {
        super(entity, PlayerModelPart.CAPE, MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.CAPE));
    }
}
