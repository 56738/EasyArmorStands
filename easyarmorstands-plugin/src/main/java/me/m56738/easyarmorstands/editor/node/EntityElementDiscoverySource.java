package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

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
    public void discover(@NotNull EyeRay eyeRay, @NotNull Consumer<@NotNull ElementDiscoveryEntry> consumer) {
        Vector3dc position = eyeRay.origin();
        double range = eyeRay.length();
        Location location = new Location(eyeRay.world(), position.x(), position.y(), position.z());
        for (Entity entity : location.getWorld().getNearbyEntities(location, range, range, range)) {
            if (eyeRay.isInRange(entity.getLocation())) {
                consumer.accept(getEntry(entity));
            }
        }
    }
}
