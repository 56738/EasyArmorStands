package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.EntitySnapshot;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import me.m56738.easyarmorstands.platform.util.Location;
import org.jspecify.annotations.Nullable;

public interface PaperEntitySnapshot extends EntitySnapshot {
    static PaperEntitySnapshot fromNative(org.bukkit.entity.EntitySnapshot snapshot) {
        return new PaperEntitySnapshotImpl(snapshot);
    }

    static @Nullable PaperEntitySnapshot ofNullable(org.bukkit.entity.@Nullable EntitySnapshot snapshot) {
        if (snapshot == null) {
            return null;
        }
        return PaperEntitySnapshot.fromNative(snapshot);
    }

    org.bukkit.entity.EntitySnapshot getNative();

    static org.bukkit.entity.EntitySnapshot toNative(EntitySnapshot snapshot) {
        return ((PaperEntitySnapshot) snapshot).getNative();
    }

    @Override
    default PaperEntity createEntity(Location location) {
        return PaperEntity.fromNative(getNative().createEntity(PaperAdapter.toNative(location)));
    }
}
