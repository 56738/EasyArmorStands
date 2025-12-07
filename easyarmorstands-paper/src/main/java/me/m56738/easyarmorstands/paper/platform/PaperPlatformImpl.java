package me.m56738.easyarmorstands.paper.platform;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.platform.world.World;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.paper.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.paper.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.paper.api.event.session.SessionStopEvent;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperLocationAdapter;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperCommandSender;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntity;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntityType;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import me.m56738.easyarmorstands.paper.api.platform.profile.PaperProfile;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlock;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlockData;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperWorld;
import me.m56738.easyarmorstands.paper.config.PaperConfiguration;
import me.m56738.easyarmorstands.paper.platform.entity.PaperCommandSenderImpl;
import me.m56738.easyarmorstands.paper.platform.entity.PaperEntityImpl;
import me.m56738.easyarmorstands.paper.platform.entity.PaperEntityTypeImpl;
import me.m56738.easyarmorstands.paper.platform.entity.PaperPlayerImpl;
import me.m56738.easyarmorstands.paper.platform.inventory.PaperItemImpl;
import me.m56738.easyarmorstands.paper.platform.profile.PaperProfileImpl;
import me.m56738.easyarmorstands.paper.platform.world.PaperBlockDataImpl;
import me.m56738.easyarmorstands.paper.platform.world.PaperBlockImpl;
import me.m56738.easyarmorstands.paper.platform.world.PaperWorldImpl;
import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PaperPlatformImpl implements PaperPlatform, CommonPlatform {
    private static final NamespacedKey TOOL_KEY = new NamespacedKey("easyarmorstands", "tool");
    private final Plugin plugin;
    private final BukkitGizmos gizmos;
    private final Configuration configuration;

    public PaperPlatformImpl(Plugin plugin) {
        this.plugin = plugin;
        this.gizmos = BukkitGizmos.create(plugin);
        this.configuration = new PaperConfiguration();
    }

    @Override
    public Item createTool() {
        ItemStack item = ItemStack.of(Material.BLAZE_ROD);
        item.editMeta(meta -> meta.getPersistentDataContainer().set(TOOL_KEY, PersistentDataType.BYTE, (byte) 1));
        return getItem(item);
    }

    @Override
    public boolean isTool(Item item) {
        ItemStack itemStack = PaperItem.toNative(item);
        ItemMeta meta = itemStack.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(TOOL_KEY);
    }

    @Override
    public @Nullable Entity getEntity(UUID uniqueId, @Nullable Location expectedLocation) {
        return null;
    }

    @Override
    public Entity spawnEntity(EntityType type, Location location, Consumer<Entity> config) {
        org.bukkit.Location nativeLocation = PaperLocationAdapter.toNative(location);
        SpawnedEntityConfigurator nativeConfig = new SpawnedEntityConfigurator(config);
        org.bukkit.entity.Entity nativeEntity = nativeLocation.getWorld()
                .spawnEntity(nativeLocation, PaperEntityType.toNative(type), SpawnReason.CUSTOM, nativeConfig);
        if (nativeConfig.entity != null) {
            return nativeConfig.entity;
        } else {
            nativeEntity.remove();
            throw new NullPointerException();
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public GizmoFactory getGizmoFactory(Player player) {
        return gizmos.player(PaperPlayer.toNative(player));
    }

    @Override
    public void close() {
        gizmos.close();
    }

    @Override
    public BlockData createBlockData(String input) {
        return getBlockData(Bukkit.createBlockData(input));
    }

    @Override
    public boolean isIgnored(Entity entity) {
        org.bukkit.entity.Entity nativeEntity = PaperEntity.toNative(entity);
        return nativeEntity.hasMetadata("easyarmorstands_ignore")
                || nativeEntity.hasMetadata("gizmo");
    }

    @Override
    public Collection<Entity> getNearbyEntities(Location location, double deltaX, double deltaY, double deltaZ) {
        org.bukkit.Location nativeLocation = PaperLocationAdapter.toNative(location);
        return nativeLocation.getNearbyEntities(deltaX, deltaY, deltaZ).stream()
                .map(this::getEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Entity> getTaggedEntities(World world, String tag) {
        return PaperWorld.toNative(world).getEntities().stream()
                .filter(entity -> entity.getScoreboardTags().contains(tag))
                .map(this::getEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Entity> getAllEntities(World world) {
        return List.of();
    }

    @Override
    public void openSpawnMenu(Player player) {
        // TODO
    }

    @Override
    public boolean canDiscoverElement(Player player, EditableElement element) {
        // TODO
        return true;
    }

    @Override
    public boolean canSelectElement(Player player, EditableElement element) {
        // TODO
        return true;
    }

    @Override
    public boolean canCreateElement(Player player, ElementType type, PropertyContainer properties) {
        // TODO
        return true;
    }

    @Override
    public boolean canDestroyElement(Player player, DestroyableElement element) {
        // TODO
        return true;
    }

    @Override
    public <T> boolean canChangeProperty(Player player, Element element, Property<T> property, T value) {
        // TODO
        return true;
    }

    @Override
    public EntityType getPlayerType() {
        return getEntityType(org.bukkit.entity.EntityType.PLAYER);
    }

    @Override
    public EntityType getArmorStandType() {
        return getEntityType(org.bukkit.entity.EntityType.ARMOR_STAND);
    }

    @Override
    public EntityType getBlockDisplayType() {
        return getEntityType(org.bukkit.entity.EntityType.BLOCK_DISPLAY);
    }

    @Override
    public EntityType getItemDisplayType() {
        return getEntityType(org.bukkit.entity.EntityType.ITEM_DISPLAY);
    }

    @Override
    public EntityType getTextDisplayType() {
        return getEntityType(org.bukkit.entity.EntityType.TEXT_DISPLAY);
    }

    @Override
    public EntityType getInteractionType() {
        return getEntityType(org.bukkit.entity.EntityType.INTERACTION);
    }

    @Override
    public void onStartSession(Session session) {
        plugin.getServer().getPluginManager().callEvent(new SessionStartEvent(session));
    }

    @Override
    public void onStopSession(Session session) {
        plugin.getServer().getPluginManager().callEvent(new SessionStopEvent(session));
    }

    @Override
    public void onElementInitialize(DefaultEntityElement element) {
        plugin.getServer().getPluginManager().callEvent(new EntityElementInitializeEvent(element));
    }

    @Override
    public PaperEntity getEntity(org.bukkit.entity.Entity nativeEntity) {
        return new PaperEntityImpl(this, nativeEntity);
    }

    @Override
    public PaperPlayer getPlayer(org.bukkit.entity.Player nativePlayer) {
        return new PaperPlayerImpl(this, nativePlayer);
    }

    @Override
    public PaperCommandSender getCommandSender(CommandSender nativeCommandSender) {
        return new PaperCommandSenderImpl(this, nativeCommandSender);
    }

    @Override
    public PaperWorld getWorld(org.bukkit.World nativeWorld) {
        return new PaperWorldImpl(this, nativeWorld);
    }

    @Override
    public PaperItem getItem(@Nullable ItemStack nativeItem) {
        return new PaperItemImpl(this, Objects.requireNonNullElseGet(nativeItem, ItemStack::empty));
    }

    @Override
    public PaperEntityType getEntityType(org.bukkit.entity.EntityType nativeType) {
        return new PaperEntityTypeImpl(this, nativeType);
    }

    @Override
    public PaperBlock getBlock(Block nativeBlock) {
        return new PaperBlockImpl(this, nativeBlock);
    }

    @Override
    public PaperBlockData getBlockData(org.bukkit.block.data.BlockData nativeBlockData) {
        return new PaperBlockDataImpl(this, nativeBlockData);
    }

    @Override
    public PaperProfile getProfile(ResolvableProfile nativeProfile) {
        return new PaperProfileImpl(this, nativeProfile);
    }

    private class SpawnedEntityConfigurator implements Consumer<org.bukkit.entity.Entity> {
        private final Consumer<Entity> config;
        private @Nullable Entity entity;

        private SpawnedEntityConfigurator(Consumer<Entity> config) {
            this.config = config;
        }

        @Override
        public void accept(org.bukkit.entity.Entity entity) {
            this.entity = getEntity(entity);
            config.accept(this.entity);
        }
    }
}
