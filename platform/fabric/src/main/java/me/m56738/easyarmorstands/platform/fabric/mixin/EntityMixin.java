package me.m56738.easyarmorstands.platform.fabric.mixin;

import me.m56738.easyarmorstands.platform.fabric.FabricPlatformEvents;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"))
    private void gameEvent(Holder<GameEvent> event, @Nullable Entity sourceEntity, CallbackInfo ci) {
        if (!event.equals(GameEvent.ENTITY_PLACE)) return;
        if (!(sourceEntity instanceof Player player)) return;
        FabricPlatformEvents.ENTITY_PLACE.invoker().onPlaceEntity(player, (Entity) (Object) this);
    }
}
