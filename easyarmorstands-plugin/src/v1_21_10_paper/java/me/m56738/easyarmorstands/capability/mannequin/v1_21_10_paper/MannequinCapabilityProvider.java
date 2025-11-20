package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.mannequin.MannequinCapability;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.MannequinDescriptionProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.MannequinImmovableProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.MannequinMainHandProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.MannequinProfileProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.part.MannequinCapeVisibleProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.part.MannequinHatVisibleProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.part.MannequinJacketVisibleProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.part.MannequinLeftPantsVisibleProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.part.MannequinLeftSleeveVisibleProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.part.MannequinRightPantsVisibleProperty;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property.part.MannequinRightSleeveVisibleProperty;
import me.m56738.easyarmorstands.element.MannequinElementType;
import me.m56738.easyarmorstands.util.NativeComponentMapper;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

public class MannequinCapabilityProvider implements CapabilityProvider<MannequinCapability> {
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public boolean isSupported() {
        try {
            Class.forName("org.bukkit.entity.Mannequin");
            Class.forName("io.papermc.paper.datacomponent.item.ResolvableProfile");
            Mannequin.class.getMethod("setProfile", ResolvableProfile.class);
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
            NativeComponentMapper mapper = NativeComponentMapper.getInstance();

            registry.register(new MannequinMainHandProperty(mannequin));
            registry.register(new MannequinProfileProperty(mannequin));
            registry.register(new MannequinImmovableProperty(mannequin));
            if (mapper != null) {
                registry.register(new MannequinDescriptionProperty(mannequin, mapper));
            }
            registerModelPartProperties(mannequin, registry);
        }

        private void registerModelPartProperties(Mannequin mannequin, PropertyRegistry registry) {
            registry.register(new MannequinCapeVisibleProperty(mannequin));
            registry.register(new MannequinJacketVisibleProperty(mannequin));
            registry.register(new MannequinLeftSleeveVisibleProperty(mannequin));
            registry.register(new MannequinRightSleeveVisibleProperty(mannequin));
            registry.register(new MannequinLeftPantsVisibleProperty(mannequin));
            registry.register(new MannequinRightPantsVisibleProperty(mannequin));
            registry.register(new MannequinHatVisibleProperty(mannequin));
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public ItemStack createProfileItem(Profile profile) {
            ItemStack item = ItemStack.of(Material.PLAYER_HEAD);
            item.setData(DataComponentTypes.PROFILE, ProfileAdapter.toResolvableProfile(profile));
            return item;
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public @Nullable Profile getItemProfile(ItemStack item) {
            ResolvableProfile profile = item.getData(DataComponentTypes.PROFILE);
            if (profile != null) {
                return ProfileAdapter.fromResolvableProfile(profile);
            }
            return null;
        }
    }
}
