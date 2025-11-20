package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot.property;

import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.entity.Mannequin;
import org.bukkit.entity.model.PlayerModelPart;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class MannequinSkinPartVisibleProperty implements Property<Boolean> {
    private final Mannequin mannequin;
    private final PlayerModelPart part;
    private final PropertyType<Boolean> type;

    public MannequinSkinPartVisibleProperty(Mannequin mannequin, PlayerModelPart part, SkinPart skinPart) {
        this.mannequin = mannequin;
        this.part = part;
        this.type = MannequinPropertyTypes.SKIN_PART_VISIBLE.get(skinPart);
    }

    public static boolean isSupported() {
        return ReflectionUtil.hasClass("PlayerModelPart");
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return type;
    }

    @Override
    public @NotNull Boolean getValue() {
        return mannequin.isModelPartShown(part);
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        mannequin.setModelPartShown(part, value);
        return true;
    }
}
