package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.particle.Particle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public abstract class ParticleImpl<T extends Entity> implements Particle {
    protected final Set<Player> players = new HashSet<>();
    private final Class<T> type;
    private final World world;
    protected T entity;
    private boolean dirty;

    public ParticleImpl(Class<T> type, World world) {
        this.type = type;
        this.world = world;
    }

    @SuppressWarnings("UnstableApiUsage")
    protected void configure(T entity) {
        entity.setPersistent(false);
        entity.setVisibleByDefault(false);
        entity.setMetadata("easyarmorstands_ignore", new FixedMetadataValue(EasyArmorStandsPlugin.getInstance(), true));
        entity.setMetadata("easyarmorstands_force", new FixedMetadataValue(EasyArmorStandsPlugin.getInstance(), true));
        entity.setGlowing(true);
    }

    protected void update(T entity) {
    }

    protected abstract Location getLocation();

    protected void markDirty() {
        dirty = true;
    }

    private void create() {
        entity = world.spawn(getLocation(), type, e -> {
            configure(e);
            update(e);
        });
        dirty = false;
    }

    @Override
    public void update() {
        if (dirty && entity != null) {
            dirty = false;
            update(entity);
            entity.teleport(getLocation());
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void show(@NotNull Player player) {
        boolean added = players.add(player);
        if (added) {
            if (players.size() == 1) {
                create();
            }
            player.showEntity(EasyArmorStandsPlugin.getInstance(), entity);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void hide(@NotNull Player player) {
        boolean removed = players.remove(player);
        if (removed) {
            if (EasyArmorStandsPlugin.getInstance().isEnabled()) {
                player.hideEntity(EasyArmorStandsPlugin.getInstance(), entity);
            }
            if (players.isEmpty()) {
                entity.remove();
                entity = null;
            }
        }
    }
}
