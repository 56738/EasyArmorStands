package me.m56738.easyarmorstands.fabric.mixin;

import me.m56738.easyarmorstands.fabric.platform.FabricPlatformEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Shadow @Final public Player player;

    @Inject(method = "setSelectedSlot", at = @At("RETURN"))
    private void setSelectedSlot(int slot, CallbackInfo ci) {
        FabricPlatformEvents.SELECT_SLOT.invoker().onSelectSlot(player, slot);
    }
}
