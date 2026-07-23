package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.dialog.DialogBuilder;
import me.m56738.easyarmorstands.dialog.TextDialogEntry;
import me.m56738.easyarmorstands.platform.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.function.Function;

public class ComponentHandler implements ButtonHandler {
    private final EasyArmorStandsCommon eas;
    private final Property<Component> property;

    public ComponentHandler(EasyArmorStandsCommon eas, Property<Component> property) {
        this.eas = eas;
        this.property = property;
    }

    public static Function<Property<Component>, ComponentHandler> provider(EasyArmorStandsCommon eas) {
        return p -> new ComponentHandler(eas, p);
    }

    @Override
    public void onClick(MenuClickContext context) {
        Player player = context.player();
        if (context.isShiftClick()) {
            Clipboard clipboard = eas.getClipboard(player);
            clipboard.handlePropertyShiftClick(property);
        } else if (context.isLeftClick()) {
            context.closeMenu();
            eas.platform().getScheduler().runTask(() -> {
                DialogBuilder builder = eas.createDialogBuilder(player);
                builder.setTitle(property.getType().getName());
                builder.addEntry(new TextDialogEntry("text", property));
                player.showDialog(builder.build());
            });
        }
    }
}
