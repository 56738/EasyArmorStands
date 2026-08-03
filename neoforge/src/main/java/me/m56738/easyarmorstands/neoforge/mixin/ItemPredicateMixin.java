package me.m56738.easyarmorstands.neoforge.mixin;

import me.m56738.easyarmorstands.neoforge.EasyArmorStandsMod;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.world.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemPredicate.class)
public class ItemPredicateMixin {
    @Inject(method = "test(Lnet/minecraft/world/item/ItemInstance;)Z", at = @At("HEAD"), cancellable = true)
    private void test(ItemInstance itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (EasyArmorStandsMod.isTool(itemStack)) {
            cir.setReturnValue(false);
        }
    }
}
