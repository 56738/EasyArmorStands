package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperLocationAdapter;

public interface PaperEntity extends Entity {
    org.bukkit.entity.Entity getNative();

    @Override
    default Location getLocation() {
        return PaperLocationAdapter.fromNative(getNative().getLocation());
    }
}
