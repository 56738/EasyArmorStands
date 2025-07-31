package me.m56738.easyarmorstands.fabric.platform;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public final class FabricPlatformEvents {
    private FabricPlatformEvents() {
    }

    public static final Event<ArmSwingCallback> ARM_SWING = EventFactory.createArrayBacked(ArmSwingCallback.class, callbacks -> player -> {
        for (ArmSwingCallback callback : callbacks) {
            callback.onArmSwing(player);
        }
    });

    public static final Event<SelectSlotCallback> SELECT_SLOT = EventFactory.createArrayBacked(SelectSlotCallback.class, callbacks -> (player, slot) -> {
        for (SelectSlotCallback callback : callbacks) {
            callback.onSelectSlot(player, slot);
        }
    });

    public interface ArmSwingCallback {
        void onArmSwing(Player player);
    }

    public interface SelectSlotCallback {
        void onSelectSlot(Player player, int slot);
    }
}
