package me.m56738.easyarmorstands.modded.property.mannequin.part;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.entity.player.PlayerModelPart;

public abstract class AbstractMannequinSkinPartVisibleProperty extends EntityProperty<Mannequin, Boolean> {
    private final PropertyType<Boolean> type;
    private final PlayerModelPart part;

    public AbstractMannequinSkinPartVisibleProperty(Mannequin entity, PlayerModelPart part, PropertyType<Boolean> type) {
        super(entity);
        this.type = type;
        this.part = part;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return type;
    }

    @Override
    public Boolean getValue() {
        return entity.isModelPartShown(part);
    }

    @Override
    public boolean setValue(Boolean value) {
        byte flags = entity.getEntityData().get(Mannequin.DATA_PLAYER_MODE_CUSTOMISATION);
        if (value) {
            flags |= (byte) part.getMask();
        } else {
            flags &= (byte) ~part.getMask();
        }
        entity.getEntityData().set(Mannequin.DATA_PLAYER_MODE_CUSTOMISATION, flags);
        return true;
    }
}
