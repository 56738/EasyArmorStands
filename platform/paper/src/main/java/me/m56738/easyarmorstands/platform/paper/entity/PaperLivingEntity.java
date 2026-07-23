package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.LivingEntity;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.platform.util.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Mannequin;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;

public interface PaperLivingEntity extends LivingEntity, PaperEntity {
    static PaperLivingEntity fromNative(org.bukkit.entity.LivingEntity entity) {
        return switch (entity) {
            case Player e -> PaperPlayer.fromNative(e);
            case ArmorStand e -> PaperArmorStand.fromNative(e);
            case Mannequin e -> PaperMannequin.fromNative(e);
            case Mob e -> PaperMob.fromNative(e);
            default -> new PaperLivingEntityImpl(entity);
        };
    }

    org.bukkit.entity.LivingEntity getNative();

    static org.bukkit.entity.LivingEntity toNative(LivingEntity entity) {
        return ((PaperLivingEntity) entity).getNative();
    }

    @Override
    default Location eyeLocation() {
        return PaperAdapter.fromNative(getNative().getEyeLocation());
    }

    @Override
    default boolean hasEquipmentSlot(EquipmentSlot slot) {
        return getNative().getEquipment() != null
                && getNative().canUseEquipmentSlot(PaperAdapter.toNative(slot));
    }

    @Override
    default ItemStack getEquipment(EquipmentSlot slot) {
        EntityEquipment equipment = getNative().getEquipment();
        if (equipment == null) {
            return PaperItemStack.empty();
        }
        return PaperItemStack.fromNative(equipment.getItem(PaperAdapter.toNative(slot)));
    }

    @Override
    default void setEquipment(EquipmentSlot slot, ItemStack item) {
        EntityEquipment equipment = getNative().getEquipment();
        if (equipment == null) {
            return;
        }
        equipment.setItem(PaperAdapter.toNative(slot), PaperItemStack.toNative(item), true);
    }

    @Override
    default boolean hasScaleAttribute() {
        return getNative().getAttribute(Attribute.SCALE) != null;
    }

    @Override
    default double getScaleAttribute() {
        AttributeInstance attribute = getNative().getAttribute(Attribute.SCALE);
        if (attribute != null) {
            return attribute.getBaseValue();
        }
        return 1;
    }

    @Override
    default void setScaleAttribute(double value) {
        AttributeInstance attribute = getNative().getAttribute(Attribute.SCALE);
        if (attribute != null) {
            attribute.setBaseValue(value);
        }
    }
}
