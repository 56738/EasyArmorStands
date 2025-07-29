package me.m56738.easyarmorstands.fancyholograms.property.display;

import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.platform.entity.display.Billboard;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public class DisplayHologramBillboardProperty extends HologramProperty<Billboard> {
    private final DisplayHologramData data;

    public DisplayHologramBillboardProperty(Hologram hologram, DisplayHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<Billboard> getType() {
        return DisplayPropertyTypes.BILLBOARD;
    }

    @Override
    public @NotNull Billboard getValue() {
        return switch (data.getBillboard()) {
            case FIXED -> Billboard.FIXED;
            case VERTICAL -> Billboard.VERTICAL;
            case HORIZONTAL -> Billboard.HORIZONTAL;
            case CENTER -> Billboard.CENTER;
        };
    }

    @Override
    public boolean setValue(@NotNull Billboard value) {
        data.setBillboard(switch (value) {
            case FIXED -> Display.Billboard.FIXED;
            case VERTICAL -> Display.Billboard.VERTICAL;
            case HORIZONTAL -> Display.Billboard.HORIZONTAL;
            case CENTER -> Display.Billboard.CENTER;
        });
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
