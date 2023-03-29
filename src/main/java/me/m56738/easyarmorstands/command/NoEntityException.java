package me.m56738.easyarmorstands.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class NoEntityException extends ComponentException {
    public static final Component MESSAGE = Component.text("No entity selected", NamedTextColor.RED);

    public NoEntityException() {
        super(MESSAGE);
    }
}
