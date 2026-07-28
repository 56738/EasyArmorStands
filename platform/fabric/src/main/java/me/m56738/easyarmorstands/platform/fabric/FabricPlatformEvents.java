package me.m56738.easyarmorstands.platform.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
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

    public static final Event<CloseContainerCallback> CLOSE_CONTAINER = EventFactory.createArrayBacked(CloseContainerCallback.class, callbacks -> player -> {
        for (CloseContainerCallback callback : callbacks) {
            callback.onCloseContainer(player);
        }
    });

    public static final Event<DropItemCallback> DROP_ITEM = EventFactory.createArrayBacked(DropItemCallback.class, callbacks -> player -> {
        for (DropItemCallback callback : callbacks) {
            if (callback.onDropItem(player)) {
                return true;
            }
        }
        return false;
    });

    public static final Event<PickUpItemCallback> PICK_UP_ITEM = EventFactory.createArrayBacked(PickUpItemCallback.class, callbacks -> (player, entity) -> {
        for (PickUpItemCallback callback : callbacks) {
            callback.onPickUpItem(player, entity);
        }
    });

    public static final Event<CustomClickCallback> CUSTOM_CLICK = EventFactory.createArrayBacked(CustomClickCallback.class, callbacks -> (id, payload) -> {
        for (CustomClickCallback callback : callbacks) {
            if (callback.onCustomClick(id, payload)) {
                return true;
            }
        }
        return false;
    });

    public static final Event<EntityPlaceCallback> ENTITY_PLACE = EventFactory.createArrayBacked(EntityPlaceCallback.class, callbacks -> (player, entity) -> {
        for (EntityPlaceCallback callback : callbacks) {
            callback.onPlaceEntity(player, entity);
        }
    });

    public static final Event<EntityKillCallback> ENTITY_KILL = EventFactory.createArrayBacked(EntityKillCallback.class, callbacks -> (player, entity) -> {
        for (EntityKillCallback callback : callbacks) {
            callback.onKillEntity(player, entity);
        }
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

    public interface PickUpItemCallback {
        void onPickUpItem(Player player, ItemEntity entity);
    }

    public interface CloseContainerCallback {
        void onCloseContainer(Player player);
    }

    public interface CustomClickCallback {
        boolean onCustomClick(Identifier id, @Nullable Tag payload);
    }

    public interface EntityPlaceCallback {
        void onPlaceEntity(Player player, Entity entity);
    }

    public interface EntityKillCallback {
        void onKillEntity(Player player, Entity entity);
    }
}
