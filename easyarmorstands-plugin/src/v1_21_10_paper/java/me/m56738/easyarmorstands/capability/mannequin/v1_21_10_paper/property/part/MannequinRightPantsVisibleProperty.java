package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.part;

import com.destroystokyo.paper.SkinParts;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import org.bukkit.entity.Mannequin;

public class MannequinRightPantsVisibleProperty extends AbstractMannequinSkinPartVisibleProperty {
    public MannequinRightPantsVisibleProperty(Mannequin mannequin) {
        super(mannequin, MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.RIGHT_PANTS));
    }

    @Override
    protected boolean getValue(SkinParts parts) {
        return parts.hasRightPantsEnabled();
    }

    @Override
    protected void setValue(SkinParts.Mutable parts, boolean value) {
        parts.setRightPantsEnabled(value);
    }
}
