package me.m56738.easyarmorstands.platform.modded;

import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import me.m56738.easyarmorstands.platform.entity.Display;
import me.m56738.easyarmorstands.platform.entity.ItemDisplay;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.util.MainHand;
import me.m56738.easyarmorstands.platform.util.Rotations;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Brightness;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3dc;

public final class ModdedAdapter {
    private ModdedAdapter() {
    }

    public static BlockPos toBlockPos(Location location) {
        return toBlockPos(location.position());
    }

    public static BlockPos toBlockPos(Vector3dc position) {
        return new BlockPos(
                (int) position.x(),
                (int) position.y(),
                (int) position.z());
    }

    public static EquipmentSlot fromNative(net.minecraft.world.entity.EquipmentSlot slot) {
        return EquipmentSlot.values()[slot.ordinal()];
    }

    public static net.minecraft.world.entity.EquipmentSlot toNative(EquipmentSlot slot) {
        return net.minecraft.world.entity.EquipmentSlot.values()[slot.ordinal()];
    }

    public static Rotations fromNative(net.minecraft.core.Rotations rotations) {
        return Rotations.ofDegrees(rotations.x(), rotations.y(), rotations.z());
    }

    public static net.minecraft.core.Rotations toNative(Rotations rotations) {
        return new net.minecraft.core.Rotations((float) rotations.x(), (float) rotations.y(), (float) rotations.z());
    }

    public static MainHand fromNative(HumanoidArm arm) {
        return MainHand.valueOf(arm.name());
    }

    public static HumanoidArm toNative(MainHand hand) {
        return HumanoidArm.valueOf(hand.name());
    }

    public static Display.Billboard fromNative(net.minecraft.world.entity.Display.BillboardConstraints constraints) {
        return Display.Billboard.valueOf(constraints.name());
    }

    public static net.minecraft.world.entity.Display.BillboardConstraints toNative(Display.Billboard billboard) {
        return net.minecraft.world.entity.Display.BillboardConstraints.valueOf(billboard.name());
    }

    public static Display.Brightness fromNative(Brightness brightness) {
        return new Display.Brightness(brightness.block(), brightness.sky());
    }

    public static Brightness toNative(Display.Brightness brightness) {
        return new Brightness(brightness.blockLight(), brightness.skyLight());
    }

    public static ItemDisplay.ItemDisplayTransform fromNative(ItemDisplayContext context) {
        return ItemDisplay.ItemDisplayTransform.valueOf(context.name());
    }

    public static ItemDisplayContext toNative(ItemDisplay.ItemDisplayTransform transform) {
        return ItemDisplayContext.valueOf(transform.name());
    }

    public static int toMask(EquipmentSlot slot, ArmorStand.LockType type) {
        return 1 << toNative(slot).getFilterBit(type.ordinal() * 8);
    }
}
