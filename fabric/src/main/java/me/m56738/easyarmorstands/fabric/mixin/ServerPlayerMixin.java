package me.m56738.easyarmorstands.fabric.mixin;

import com.mojang.authlib.GameProfile;
import me.m56738.easyarmorstands.fabric.event.FabricPlatformEvents;
import net.minecraft.server.level.ServerPlayer;
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
}
