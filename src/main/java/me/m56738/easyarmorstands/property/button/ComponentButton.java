package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.util.ItemTemplate;
import net.kyori.adventure.text.Component;

public class ComponentButton extends SimpleButton<Component> {
    private final String command;

    public ComponentButton(Property<Component> property, PropertyContainer container, ItemTemplate item, String command) {
        super(property, container, item);
        this.command = command;
    }

    @Override
    public void onClick(MenuClick click) {
        if (click.isLeftClick()) {
            click.close();
            SessionCommands.showText(click.player(), property.getType().getName(), property.getValue(), command);
        }
    }
}
