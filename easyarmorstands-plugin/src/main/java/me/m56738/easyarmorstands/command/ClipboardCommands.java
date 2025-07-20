package me.m56738.easyarmorstands.command;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.command.requirement.RequireElement;
import me.m56738.easyarmorstands.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.util.PropertyCopier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.paper.util.sender.PlayerSource;

@Command("eas")
public class ClipboardCommands {
    @Command("clipboard")
    @Permission(Permissions.CLIPBOARD)
    @CommandDescription("easyarmorstands.command.description.clipboard")
    public void clipboard(PlayerSource source, Clipboard clipboard) {
        Player sender = source.source();
        if (clipboard.getProperties().isEmpty()) {
            sender.sendMessage(Message.warning("easyarmorstands.warning.clipboard-empty"));
            return;
        }
        sender.sendMessage(Message.title("easyarmorstands.title.clipboard"));
        clipboard.getProperties().forEach(property -> sender.sendMessage(Component.text()
                .content("* ")
                .color(NamedTextColor.GRAY)
                .append(describeProperty(property))));
        sender.sendMessage(Message.hint("easyarmorstands.hint.paste-clipboard", Message.command("/eas paste")));
        sender.sendMessage(Message.hint("easyarmorstands.hint.clear-clipboard", Message.command("/eas clipboard clear")));
    }

    private <T> Component describeProperty(Property<T> property) {
        PropertyType<T> type = property.getType();
        return Component.text()
                .append(type.getName())
                .append(Component.text(": "))
                .append(type.getValueComponent(property.getValue()))
                .build();
    }

    @Command("clipboard clear")
    @Permission(Permissions.CLIPBOARD)
    @CommandDescription("easyarmorstands.command.description.clipboard.clear")
    public void clear(PlayerSource source, Clipboard clipboard) {
        Player sender = source.source();
        if (clipboard.getProperties().isEmpty()) {
            sender.sendMessage(Message.warning("easyarmorstands.warning.clipboard-empty"));
        } else {
            clipboard.getProperties().clear();
            sender.sendMessage(Message.success("easyarmorstands.success.clipboard-cleared"));
        }
    }

    @Command("copy")
    @Permission(Permissions.CLIPBOARD)
    @CommandDescription("easyarmorstands.command.description.copy")
    @RequireElement
    public void copy(PlayerSource source, Clipboard clipboard, Element element) {
        Player sender = source.source();
        element.getProperties().forEach(property -> {
            if (property.getType().canCopy(PaperPlayer.fromNative(sender))) {
                copyProperty(clipboard, property);
            }
        });
        sender.sendMessage(Message.success("easyarmorstands.success.clipboard-copied"));
        sender.sendMessage(Message.hint("easyarmorstands.hint.show-clipboard", Message.command("/eas clipboard")));
        sender.sendMessage(Message.hint("easyarmorstands.hint.paste-clipboard", Message.command("/eas paste")));
    }

    private <T> void copyProperty(Clipboard clipboard, Property<T> property) {
        clipboard.getProperties().put(property.getType(), property.getValue());
    }

    @Command("paste")
    @Permission(Permissions.CLIPBOARD)
    @CommandDescription("easyarmorstands.command.description.paste")
    @RequireElementSelection
    public void paste(PlayerSource source, Clipboard clipboard, ElementSelection selection) {
        Player sender = source.source();
        if (clipboard.getProperties().isEmpty()) {
            sender.sendMessage(Message.error("easyarmorstands.error.clipboard-empty"));
            sender.sendMessage(Message.hint("easyarmorstands.hint.copy-property"));
            sender.sendMessage(Message.hint("easyarmorstands.hint.copy-all-properties", Message.command("/eas copy")));
            return;
        }

        PropertyCopier copier = new PropertyCopier();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(PaperPlayer.fromNative(sender))) {
            for (Element element : selection.elements()) {
                PropertyContainer properties = context.getProperties(element);
                copier.copyProperties(properties, clipboard.getProperties());
            }
            context.commit(Message.component("easyarmorstands.history.clipboard-pasted"));
        }

        if (copier.getSuccessCount() > 0) {
            sender.sendMessage(Message.success("easyarmorstands.success.clipboard-pasted"));
            if (copier.getFailureCount() > 0) {
                sender.sendMessage(Message.warning("easyarmorstands.warning.clipboard-partial"));
            }
        } else if (copier.getFailureCount() > 0) {
            sender.sendMessage(Message.error("easyarmorstands.error.clipboard-failed"));
        }
    }
}
