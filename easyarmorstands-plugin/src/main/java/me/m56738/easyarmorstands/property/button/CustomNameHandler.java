package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.dialog.BooleanDialogEntry;
import me.m56738.easyarmorstands.dialog.DialogBuilder;
import me.m56738.easyarmorstands.dialog.OptionalTextDialogEntry;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CustomNameHandler implements ButtonHandler {
    private final Property<Optional<Component>> property;
    private final Property<Boolean> customNameVisibleProperty;

    public CustomNameHandler(Property<Optional<Component>> property, Property<Boolean> customNameVisibleProperty) {
        this.property = property;
        this.customNameVisibleProperty = customNameVisibleProperty;
    }

    @Override
    public void onClick(MenuClickContext context) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        Player player = context.player();
        if (context.isShiftClick()) {
            Clipboard clipboard = plugin.getClipboard(player);
            clipboard.handlePropertyShiftClick(property);
            clipboard.handlePropertyShiftClick(customNameVisibleProperty);
        } else if (context.isLeftClick()) {
            context.closeMenu();
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                DialogBuilder builder = new DialogBuilder(player.locale());
                builder.setTitle(property.getType().getName());
                builder.addEntry(new OptionalTextDialogEntry("text", property));
                builder.addEntry(new BooleanDialogEntry("visible", customNameVisibleProperty));
                player.showDialog(builder.build());
            });
        }
    }
}
