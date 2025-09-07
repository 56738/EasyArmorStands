package me.m56738.easyarmorstands.fancyholograms.property;

import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperLocationAdapter;
import org.jetbrains.annotations.NotNull;

public class HologramLocationProperty extends HologramProperty<Location> {
    private final PaperPlatform platform;

    public HologramLocationProperty(PaperPlatform platform, Hologram hologram) {
        super(hologram);
        this.platform = platform;
    }

    @Override
    public @NotNull PropertyType<Location> getType() {
        return EntityPropertyTypes.LOCATION;
    }

    @Override
    public @NotNull Location getValue() {
        return PaperLocationAdapter.fromNative(platform, hologram.getData().getLocation());
    }

    @Override
    public boolean setValue(@NotNull Location value) {
        hologram.getData().setLocation(PaperLocationAdapter.toNative(value));
        hologram.refreshForViewersInWorld();
        return true;
    }
}
