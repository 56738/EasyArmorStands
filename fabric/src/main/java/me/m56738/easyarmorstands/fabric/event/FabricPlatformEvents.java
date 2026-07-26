package me.m56738.easyarmorstands.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

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

    public static final Event<SwapHandsCallback> SWAP_HANDS = EventFactory.createArrayBacked(SwapHandsCallback.class, callbacks -> player -> {
        for (SwapHandsCallback callback : callbacks) {
            if (callback.onSwapHands(player)) {
                return true;
            }
        }
        return false;
    });

    public static final Event<DropItemCallback> DROP_ITEM = EventFactory.createArrayBacked(DropItemCallback.class, callbacks -> player -> {
        for (DropItemCallback callback : callbacks) {
            if (callback.onDropItem(player)) {
                return true;
            }
        }
        return false;
    });

    public static final Event<CustomClickCallback> CUSTOM_CLICK = EventFactory.createArrayBacked(CustomClickCallback.class, callbacks -> (id, payload) -> {
        for (CustomClickCallback callback : callbacks) {
            if (callback.onCustomClick(id, payload)) {
                return true;
            }
        }
        return false;
    });

    public interface ArmSwingCallback {
        void onArmSwing(Player player);
    }

    public interface SelectSlotCallback {
        void onSelectSlot(Player player, int slot);
    }

    public interface SwapHandsCallback {
        boolean onSwapHands(Player player);
    }

    public interface DropItemCallback {
        boolean onDropItem(Player player);
    }

    public interface CustomClickCallback {
        boolean onCustomClick(Identifier id, @Nullable Tag payload);
    }
}
