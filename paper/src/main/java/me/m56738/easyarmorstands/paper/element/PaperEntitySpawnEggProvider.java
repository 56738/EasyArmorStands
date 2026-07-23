package me.m56738.easyarmorstands.paper.element;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;

public final class PaperEntitySpawnEggProvider {
    private PaperEntitySpawnEggProvider() {
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
