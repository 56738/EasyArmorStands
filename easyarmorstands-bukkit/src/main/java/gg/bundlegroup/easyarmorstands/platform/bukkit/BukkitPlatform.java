package gg.bundlegroup.easyarmorstands.platform.bukkit;

import cloud.commandframework.CommandManager;
import gg.bundlegroup.easyarmorstands.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.platform.EasWorld;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityGlowSetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityHider;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityPersistenceSetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntitySpawner;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EquipmentAccessor;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.HeldItemGetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ParticleSpawner;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ToolChecker;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BukkitPlatform implements EasPlatform, Listener {
    private final Plugin plugin;
    private final BukkitAudiences adventure;
    private final CommandManager<EasCommandSender> commandManager;
    private final Map<Player, BukkitPlayer> players = new HashMap<>();
    private final EntityGlowSetter entityGlowSetter;
    private final EntityHider entityHider;
    private final EntityPersistenceSetter entityPersistenceSetter;
    private final EntitySpawner entitySpawner;
    private final ToolChecker toolChecker;
    private final HeldItemGetter heldItemGetter;
    private final ParticleSpawner particleSpawner;
    private final EquipmentAccessor equipmentAccessor;

    public BukkitPlatform(Plugin plugin, CommandManager<EasCommandSender> commandManager, EntityGlowSetter entityGlowSetter, EntityHider entityHider, EntityPersistenceSetter entityPersistenceSetter, EntitySpawner entitySpawner, ToolChecker toolChecker, HeldItemGetter heldItemGetter, ParticleSpawner particleSpawner, EquipmentAccessor equipmentAccessor) {
        this.plugin = plugin;
        this.adventure = BukkitAudiences.create(plugin);
        this.commandManager = commandManager;
        this.entityGlowSetter = entityGlowSetter;
        this.entityHider = entityHider;
        this.entityPersistenceSetter = entityPersistenceSetter;
        this.entitySpawner = entitySpawner;
        this.toolChecker = toolChecker;
        this.heldItemGetter = heldItemGetter;
        this.particleSpawner = particleSpawner;
        this.equipmentAccessor = equipmentAccessor;

        for (Player player : Bukkit.getOnlinePlayers()) {
            players.put(player, getPlayer(player));
        }
    }

    public <T extends Entity> BukkitEntity<T> getEntity(T entity) {
        BukkitEntity<T> wrapper = new BukkitEntity<>(this, entity);
        wrapper.update();
        return wrapper;
    }

    public EasCommandSender getCommandSender(CommandSender sender) {
        if (sender instanceof Player) {
            return getPlayer((Player) sender);
        }
        return new BukkitCommandSender(this, sender, adventure.sender(sender));
    }

    public BukkitItem getItem(ItemStack item) {
        return new BukkitItem(this, item);
    }

    public BukkitArmorStand getArmorStand(ArmorStand armorStand) {
        BukkitArmorStand wrapper = new BukkitArmorStand(this, armorStand);
        wrapper.update();
        return wrapper;
    }

    public BukkitPlayer getPlayer(Player player) {
        BukkitPlayer wrapper = players.get(player);
        if (wrapper == null) {
            wrapper = new BukkitPlayer(this, player, adventure.player(player));
        }
        wrapper.update();
        return wrapper;
    }

    public EasWorld getWorld(World world) {
        return new BukkitWorld(this, world);
    }

    @Override
    public CommandManager<EasCommandSender> commandManager() {
        return commandManager;
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
    public boolean canSpawnParticles() {
        return particleSpawner != null;
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

    public ToolChecker toolChecker() {
        return toolChecker;
    }

    public HeldItemGetter heldItemGetter() {
        return heldItemGetter;
    }

    public ParticleSpawner particleSpawner() {
        return particleSpawner;
    }

    public EquipmentAccessor equipmentAccessor() {
        return equipmentAccessor;
    }
}
