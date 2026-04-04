package me.m56738.easyarmorstands.command.global;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.lib.cloud.annotations.Command;
import me.m56738.easyarmorstands.lib.cloud.annotations.CommandDescription;
import me.m56738.easyarmorstands.lib.cloud.annotations.Permission;
import me.m56738.easyarmorstands.lib.cloud.annotations.processing.CommandContainer;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.session.SessionListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
        EntityEquipment equipment = player.getEquipment();
        EquipmentCapability equipmentCapability = EasyArmorStandsPlugin.getInstance().getCapability(EquipmentCapability.class);

        // if tool is already selected: do nothing
        for (EquipmentSlot hand : equipmentCapability.getHands()) {
            if (plugin.isTool(equipmentCapability.getItem(equipment, hand))) {
                sendSelected(sender);
                sessionListener.updateHeldItem(player);
                return;
            }
        }

        // attempt to select a tool which is already in the inventory
        if (selectExistingTool(inventory)) {
            sendSelected(sender);
            sessionListener.updateHeldItem(player);
            return;
        }

        if (isEmpty(inventory.getItem(inventory.getHeldItemSlot()))) {
            // put the tool into the selected slot
            inventory.setItem(inventory.getHeldItemSlot(), plugin.createTool(sender.locale()));
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

            if (isEmpty(inventory.getItem(inventory.getHeldItemSlot()))) {
                // selected slot is empty, move the tool into it
                inventory.clear(i);
                inventory.setItem(inventory.getHeldItemSlot(), item);
                return true;
            }

            for (int j = 0; j < 9; j++) {
                ItemStack existingItem = inventory.getItem(j);
                if (!isEmpty(existingItem)) {
                    continue;
                }
                // found an empty hotbar slot, move the tool into it
                inventory.clear(i);
                inventory.setItem(j, item);
                return true;
            }

            // hotbar is full: swap tool with selected slot
            ItemStack existingItem = inventory.getItem(inventory.getHeldItemSlot());
            inventory.setItem(inventory.getHeldItemSlot(), item);
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

    private boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR || item.getAmount() == 0;
    }
}
