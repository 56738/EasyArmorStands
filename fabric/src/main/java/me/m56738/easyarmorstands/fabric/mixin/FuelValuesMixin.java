package me.m56738.easyarmorstands.fabric.mixin;

import me.m56738.easyarmorstands.fabric.EasyArmorStandsMod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.FuelValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FuelValues.class)
public class FuelValuesMixin {
    @Inject(method = "isFuel", at = @At("HEAD"), cancellable = true)
    private void isFuel(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (EasyArmorStandsMod.isTool(itemStack)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "burnDuration", at = @At("HEAD"), cancellable = true)
    private void burnDuration(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        if (EasyArmorStandsMod.isTool(itemStack)) {
            cir.setReturnValue(0);
        }
    }
}
