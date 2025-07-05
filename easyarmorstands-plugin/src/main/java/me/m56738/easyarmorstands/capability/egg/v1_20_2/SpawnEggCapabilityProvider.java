package me.m56738.easyarmorstands.capability.egg.v1_20_2;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.egg.SpawnEggCapability;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

@SuppressWarnings("UnstableApiUsage")
public class SpawnEggCapabilityProvider implements CapabilityProvider<SpawnEggCapability> {
    @Override
    public boolean isSupported() {
        try {
            Entity.class.getMethod("createSnapshot");
            SpawnEggMeta.class.getMethod("setSpawnedEntity", EntitySnapshot.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public SpawnEggCapability create(Plugin plugin) {
        return new SpawnEggCapabilityImpl();
    }

    private static class SpawnEggCapabilityImpl implements SpawnEggCapability {
        @Override
        public ItemStack createSpawnEgg(Entity entity) {
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
}
