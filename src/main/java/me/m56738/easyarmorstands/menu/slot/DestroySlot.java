package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.element.DestroyableElement;
import me.m56738.easyarmorstands.event.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class DestroySlot implements MenuSlot {
    private final DestroyableElement element;

    public DestroySlot(DestroyableElement element) {
        this.element = element;
    }

    @Override
    public ItemStack getItem() {
        return Util.createItem(
                ItemType.TNT,
                Component.text("Destroy", NamedTextColor.BLUE),
                Collections.singletonList(
                        Component.text("Deletes this entity.", NamedTextColor.GRAY)
                )
        );
    }

    @Override
    public void onClick(MenuClick click) {
        click.cancel();
        if (!click.isLeftClick()) {
            return;
        }

        Player player = click.player();
        PlayerDestroyElementEvent event = new PlayerDestroyElementEvent(player, element);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        EasyArmorStands.getInstance().getHistoryManager().getHistory(player).push(new ElementDestroyAction(element));
        element.destroy();
        click.close();
    }
}
