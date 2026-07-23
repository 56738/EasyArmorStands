package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.dialog.BooleanDialogEntry;
import me.m56738.easyarmorstands.dialog.DialogBuilder;
import me.m56738.easyarmorstands.dialog.OptionalTextDialogEntry;
import me.m56738.easyarmorstands.platform.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public class CustomNameHandler implements ButtonHandler {
    private final EasyArmorStandsCommon eas;
    private final Property<Optional<Component>> property;
    private final Property<Boolean> customNameVisibleProperty;

    public CustomNameHandler(EasyArmorStandsCommon eas, Property<Optional<Component>> property, Property<Boolean> customNameVisibleProperty) {
        this.eas = eas;
        this.property = property;
        this.customNameVisibleProperty = customNameVisibleProperty;
    }

    @Override
    public void onClick(MenuClickContext context) {
        Player player = context.player();
        if (context.isShiftClick()) {
            Clipboard clipboard = eas.getClipboard(player);
            clipboard.handlePropertyShiftClick(property);
            clipboard.handlePropertyShiftClick(customNameVisibleProperty);
        } else if (context.isLeftClick()) {
            context.closeMenu();
            eas.platform().getScheduler().runTask(() -> {
                DialogBuilder builder = eas.createDialogBuilder(player);
                builder.setTitle(property.getType().getName());
                builder.addEntry(new OptionalTextDialogEntry("text", property));
                builder.addEntry(new BooleanDialogEntry("visible", customNameVisibleProperty));
                player.showDialog(builder.build());
            });
        }
    }
}
