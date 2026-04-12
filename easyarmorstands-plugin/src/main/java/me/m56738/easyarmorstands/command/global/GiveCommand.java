package me.m56738.easyarmorstands.command.global;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.session.SessionListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;

import java.util.HashMap;

@CommandContainer
public class GiveCommand {
    @Command("eas give")
    @Permission(Permissions.GIVE)
    @CommandDescription("easyarmorstands.command.description.give")
    public void give(EasPlayer sender, SessionListener sessionListener) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        Player player = sender.get();
        PlayerInventory inventory = player.getInventory();

        // if tool is already selected: do nothing
        if (plugin.isTool(inventory.getItemInMainHand()) || plugin.isTool(inventory.getItemInOffHand())) {
            sendSelected(sender);
            sessionListener.updateHeldItem(player);
            return;
        }

        // attempt to select a tool which is already in the inventory
        if (selectExistingTool(inventory)) {
            sendSelected(sender);
            sessionListener.updateHeldItem(player);
            return;
        }

        if (inventory.getItemInMainHand().isEmpty()) {
            // put the tool into the selected slot
            inventory.setItemInMainHand(plugin.createTool(sender.locale()));
        } else {
            // put the tool into any slot
            HashMap<Integer, ItemStack> failed = inventory.addItem(plugin.createTool(sender.locale()));
            if (!failed.isEmpty()) {
                sendFull(sender);
                return;
            }
            selectExistingTool(inventory);
        }

        sendAdded(sender);
        sessionListener.updateHeldItem(player);
    }

    private boolean selectExistingTool(PlayerInventory inventory) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (!plugin.isTool(item)) {
                continue;
            }

            if (i < 9) {
                // tool is already in the hotbar, select it
                inventory.setHeldItemSlot(i);
                return true;
            }

            if (inventory.getItemInMainHand().isEmpty()) {
                // selected slot is empty, move the tool into it
                inventory.clear(i);
                inventory.setItemInMainHand(item);
                return true;
            }

            for (int j = 0; j < 9; j++) {
                ItemStack existingItem = inventory.getItem(j);
                if (existingItem != null && !existingItem.isEmpty()) {
                    continue;
                }
                // found an empty hotbar slot, move the tool into it
                inventory.clear(i);
                inventory.setItem(j, item);
                inventory.setHeldItemSlot(j);
                return true;
            }

            // hotbar is full: swap tool with selected slot
            ItemStack existingItem = inventory.getItemInMainHand();
            inventory.setItemInMainHand(item);
            inventory.setItem(i, existingItem);
            return true;
        }
        return false;
    }

    private void sendAdded(EasPlayer sender) {
        sender.sendMessage(Message.success("easyarmorstands.success.added-tool-to-inventory"));
    }

    private void sendSelected(EasPlayer sender) {
        sender.sendMessage(Message.success("easyarmorstands.success.selected-tool"));
    }

    private void sendFull(EasPlayer sender) {
        sender.sendMessage(Message.error("easyarmorstands.error.inventory-full"));
    }
}
