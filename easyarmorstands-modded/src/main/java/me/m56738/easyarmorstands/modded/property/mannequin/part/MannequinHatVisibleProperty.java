package me.m56738.easyarmorstands.modded.property.mannequin.part;

import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.entity.player.PlayerModelPart;

public class MannequinHatVisibleProperty extends AbstractMannequinSkinPartVisibleProperty {
    public MannequinHatVisibleProperty(Mannequin entity) {
        super(entity, PlayerModelPart.HAT, MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.HAT));
    }
}
