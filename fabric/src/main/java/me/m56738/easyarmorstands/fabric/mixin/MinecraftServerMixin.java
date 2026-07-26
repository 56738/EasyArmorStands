package me.m56738.easyarmorstands.fabric.mixin;

import me.m56738.easyarmorstands.fabric.event.FabricPlatformEvents;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "handleCustomClickAction", at = @At("HEAD"), cancellable = true)
    private void handleCustomClickAction(Identifier id, Optional<Tag> payload, CallbackInfo ci) {
        if (FabricPlatformEvents.CUSTOM_CLICK.invoker().onCustomClick(id, payload.orElse(null))) {
            ci.cancel();
        }
    }
}
