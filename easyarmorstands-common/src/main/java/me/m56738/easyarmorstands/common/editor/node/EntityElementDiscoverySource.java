package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.platform.world.World;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.common.element.CommonEntityElementProviderRegistry;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class EntityElementDiscoverySource implements ElementDiscoverySource {
    private final CommonPlatform platform;
    private final CommonEntityElementProviderRegistry providerRegistry;
    private final Player player;

    public EntityElementDiscoverySource(CommonPlatform platform, CommonEntityElementProviderRegistry providerRegistry, Player player) {
        this.platform = platform;
        this.providerRegistry = providerRegistry;
        this.player = player;
    }

    public ElementDiscoveryEntry getEntry(Entity entity) {
        return new EntityElementDiscoveryEntry(this, player, entity);
    }

    public @Nullable Element getElement(Entity entity) {
        return providerRegistry.getElement(entity);
    }

    @Override
    public void discover(World world, BoundingBox box, Consumer<ElementDiscoveryEntry> consumer) {
        Vector3dc delta = box.getMaxPosition().sub(box.getMinPosition(), new Vector3d()).div(2);
        Vector3dc center = box.getMinPosition().add(delta, new Vector3d());
        Location location = Location.of(world, center);
        for (Entity entity : platform.getNearbyEntities(location, delta.x(), delta.y(), delta.z())) {
            consumer.accept(getEntry(entity));
        }
    }
}
