package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ComponentButton extends PropertyButton<Component> {
    private final String command;

    public ComponentButton(Element element, PropertyType<Component> type, SimpleItemTemplate item, String command) {
        super(element, type, item);
        this.command = command;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        Property<Component> property = getUntrackedProperty();
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(property, click);
        } else if (click.isLeftClick()) {
            click.close();
            SessionCommands.showText(click, property.getType().getName(), property.getValue(), command);
        }
    }
}
