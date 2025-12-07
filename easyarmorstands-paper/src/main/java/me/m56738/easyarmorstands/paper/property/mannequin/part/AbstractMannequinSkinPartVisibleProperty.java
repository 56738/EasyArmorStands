package me.m56738.easyarmorstands.paper.property.mannequin.part;

import com.destroystokyo.paper.SkinParts;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Mannequin;

public abstract class AbstractMannequinSkinPartVisibleProperty extends EntityProperty<Mannequin, Boolean> {
    private final PropertyType<Boolean> type;

    public AbstractMannequinSkinPartVisibleProperty(Mannequin entity, PropertyType<Boolean> type) {
        super(entity);
        this.type = type;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return type;
    }

    @Override
    public Boolean getValue() {
        return getValue(entity.getSkinParts());
    }

    @Override
    public boolean setValue(Boolean value) {
        SkinParts.Mutable parts = entity.getSkinParts();
        setValue(parts, value);
        entity.setSkinParts(parts);
        return true;
    }

    protected abstract boolean getValue(SkinParts parts);

    protected abstract void setValue(SkinParts.Mutable parts, boolean value);
}
