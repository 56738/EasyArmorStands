package me.m56738.easyarmorstands.fancyholograms.property.display;

import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public class DisplayHologramBillboardProperty extends HologramProperty<Display.Billboard> {
    private final DisplayHologramData data;

    public DisplayHologramBillboardProperty(Hologram hologram, DisplayHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<Display.Billboard> getType() {
        return DisplayPropertyTypes.BILLBOARD;
    }

    @Override
    public @NotNull Display.Billboard getValue() {
        return data.getBillboard();
    }

    @Override
    public boolean setValue(@NotNull Display.Billboard value) {
        data.setBillboard(value);
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
