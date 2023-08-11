package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.editor.DestroyableObject;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class DestroySlot implements MenuSlot {
    private final DestroyableObject destroyableObject;

    public DestroySlot(DestroyableObject destroyableObject) {
        this.destroyableObject = destroyableObject;
    }

    @Override
    public ItemStack getItem() {
        return Util.createItem(
                ItemType.BARRIER,
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

        if (destroyableObject.destroy(click.player())) {
            click.close();
        }
    }
}
