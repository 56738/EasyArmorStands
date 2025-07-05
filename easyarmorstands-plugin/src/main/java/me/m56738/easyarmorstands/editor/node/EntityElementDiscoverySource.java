package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class EntityElementDiscoverySource implements ElementDiscoverySource {
    private final EntityElementProviderRegistryImpl providerRegistry;

    public EntityElementDiscoverySource() {
        this(EasyArmorStandsPlugin.getInstance().entityElementProviderRegistry());
    }

    public EntityElementDiscoverySource(@NotNull EntityElementProviderRegistryImpl providerRegistry) {
        this.providerRegistry = providerRegistry;
    }

    public @NotNull ElementDiscoveryEntry getEntry(@NotNull Entity entity) {
        return new EntityElementDiscoveryEntry(this, entity);
    }

    public @Nullable Element getElement(@NotNull Entity entity) {
        return providerRegistry.getElement(entity);
    }

    @Override
    public void discover(@NotNull World world, @NotNull BoundingBox box, @NotNull Consumer<@NotNull ElementDiscoveryEntry> consumer) {
        Vector3dc delta = box.getMaxPosition().sub(box.getMinPosition(), new Vector3d()).div(2);
        Vector3dc center = box.getMinPosition().add(delta, new Vector3d());
        Location location = new Location(world, center.x(), center.y(), center.z());
        for (Entity entity : world.getNearbyEntities(location, delta.x(), delta.y(), delta.z())) {
            consumer.accept(getEntry(entity));
        }
    }
}
