package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

@NullMarked
public class EntityCopySlot implements MenuSlot {
    private final EntityElement<?> element;
    private final ItemStack item;

    public EntityCopySlot(EntityElement<?> element, ItemStack item) {
        this.element = element;
        this.item = item;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        ItemStack item = this.item.clone();
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.renderForItem(Message.buttonName("easyarmorstands.menu.copy"), locale));
            meta.lore(List.of(ComponentUtil.renderForItem(Message.buttonDescription("easyarmorstands.menu.copy.description"), locale)));
        });
        return item;
    }

    @Override
    public void onClick(MenuClick click) {
        if (click.cursor().getType() == Material.AIR) {
            click.queueTask(() -> {
                ItemStack item = createItem(element.getEntity());
                if (item != null) {
                    item.editMeta(meta -> meta.displayName(null));
                }
                click.player().setItemOnCursor(item);
            });
        } else {
            click.queueTask(() -> click.player().setItemOnCursor(null));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static @Nullable ItemStack createItem(Entity entity) {
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
        if (!(meta instanceof SpawnEggMeta spawnEggMeta)) {
            return null;
        }

        spawnEggMeta.setSpawnedEntity(snapshot);
        item.setItemMeta(spawnEggMeta);
        return item;
    }

    public static @Nullable ItemStack createArmorStand(EntitySnapshot snapshot) throws ReflectiveOperationException {
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
