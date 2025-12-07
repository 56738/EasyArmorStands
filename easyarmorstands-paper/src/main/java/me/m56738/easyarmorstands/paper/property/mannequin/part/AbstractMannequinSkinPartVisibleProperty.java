package me.m56738.easyarmorstands.paper.property.mannequin.part;

import com.destroystokyo.paper.SkinParts;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Mannequin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMannequinSkinPartVisibleProperty implements Property<Boolean> {
    private final Mannequin mannequin;
    private final PropertyType<Boolean> type;

    public AbstractMannequinSkinPartVisibleProperty(Mannequin mannequin, PropertyType<Boolean> type) {
        this.mannequin = mannequin;
        this.type = type;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return type;
    }

    @Override
    public @NotNull Boolean getValue() {
        return getValue(mannequin.getSkinParts());
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        SkinParts.Mutable parts = mannequin.getSkinParts();
        setValue(parts, value);
        mannequin.setSkinParts(parts);
        return true;
    }

    @Override
    public boolean isValid() {
        return mannequin.isValid();
    }

    protected abstract boolean getValue(SkinParts parts);

    protected abstract void setValue(SkinParts.Mutable parts, boolean value);
}
