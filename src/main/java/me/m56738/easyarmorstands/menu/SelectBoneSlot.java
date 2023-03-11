package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.bone.Bone;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class SelectBoneSlot implements InventorySlot {
    private final SessionMenu menu;
    private final Bone bone;
    private final ItemType type;
    private final Component name;

    public SelectBoneSlot(SessionMenu menu, Bone bone, ItemType type, Component name) {
        this.menu = menu;
        this.bone = bone;
        this.type = type;
        this.name = name;
    }

    @Override
    public void initialize(int slot) {
        ItemStack item = Util.createItem(
                type,
                Component.text()
                        .content("Edit ")
                        .append(name)
                        .color(NamedTextColor.BLUE)
                        .build(),
                Arrays.asList(
                        Component.text("Selects this bone", NamedTextColor.GRAY),
                        Component.text("in the editor.", NamedTextColor.GRAY)
                )
        );
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take) {
        if (click) {
            menu.getSession().setBone(bone);
            menu.queueTask(() -> {
                Player player = menu.getSession().getPlayer();
                if (menu.getInventory().equals(player.getOpenInventory().getTopInventory())) {
                    player.closeInventory();
                }
            });
        }
        return false;
    }
}
