package me.m56738.easyarmorstands.editor.layer;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class EntityElementDiscoverySource implements ElementDiscoverySource {
    private final EasyArmorStandsCommon eas;
    private final Player player;

    public EntityElementDiscoverySource(EasyArmorStandsCommon eas, @NotNull Player player) {
        this.eas = eas;
        this.player = player;
    }

    public @NotNull ElementDiscoveryEntry getEntry(@NotNull Entity entity) {
        return new EntityElementDiscoveryEntry(this, player, entity);
    }

    public @Nullable Element getElement(@NotNull Entity entity) {
        return eas.getElement(entity);
    }

    @Override
    public void discover(@NotNull World world, @NotNull BoundingBox box, @NotNull Consumer<@NotNull ElementDiscoveryEntry> consumer) {
        Vector3dc delta = box.getMaxPosition().sub(box.getMinPosition(), new Vector3d()).div(2);
        Vector3dc center = box.getMinPosition().add(delta, new Vector3d());
        Location location = Location.of(world, center);
        for (Entity entity : world.getNearbyEntities(location, delta.x(), delta.y(), delta.z())) {
            if (entity.equals(player)) {
                continue;
            }
            consumer.accept(getEntry(entity));
        }
    }
}
