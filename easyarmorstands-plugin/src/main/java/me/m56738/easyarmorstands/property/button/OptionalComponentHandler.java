package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.command.SessionCommands;
import net.kyori.adventure.text.Component;

import java.util.Optional;
import java.util.function.Function;

public class OptionalComponentHandler implements ButtonHandler {
    private final Property<Optional<Component>> property;
    private final String command;

    public OptionalComponentHandler(Property<Optional<Component>> property, String command) {
        this.property = property;
        this.command = command;
    }

    public static Function<Property<Optional<Component>>, OptionalComponentHandler> provider(String command) {
        return property -> new OptionalComponentHandler(property, command);
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (context.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(context.player())
                    .handlePropertyShiftClick(property);
        } else if (context.isLeftClick()) {
            context.closeMenu();
            SessionCommands.showText(context.player(), property.getType().getName(), property.getValue().orElse(null), command);
        }
    }
}
