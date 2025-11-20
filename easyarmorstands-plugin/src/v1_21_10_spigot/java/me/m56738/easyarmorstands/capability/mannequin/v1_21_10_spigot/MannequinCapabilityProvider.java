package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot;

import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.mannequin.MannequinCapability;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot.property.MannequinDescriptionProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot.property.MannequinImmovableProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot.property.MannequinMainHandProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot.property.MannequinProfileProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot.property.MannequinSkinPartVisibleProperty;
import me.m56738.easyarmorstands.element.MannequinElementType;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mannequin;
import org.bukkit.entity.model.PlayerModelPart;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.Nullable;

public class MannequinCapabilityProvider implements CapabilityProvider<MannequinCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("org.bukkit.entity.Mannequin");
            Mannequin.class.getMethod("setPlayerProfile", PlayerProfile.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public MannequinCapability create(Plugin plugin) {
        return new MannequinCapabilityImpl();
    }

    private static class MannequinCapabilityImpl implements MannequinCapability {
        @Override
        public boolean isMannequin(Entity entity) {
            return entity instanceof Mannequin;
        }

        @Override
        public MannequinElementType<?> createElementType() {
            return new MannequinElementType<>(EntityType.MANNEQUIN, Mannequin.class);
        }

        @Override
        public void registerProperties(Entity entity, PropertyRegistry registry) {
            Mannequin mannequin = (Mannequin) entity;
            registry.register(new MannequinMainHandProperty(mannequin));
            registry.register(new MannequinProfileProperty(mannequin));
            registry.register(new MannequinImmovableProperty(mannequin));
            registry.register(new MannequinDescriptionProperty(mannequin));
            if (ReflectionUtil.hasClass("org.bukkit.entity.model.PlayerModelPart")) {
                registerModelPartProperties(mannequin, registry);
            }
        }

        @SuppressWarnings("UnstableApiUsage")
        private void registerModelPartProperties(Mannequin mannequin, PropertyRegistry registry) {
            registry.register(new MannequinSkinPartVisibleProperty(mannequin, PlayerModelPart.CAPE, SkinPart.CAPE));
            registry.register(new MannequinSkinPartVisibleProperty(mannequin, PlayerModelPart.JACKET, SkinPart.JACKET));
            registry.register(new MannequinSkinPartVisibleProperty(mannequin, PlayerModelPart.LEFT_SLEEVE, SkinPart.LEFT_SLEEVE));
            registry.register(new MannequinSkinPartVisibleProperty(mannequin, PlayerModelPart.RIGHT_SLEEVE, SkinPart.RIGHT_SLEEVE));
            registry.register(new MannequinSkinPartVisibleProperty(mannequin, PlayerModelPart.LEFT_PANTS_LEG, SkinPart.LEFT_PANTS));
            registry.register(new MannequinSkinPartVisibleProperty(mannequin, PlayerModelPart.RIGHT_PANTS_LEG, SkinPart.RIGHT_PANTS));
            registry.register(new MannequinSkinPartVisibleProperty(mannequin, PlayerModelPart.HAT, SkinPart.HAT));
        }

        @Override
        public ItemStack createProfileItem(Profile profile) {
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            if (meta != null) {
                PlayerProfile playerProfile = ProfileAdapter.toPlayerProfile(profile);
                if (playerProfile != null) {
                    meta.setOwnerProfile(playerProfile);
                }
                item.setItemMeta(meta);
            }
            return item;
        }

        @Override
        public @Nullable Profile getItemProfile(ItemStack item) {
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof SkullMeta) {
                PlayerProfile profile = ((SkullMeta) meta).getOwnerProfile();
                if (profile != null) {
                    return ProfileAdapter.fromPlayerProfile(profile);
                }
            }
            return null;
        }
    }
}
