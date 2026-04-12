package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.command.SessionCommands;
import net.kyori.adventure.text.Component;

import java.util.function.Function;

public class ComponentHandler implements ButtonHandler {
    private final Property<Component> property;
    private final String command;

    public ComponentHandler(Property<Component> property, String command) {
        this.property = property;
        this.command = command;
    }

    public static Function<Property<Component>, ComponentHandler> provider(String command) {
        return property -> new ComponentHandler(property, command);
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (context.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(context.player())
                    .handlePropertyShiftClick(property);
        } else if (context.isLeftClick()) {
            SessionCommands.showText(context.player(), property.getType().getName(), property.getValue(), command);
        }
    }
}
