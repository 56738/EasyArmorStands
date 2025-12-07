package me.m56738.easyarmorstands.modded.property.mannequin.part;

import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.entity.player.PlayerModelPart;

public class MannequinLeftSleeveVisibleProperty extends AbstractMannequinSkinPartVisibleProperty {
    public MannequinLeftSleeveVisibleProperty(Mannequin entity) {
        super(entity, PlayerModelPart.LEFT_SLEEVE, MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.LEFT_SLEEVE));
    }
}
