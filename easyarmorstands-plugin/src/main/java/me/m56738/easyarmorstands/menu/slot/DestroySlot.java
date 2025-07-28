package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.util.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class DestroySlot implements MenuSlot {
    private final DestroyableElement element;

    public DestroySlot(DestroyableElement element) {
        this.element = element;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        ItemStack item = ItemStack.of(Material.TNT);
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.renderForItem(Message.buttonName("easyarmorstands.menu.destroy"), locale));
            meta.lore(List.of(ComponentUtil.renderForItem(Message.buttonDescription("easyarmorstands.menu.destroy.description"), locale)));
        });
        return item;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }

        if (!EasyArmorStandsPlugin.getInstance().canDestroyElement(click.player(), element)) {
            return;
        }

        History history = EasyArmorStandsPlugin.getInstance().getHistory(click.player());
        history.push(new ElementDestroyAction(element));
        element.destroy();
        click.close();
    }
}
