package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.LivingEntity;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedAdapter;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;
import me.m56738.easyarmorstands.platform.util.Location;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.joml.Vector3d;

public interface ModdedLivingEntity extends LivingEntity, ModdedEntity {
    @Override
    net.minecraft.world.entity.LivingEntity getNative();

    static ModdedLivingEntity fromNative(ModdedPlatform platform, net.minecraft.world.entity.LivingEntity entity) {
        return switch (entity) {
            case ServerPlayer e -> ModdedPlayer.fromNative(platform, e);
//            case ArmorStand e -> ModdedArmorStand.fromNative(platform, e);
//            case Mannequin e -> ModdedMannequin.fromNative(platform, e);
//            case Mob e -> ModdedMob.fromNative(platform, e);
            default -> new ModdedLivingEntityImpl(platform, entity);
        };
    }

    static net.minecraft.world.entity.LivingEntity toNative(LivingEntity entity) {
        return ((ModdedLivingEntity) entity).getNative();
    }

    @Override
    default Location eyeLocation() {
        net.minecraft.world.entity.Entity entity = getNative();
        Vector3d position = new Vector3d(entity.getX(), entity.getEyeY(), entity.getZ());
        return Location.of(world(), position, entity.getYRot(), entity.getXRot());
    }

    @Override
    default boolean hasEquipmentSlot(EquipmentSlot slot) {
        return getNative().canUseSlot(ModdedAdapter.toNative(slot));
    }

    @Override
    default ItemStack getEquipment(EquipmentSlot slot) {
        return ModdedItemStack.fromNative(getPlatform(), getNative().getItemBySlot(ModdedAdapter.toNative(slot)));
    }

    @Override
    default void setEquipment(EquipmentSlot slot, ItemStack item) {
        getNative().setItemSlot(ModdedAdapter.toNative(slot), ModdedItemStack.toNative(item));
    }

    @Override
    default boolean hasScaleAttribute() {
        return getNative().getAttribute(Attributes.SCALE) != null;
    }

    @Override
    default double getScaleAttribute() {
        AttributeInstance attribute = getNative().getAttribute(Attributes.SCALE);
        if (attribute != null) {
            return attribute.getBaseValue();
        } else {
            return 1;
        }
    }

    @Override
    default void setScaleAttribute(double value) {
        AttributeInstance attribute = getNative().getAttribute(Attributes.SCALE);
        if (attribute != null) {
            attribute.setBaseValue(value);
        }
    }
}
