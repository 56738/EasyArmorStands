package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.dialog.DialogBuilder;
import me.m56738.easyarmorstands.dialog.TextDialogEntry;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ComponentHandler implements ButtonHandler {
    private final Property<Component> property;

    public ComponentHandler(Property<Component> property) {
        this.property = property;
    }

    @Override
    public void onClick(MenuClickContext context) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        Player player = context.player();
        if (context.isShiftClick()) {
            Clipboard clipboard = plugin.getClipboard(player);
            clipboard.handlePropertyShiftClick(property);
        } else if (context.isLeftClick()) {
            context.closeMenu();
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                DialogBuilder builder = new DialogBuilder(player.locale());
                builder.setTitle(property.getType().getName());
                builder.addEntry(new TextDialogEntry("text", property));
                player.showDialog(builder.build());
            });
        }
    }
}
