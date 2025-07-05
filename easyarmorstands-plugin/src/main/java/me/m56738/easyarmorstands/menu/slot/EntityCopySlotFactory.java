package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class EntityCopySlotFactory implements MenuSlotFactory {
    private final SimpleItemTemplate buttonTemplate;
    private final SimpleItemTemplate itemTemplate;

    public EntityCopySlotFactory(SimpleItemTemplate buttonTemplate, SimpleItemTemplate itemTemplate) {
        this.buttonTemplate = buttonTemplate;
        this.itemTemplate = itemTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        Element element = context.element();
        if (element instanceof EntityElement<?>) {
            Entity entity = ((EntityElement<?>) element).getEntity();
            if (context.permissions().test(Permissions.COPY_ENTITY)) {
                ItemStack item = createItem(entity);
                if (item != null) {
                    return new EntityCopySlot(
                            buttonTemplate.withFallbackTemplate(item),
                            itemTemplate.withTemplate(item),
                            element);
                }
            }
        }
        return null;
    }

    private ItemStack createItem(Entity entity) {
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

    private @Nullable ItemStack createArmorStand(EntitySnapshot snapshot) throws ReflectiveOperationException {
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
