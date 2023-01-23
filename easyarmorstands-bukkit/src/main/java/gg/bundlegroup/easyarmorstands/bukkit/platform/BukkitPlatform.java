package gg.bundlegroup.easyarmorstands.bukkit.platform;

import cloud.commandframework.CommandManager;
import gg.bundlegroup.easyarmorstands.bukkit.event.SessionInitializeEvent;
import gg.bundlegroup.easyarmorstands.bukkit.event.SessionMenuInitializeEvent;
import gg.bundlegroup.easyarmorstands.bukkit.event.SessionStartEvent;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ArmorStandCanTickAccessor;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityGlowSetter;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityHider;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityNameAccessor;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityPersistenceSetter;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntitySpawner;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EquipmentAccessor;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ItemProvider;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ParticleSpawner;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ToolChecker;
import gg.bundlegroup.easyarmorstands.common.inventory.SessionMenu;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.common.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.common.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.common.platform.EasInventoryListener;
import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import gg.bundlegroup.easyarmorstands.common.platform.EasListener;
import gg.bundlegroup.easyarmorstands.common.platform.EasMaterial;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.platform.EasWorld;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    private final ParticleSpawner particleSpawner;
    private final EquipmentAccessor equipmentAccessor;
    private final EntityNameAccessor entityNameAccessor;
    private final ArmorStandCanTickAccessor armorStandCanTickAccessor;
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
            case ARMOR_STAND_CAN_TICK:
                return armorStandCanTickAccessor != null;
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
        plugin.getServer().getPluginManager().registerEvents(new BukkitListener(this, listener), plugin);
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
}
