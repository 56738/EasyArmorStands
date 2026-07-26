package me.m56738.easyarmorstands.platform.modded.inventory;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import me.m56738.easyarmorstands.platform.event.EventBus;
import me.m56738.easyarmorstands.platform.event.EventType;
import me.m56738.easyarmorstands.platform.event.callback.MenuClickCallback;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ModdedContainerMenu extends ChestMenu {
    private final ModdedInventory container;
    private final IntSet dragSlots = new IntOpenHashSet();
    private boolean forwardingQuickCraft;

    public ModdedContainerMenu(MenuType<?> menuType, int containerId, Inventory inventory, ModdedInventory container, int rows) {
        super(menuType, containerId, inventory, container.getNative(), rows);
        this.container = container;
    }

    @Override
    protected Slot addSlot(Slot slot) {
        if (slot.container == getContainer()) {
            slot = new ReadOnlySlot(getContainer(), slot.getContainerSlot(), slot.x, slot.y);
        }
        return super.addSlot(slot);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, ContainerInput containerInput, Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ModdedPlatform platform = container.getPlatform();
        EventBus eventBus = platform.getEventBus();
        ModdedPlayer moddedPlayer = ModdedPlayer.fromNative(platform, serverPlayer);
        if (containerInput == ContainerInput.PICKUP || containerInput == ContainerInput.QUICK_MOVE || containerInput == ContainerInput.PICKUP_ALL) {
            MenuClickCallback.ClickOptions options = new ContainerClickOptions(containerInput, buttonNum);
            boolean handled = eventBus.invoker(EventType.MENU_CLICK).onClick(
                    moddedPlayer,
                    container,
                    slotIndex,
                    options);
            if (!handled) {
                super.clicked(slotIndex, buttonNum, containerInput, player);
            }
        } else if (containerInput == ContainerInput.QUICK_CRAFT) {
            // drag
            int status = buttonNum & 0b11;
            int type = (buttonNum >> 2) & 0b11;
            if (forwardingQuickCraft) {
                super.clicked(slotIndex, buttonNum, containerInput, player);
                if (status == 2) {
                    forwardingQuickCraft = false;
                }
                return;
            }
            if (status == 0) {
                // start
                dragSlots.clear();
            } else if (status == 1) {
                // add
                if (isValidSlotIndex(slotIndex)) {
                    if (dragSlots.isEmpty() && slotIndex >= container.getSize()) {
                        // first selected slot is in the bottom inventory - pass through
                        forwardingQuickCraft = true;
                        super.clicked(-999, buttonNum - 1, containerInput, player);
                        super.clicked(slotIndex, buttonNum, containerInput, player);
                        return;
                    }
                    dragSlots.add(slotIndex);
                }
            } else if (status == 2) {
                // end
                if (dragSlots.size() == 1) {
                    int slot = dragSlots.iterator().nextInt();
                    dragSlots.clear();
                    MenuClickCallback.ClickOptions options = new ContainerClickOptions(ContainerInput.PICKUP, type);
                    boolean handled = eventBus.invoker(EventType.MENU_CLICK).onClick(
                            moddedPlayer,
                            container,
                            slot,
                            options);
                    if (!handled) {
                        super.clicked(slot, type, ContainerInput.PICKUP, player);
                    }
                } else {
                    eventBus.invoker(EventType.MENU_DRAG).onDrag(moddedPlayer, container);
                }
            }
        } else {
            super.clicked(slotIndex, buttonNum, containerInput, player);
        }
    }

    private record ContainerClickOptions(
            ContainerInput input,
            int buttonNum
    ) implements MenuClickCallback.ClickOptions {
        @Override
        public boolean left() {
            return buttonNum == 0;
        }

        @Override
        public boolean right() {
            return buttonNum == 1;
        }

        @Override
        public boolean shift() {
            return input == ContainerInput.QUICK_MOVE;
        }
    }

    private static class ReadOnlySlot extends Slot {
        public ReadOnlySlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPickup(Player player) {
            return false;
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return false;
        }
    }
}
