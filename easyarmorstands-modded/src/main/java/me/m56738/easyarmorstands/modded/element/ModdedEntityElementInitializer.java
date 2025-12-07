package me.m56738.easyarmorstands.modded.element;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.api.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntity;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandArmsProperty;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandBasePlateProperty;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandGravityProperty;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandInvulnerabilityProperty;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandLockProperty;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandMarkerProperty;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandSizeProperty;
import me.m56738.easyarmorstands.modded.property.armorstand.ArmorStandVisibilityProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntityAIProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntityCustomNameProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntityCustomNameVisibleProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntityEquipmentProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntityGlowingProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntityScaleProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntitySilentProperty;
import me.m56738.easyarmorstands.modded.property.entity.EntityTagsProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.MannequinDescriptionProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.MannequinImmovableProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.MannequinMainHandProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.MannequinProfileProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.part.MannequinCapeVisibleProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.part.MannequinHatVisibleProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.part.MannequinJacketVisibleProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.part.MannequinLeftPantsVisibleProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.part.MannequinLeftSleeveVisibleProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.part.MannequinRightPantsVisibleProperty;
import me.m56738.easyarmorstands.modded.property.mannequin.part.MannequinRightSleeveVisibleProperty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.Mannequin;

public class ModdedEntityElementInitializer {
    private final EasyArmorStandsCommonProvider easProvider;

    public ModdedEntityElementInitializer(EasyArmorStandsCommonProvider easProvider) {
        this.easProvider = easProvider;
    }

    public void registerProperties(DefaultEntityElement element) {
        registerProperties(ModdedEntity.toNative(element.getEntity()), element.getProperties());
    }

    private void registerProperties(Entity entity, PropertyRegistry registry) {
        ModdedPlatform platform = (ModdedPlatform) easProvider.getEasyArmorStands().getPlatform();
        registerEntityProperties(entity, registry, platform);
        if (entity instanceof LivingEntity livingEntity) {
            registerLivingEntityProperties(livingEntity, registry, platform);
        }
        if (entity instanceof Mob mob) {
            registerMobProperties(mob, registry);
        }
        if (entity instanceof ArmorStand armorStand) {
            registerArmorStandProperties(armorStand, registry);
        }
        if (entity instanceof Mannequin mannequin) {
            registerMannequinProperties(mannequin, registry, platform);
        }
    }

    private void registerEntityProperties(Entity entity, PropertyRegistry registry, ModdedPlatform platform) {
        registry.register(new EntityGlowingProperty(entity));
        registry.register(new EntityLocationProperty(platform, entity));
        registry.register(new EntitySilentProperty(entity));
        registry.register(new EntityTagsProperty(entity));
        registry.register(new EntityCustomNameProperty(entity, platform.getAdventure()));
        registry.register(new EntityCustomNameVisibleProperty(entity));
    }

    private void registerLivingEntityProperties(LivingEntity entity, PropertyRegistry registry, ModdedPlatform platform) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (entity instanceof ArmorStand && (slot == EquipmentSlot.BODY || slot == EquipmentSlot.SADDLE)) {
                continue;
            }
            registry.register(new EntityEquipmentProperty(entity, slot, platform));
        }
        registry.register(new EntityScaleProperty(entity));
    }

    private void registerMobProperties(Mob entity, PropertyRegistry registry) {
        registry.register(new EntityAIProperty(entity));
    }

    private void registerArmorStandProperties(ArmorStand entity, PropertyRegistry registry) {
        registry.register(new ArmorStandArmsProperty(entity));
        registry.register(new ArmorStandBasePlateProperty(entity));
        registry.register(new ArmorStandMarkerProperty(entity));
        registry.register(new ArmorStandSizeProperty(entity));
        registry.register(new ArmorStandVisibilityProperty(entity));
        registry.register(new ArmorStandGravityProperty(entity));
        registry.register(new ArmorStandInvulnerabilityProperty(entity));
        registry.register(new ArmorStandLockProperty(entity));
        for (ArmorStandPart part : ArmorStandPart.values()) {
            registry.register(new ArmorStandPoseProperty(entity, part));
        }
    }

    private void registerMannequinProperties(Mannequin entity, PropertyRegistry registry, ModdedPlatform platform) {
        registry.register(new MannequinMainHandProperty(entity));
        registry.register(new MannequinProfileProperty(entity, platform));
        registry.register(new MannequinImmovableProperty(entity));
        registry.register(new MannequinDescriptionProperty(entity, platform.getAdventure()));
        registry.register(new MannequinCapeVisibleProperty(entity));
        registry.register(new MannequinJacketVisibleProperty(entity));
        registry.register(new MannequinLeftSleeveVisibleProperty(entity));
        registry.register(new MannequinRightSleeveVisibleProperty(entity));
        registry.register(new MannequinLeftPantsVisibleProperty(entity));
        registry.register(new MannequinRightPantsVisibleProperty(entity));
        registry.register(new MannequinHatVisibleProperty(entity));
    }
}
