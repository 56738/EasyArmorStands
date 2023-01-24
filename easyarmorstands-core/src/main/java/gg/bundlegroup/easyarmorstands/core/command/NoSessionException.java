package gg.bundlegroup.easyarmorstands.core.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class NoSessionException extends RuntimeException {
    public static final Component MESSAGE = Component.text()
            .content("Not editing an armor stand, right click one using ")
            .append(Component.text()
                    .content("/eas give")
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/eas give"))
                    .hoverEvent(Component.text("Give yourself the editor tool"))
                    .decorate(TextDecoration.UNDERLINED)
            )
            .color(NamedTextColor.RED)
            .build();

    public NoSessionException() {
        super("No session");
    }
}
