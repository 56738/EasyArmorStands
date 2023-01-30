package me.m56738.easyarmorstands.bukkit.platform;

import cloud.commandframework.CommandManager;
import me.m56738.easyarmorstands.bukkit.event.ArmorStandPreSpawnEvent;
import me.m56738.easyarmorstands.bukkit.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.bukkit.event.SessionMenuInitializeEvent;
import me.m56738.easyarmorstands.bukkit.event.SessionMoveEvent;
import me.m56738.easyarmorstands.bukkit.event.SessionStartEvent;
import me.m56738.easyarmorstands.bukkit.feature.ArmorStandCanTickAccessor;
import me.m56738.easyarmorstands.bukkit.feature.ArmorStandLockAccessor;
import me.m56738.easyarmorstands.bukkit.feature.EntityGlowSetter;
import me.m56738.easyarmorstands.bukkit.feature.EntityHider;
import me.m56738.easyarmorstands.bukkit.feature.EntityInvulnerableAccessor;
import me.m56738.easyarmorstands.bukkit.feature.EntityNameAccessor;
import me.m56738.easyarmorstands.bukkit.feature.EntityPersistenceSetter;
import me.m56738.easyarmorstands.bukkit.feature.EntitySpawner;
import me.m56738.easyarmorstands.bukkit.feature.EquipmentAccessor;
import me.m56738.easyarmorstands.bukkit.feature.ItemProvider;
import me.m56738.easyarmorstands.bukkit.feature.ParticleSpawner;
import me.m56738.easyarmorstands.bukkit.feature.ToolChecker;
import me.m56738.easyarmorstands.core.inventory.SessionMenu;
import me.m56738.easyarmorstands.core.platform.EasArmorEntity;
import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.platform.EasCommandSender;
import me.m56738.easyarmorstands.core.platform.EasFeature;
import me.m56738.easyarmorstands.core.platform.EasInventory;
import me.m56738.easyarmorstands.core.platform.EasInventoryListener;
import me.m56738.easyarmorstands.core.platform.EasItem;
import me.m56738.easyarmorstands.core.platform.EasListener;
import me.m56738.easyarmorstands.core.platform.EasMaterial;
import me.m56738.easyarmorstands.core.platform.EasPlatform;
import me.m56738.easyarmorstands.core.platform.EasPlayer;
import me.m56738.easyarmorstands.core.platform.EasWorld;
import me.m56738.easyarmorstands.core.session.Session;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.joml.Vector3dc;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitPlatform implements EasPlatform, Listener, Closeable {
    private final Plugin plugin;
    private final BukkitAudiences adventure;
    private final CommandManager<EasCommandSender> commandManager;
    private final Map<Player, BukkitPlayer> players = new HashMap<>();
    private final List<BukkitListener> listeners = new ArrayList<>();
    private final EntityGlowSetter entityGlowSetter;
    private final EntityHider entityHider;
    private final EntityPersistenceSetter entityPersistenceSetter;
    private final EntitySpawner entitySpawner;
    private final ToolChecker toolChecker;
    private final ParticleSpawner particleSpawner;
    private final EquipmentAccessor equipmentAccessor;
    private final EntityNameAccessor entityNameAccessor;
    private final ArmorStandCanTickAccessor armorStandCanTickAccessor;
    private final ArmorStandLockAccessor armorStandLockAccessor;
    private final EntityInvulnerableAccessor entityInvulnerableAccessor;
    private final ItemProvider itemProvider;

    public BukkitPlatform(Plugin plugin,
                          CommandManager<EasCommandSender> commandManager,
                          EntityGlowSetter entityGlowSetter,
                          EntityHider entityHider,
                          EntityPersistenceSetter entityPersistenceSetter,
                          EntitySpawner entitySpawner,
                          ToolChecker toolChecker,
                          ParticleSpawner particleSpawner,
                          EquipmentAccessor equipmentAccessor,
                          EntityNameAccessor entityNameAccessor,
                          ArmorStandCanTickAccessor armorStandCanTickAccessor,
                          ArmorStandLockAccessor armorStandLockAccessor,
                          EntityInvulnerableAccessor entityInvulnerableAccessor,
                          ItemProvider itemProvider) {
        this.plugin = plugin;
        this.adventure = BukkitAudiences.create(plugin);
        this.commandManager = commandManager;
        this.entityGlowSetter = entityGlowSetter;
        this.entityHider = entityHider;
        this.entityPersistenceSetter = entityPersistenceSetter;
        this.entitySpawner = entitySpawner;
        this.toolChecker = toolChecker;
        this.particleSpawner = particleSpawner;
        this.equipmentAccessor = equipmentAccessor;
        this.entityNameAccessor = entityNameAccessor;
        this.armorStandCanTickAccessor = armorStandCanTickAccessor;
        this.armorStandLockAccessor = armorStandLockAccessor;
        this.entityInvulnerableAccessor = entityInvulnerableAccessor;
        this.itemProvider = itemProvider;

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
    public boolean hasFeature(EasFeature feature) {
        switch (feature) {
            case ENTITY_GLOW:
                return entityGlowSetter != null;
            case ENTITY_INVULNERABLE:
                return entityInvulnerableAccessor != null;
            case ARMOR_STAND_CAN_TICK:
                return armorStandCanTickAccessor != null;
            case ARMOR_STAND_LOCK:
                return armorStandLockAccessor != null;
            default:
                return false;
        }
    }

    @Override
    public boolean hasSlot(EasArmorEntity.Slot slot) {
        return equipmentAccessor.hasSlot(slot);
    }

    @Override
    public Collection<? extends EasPlayer> getPlayers() {
        return players.values();
    }

    @Override
    public EasInventory createInventory(Component title, int width, int height, EasInventoryListener listener) {
        if (width != 9) {
            throw new IllegalArgumentException("Invalid width");
        }
        if (height < 1 || height > 6) {
            throw new IllegalArgumentException("Invalid height");
        }
        BukkitInventoryHolder holder = new BukkitInventoryHolder(width * height,
                LegacyComponentSerializer.legacySection().serialize(title),
                listener);
        return new BukkitInventory(this, holder.getInventory());
    }

    @Override
    public EasItem createItem(EasMaterial material, Component name, List<Component> lore) {
        ItemStack item = itemProvider.createItem(material);
        ItemMeta meta = item.getItemMeta();
        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();
        List<String> legacyLore = new ArrayList<>(lore.size());
        for (Component component : lore) {
            legacyLore.add(serializer.serialize(component));
        }
        meta.setLore(legacyLore);
        if (name == Component.empty()) {
            meta.setDisplayName(ChatColor.RESET.toString());
        } else {
            meta.setDisplayName(serializer.serialize(name));
        }
        item.setItemMeta(meta);
        return getItem(item);
    }

    @Override
    public void registerListener(EasListener listener) {
        BukkitListener bukkitListener = new BukkitListener(this, listener);
        listeners.add(bukkitListener);
        plugin.getServer().getPluginManager().registerEvents(bukkitListener, plugin);
    }

    @Override
    public void registerTickTask(Runnable task) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, task, 0, 1);
    }

    @Override
    public boolean canStartSession(EasPlayer player, EasArmorStand armorStand) {
        SessionStartEvent event = new SessionStartEvent(
                ((BukkitPlayer) player).get(),
                ((BukkitArmorStand) armorStand).get());
        plugin.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean canSpawnArmorStand(EasPlayer player, Vector3dc position) {
        Player p = ((BukkitPlayer) player).get();
        ArmorStandPreSpawnEvent event = new ArmorStandPreSpawnEvent(
                p, new Location(p.getWorld(), position.x(), position.y(), position.z()));
        plugin.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean canMoveSession(Session session, Vector3dc position) {
        SessionMoveEvent event = new SessionMoveEvent(session, position);
        plugin.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public void onSessionStarted(Session session) {
        SessionInitializeEvent event = new SessionInitializeEvent(
                ((BukkitPlayer) session.getPlayer()).get(),
                session);
        plugin.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onInventoryInitialize(SessionMenu menu) {
        SessionMenuInitializeEvent event = new SessionMenuInitializeEvent(menu);
        plugin.getServer().getPluginManager().callEvent(event);
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

    public List<BukkitListener> listeners() {
        return Collections.unmodifiableList(listeners);
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

    public ParticleSpawner particleSpawner() {
        return particleSpawner;
    }

    public EquipmentAccessor equipmentAccessor() {
        return equipmentAccessor;
    }

    public EntityNameAccessor entityNameAccessor() {
        return entityNameAccessor;
    }

    public ArmorStandCanTickAccessor armorStandCanTickAccessor() {
        return armorStandCanTickAccessor;
    }

    public ArmorStandLockAccessor armorStandLockAccessor() {
        return armorStandLockAccessor;
    }

    public EntityInvulnerableAccessor entityInvulnerableAccessor() {
        return entityInvulnerableAccessor;
    }

    @Override
    public void close() {
        adventure.close();
    }
}
