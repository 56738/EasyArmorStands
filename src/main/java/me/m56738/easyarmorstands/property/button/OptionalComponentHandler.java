package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.dialog.DialogBuilder;
import me.m56738.easyarmorstands.dialog.OptionalTextDialogEntry;
import me.m56738.easyarmorstands.platform.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.Optional;
import java.util.function.Function;

public class OptionalComponentHandler implements ButtonHandler {
    private final EasyArmorStandsCommon eas;
    private final Property<Optional<Component>> property;

    public OptionalComponentHandler(EasyArmorStandsCommon eas, Property<Optional<Component>> property) {
        this.eas = eas;
        this.property = property;
    }

    public static Function<Property<Optional<Component>>, OptionalComponentHandler> provider(EasyArmorStandsCommon eas) {
        return p -> new OptionalComponentHandler(eas, p);
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
                builder.addEntry(new OptionalTextDialogEntry("text", property));
                player.showDialog(builder.build());
            });
        }
    }
}
