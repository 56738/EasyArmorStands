package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class OptionalComponentButton extends PropertyButton<Optional<Component>> {
    private final String command;

    public OptionalComponentButton(Property<Optional<Component>> property, PropertyContainer container, SimpleItemTemplate item, String command) {
        super(property, container, item);
        this.command = command;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(property, click);
        } else if (click.isLeftClick()) {
            click.close();
            SessionCommands.showText(click, property.getType().getName(), property.getValue().orElse(null), command);
        }
    }
}
