package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.property.ButtonProperty;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

public class ButtonPropertySlot implements MenuSlot {
    private final ButtonProperty<?> property;
    private final Session session;

    public ButtonPropertySlot(ButtonProperty<?> property, Session session) {
        this.property = property;
        this.session = session;
    }

    @Override
    public ItemStack getItem() {
        return property.createItem();
    }

    @Override
    public void onClick(MenuClick click) {
        click.cancel();

        if (!click.isLeftClick()) {
            return;
        }

        String permission = property.getType().getPermission();
        if (permission != null && !session.getPlayer().hasPermission(permission)) {
            session.sendMessage(Component.text("You don't have permission to edit this property.", NamedTextColor.RED));
            return;
        }
        property.onClick(session);
        session.commit();
        click.updateItem();
    }
}
