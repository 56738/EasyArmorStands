package me.m56738.easyarmorstands.platform.fabric.mixin;

import com.mojang.authlib.GameProfile;
import me.m56738.easyarmorstands.platform.fabric.FabricPlatformEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    private ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "drop(Z)V", at = @At("HEAD"), cancellable = true)
    private void drop(boolean all, CallbackInfo ci) {
        if (FabricPlatformEvents.DROP_ITEM.invoker().onDropItem(this)) {
            containerMenu.sendAllDataToRemote();
            ci.cancel();
        }
    }

    @Inject(method = "doCloseContainer", at = @At("HEAD"))
    private void doCloseContainer(CallbackInfo ci) {
        FabricPlatformEvents.CLOSE_CONTAINER.invoker().onCloseContainer(this);
    }

    @Inject(method = "onItemPickup", at = @At("HEAD"))
    private void onItemPickup(ItemEntity entity, CallbackInfo ci) {
        FabricPlatformEvents.PICK_UP_ITEM.invoker().onPickUpItem(this, entity);
    }
}
