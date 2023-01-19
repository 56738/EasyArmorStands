package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.platform.EasWorld;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityGlowSetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityHider;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityPersistenceSetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntitySpawner;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BukkitPlatform implements EasPlatform, Listener {
    private final Plugin plugin;
    private final BukkitAudiences adventure;
    private final Map<Player, BukkitPlayer> players = new HashMap<>();
    private final EntityGlowSetter entityGlowSetter;
    private final EntityHider entityHider;
    private final EntityPersistenceSetter entityPersistenceSetter;
    private final EntitySpawner entitySpawner;

    public BukkitPlatform(Plugin plugin, EntityGlowSetter entityGlowSetter, EntityHider entityHider, EntityPersistenceSetter entityPersistenceSetter, EntitySpawner entitySpawner) {
        this.plugin = plugin;
        this.adventure = BukkitAudiences.create(plugin);
        this.entityGlowSetter = entityGlowSetter;
        this.entityHider = entityHider;
        this.entityPersistenceSetter = entityPersistenceSetter;
        this.entitySpawner = entitySpawner;
    }

    public <T extends Entity> BukkitEntity<T> getEntity(T entity) {
        BukkitEntity<T> wrapper = new BukkitEntity<>(this, entity);
        wrapper.update();
        return wrapper;
    }

    public BukkitArmorStand getArmorStand(ArmorStand armorStand) {
        BukkitArmorStand wrapper = new BukkitArmorStand(this, armorStand);
        wrapper.update();
        return wrapper;
    }

    public BukkitPlayer getPlayer(Player player) {
        BukkitPlayer wrapper = new BukkitPlayer(this, player, adventure.player(player));
        wrapper.update();
        return wrapper;
    }

    public EasWorld getWorld(World world) {
        return new BukkitWorld(this, world);
    }

    @Override
    public boolean canHideEntities() {
        return entityHider != null;
    }

    @Override
    public boolean canSetEntityPersistence() {
        return entityPersistenceSetter != null;
    }

    @Override
    public boolean canSetEntityGlowing() {
        return entityGlowSetter != null;
    }

    @Override
    public Collection<? extends EasPlayer> getPlayers() {
        return players.values();
    }

    @Override
    public void registerListener(EasListener listener) {
        plugin.getServer().getPluginManager().registerEvents(new BukkitListener(this, listener), plugin);
    }

    @Override
    public void registerTickTask(Runnable task) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, task, 0, 1);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        players.put(player, getPlayer(player));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        players.remove(event.getPlayer());
    }

    public Plugin plugin() {
        return plugin;
    }

    public EntityGlowSetter entityGlowSetter() {
        return entityGlowSetter;
    }

    public EntityHider entityHider() {
        return entityHider;
    }

    public EntityPersistenceSetter entityPersistenceSetter() {
        return entityPersistenceSetter;
    }

    public EntitySpawner entitySpawner() {
        return entitySpawner;
    }
}
