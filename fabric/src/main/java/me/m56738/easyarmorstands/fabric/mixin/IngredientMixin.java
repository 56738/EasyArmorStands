package me.m56738.easyarmorstands.fabric.mixin;

import me.m56738.easyarmorstands.fabric.EasyArmorStandsMod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ingredient.class)
public class IngredientMixin {
    @Inject(method = "test(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void test(ItemStack input, CallbackInfoReturnable<Boolean> cir) {
        if (EasyArmorStandsMod.isTool(input)) {
            cir.setReturnValue(false);
        }
    }
}
