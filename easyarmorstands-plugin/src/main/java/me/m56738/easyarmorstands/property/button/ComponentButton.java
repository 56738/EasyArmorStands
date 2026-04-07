package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.command.SessionCommands;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class ComponentButton extends PropertyButton<Component> {
    private final String command;

    public ComponentButton(Property<Component> property, MenuIcon icon, List<Component> description, String command) {
        super(property, icon, description);
        this.command = command;
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (context.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(context.player())
                    .handlePropertyShiftClick(property);
        } else if (context.isLeftClick()) {
            context.closeMenu();
            SessionCommands.showText(context.player(), property.getType().getName(), property.getValue(), command);
        }
    }
}
