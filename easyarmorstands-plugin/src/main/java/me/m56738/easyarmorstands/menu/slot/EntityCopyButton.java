package me.m56738.easyarmorstands.menu.slot;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

@NullMarked
public class EntityCopyButton implements MenuButton {
    private static final Key KEY = EasyArmorStands.key("copy");
    private final Entity entity;
    private final ElementType type;
    private final ItemStack item;

    public EntityCopyButton(Entity entity, ElementType type, ItemStack item) {
        this.entity = entity;
        this.type = type;
        this.item = item;
    }

    @Override
    public Key key() {
        return KEY;
    }

    @Override
    public MenuIcon icon() {
        return MenuIcon.of(item);
    }

    @Override
    public MenuButtonCategory category() {
        return MenuButtonCategory.FOOTER;
    }

    @Override
    public Component name() {
        return Component.translatable("easyarmorstands.menu.copy");
    }

    @Override
    public List<Component> description() {
        return List.of(Component.translatable("easyarmorstands.menu.copy.description"));
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onClick(MenuClickContext context) {
        Player player = context.player();
        ItemStack cursor = Util.wrapItem(player.getItemOnCursor());
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        if (cursor.isEmpty()) {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                ItemStack item = createSpawnEgg(entity);
                if (item != null) {
                    item.setData(DataComponentTypes.ITEM_NAME,
                            GlobalTranslator.render(Component.translatable("easyarmorstands.copy.item.name",
                                    NamedTextColor.GOLD,
                                    type.getDisplayName()), player.locale()));
                    player.setItemOnCursor(item);
                }
            });
        } else {
            plugin.getServer().getScheduler().runTask(plugin, () -> player.setItemOnCursor(null));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static @Nullable ItemStack createSpawnEgg(Entity entity) {
        EntitySnapshot snapshot = entity.createSnapshot();
        if (snapshot == null) {
            return null;
        }

        try {
            ItemStack item = createArmorStand(snapshot);
            if (item != null) {
                return item;
            }
        } catch (Throwable ignored) {
        }

        Material material;
        try {
            material = Material.valueOf(entity.getType().name() + "_SPAWN_EGG");
        } catch (IllegalArgumentException e) {
            return null;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof SpawnEggMeta)) {
            return null;
        }
        ((SpawnEggMeta) meta).setSpawnedEntity(snapshot);
        item.setItemMeta(meta);
        return item;
    }

    private static @Nullable ItemStack createArmorStand(EntitySnapshot snapshot) throws ReflectiveOperationException {
        if (snapshot.getEntityType() != EntityType.ARMOR_STAND) {
            return null;
        }
        Object data = snapshot.getClass().getMethod("getData").invoke(snapshot);
        ItemStack item = new ItemStack(Material.ARMOR_STAND);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        Field tagField = meta.getClass().getDeclaredField("entityTag");
        tagField.setAccessible(true);
        tagField.set(meta, data);
        item.setItemMeta(meta);
        return item;
    }
}
