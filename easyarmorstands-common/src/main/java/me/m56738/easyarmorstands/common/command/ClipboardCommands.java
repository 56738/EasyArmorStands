package me.m56738.easyarmorstands.common.command;

import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.clipboard.Clipboard;
import me.m56738.easyarmorstands.common.command.requirement.RequireElement;
import me.m56738.easyarmorstands.common.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.common.command.util.ElementSelection;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import me.m56738.easyarmorstands.common.util.PropertyCopier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;

@CommandContainer
@Command("eas")
public class ClipboardCommands {
    @Command("clipboard")
    @Permission(Permissions.CLIPBOARD)
    @CommandDescription("easyarmorstands.command.description.clipboard")
    public void clipboard(PlayerCommandSource source, Clipboard clipboard) {
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
                .append(type.name())
                .append(Component.text(": "))
                .append(type.getValueComponent(property.getValue()))
                .build();
    }

    @Command("clipboard clear")
    @Permission(Permissions.CLIPBOARD)
    @CommandDescription("easyarmorstands.command.description.clipboard.clear")
    public void clear(PlayerCommandSource source, Clipboard clipboard) {
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
    public void copy(PlayerCommandSource source, Clipboard clipboard, Element element) {
        Player sender = source.source();
        element.getProperties().forEach(property -> {
            if (property.getType().canCopy(sender)) {
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
    public void paste(PlayerCommandSource source, Clipboard clipboard, ElementSelection selection, EasyArmorStandsCommon eas) {
        Player sender = source.source();
        if (clipboard.getProperties().isEmpty()) {
            sender.sendMessage(Message.error("easyarmorstands.error.clipboard-empty"));
            sender.sendMessage(Message.hint("easyarmorstands.hint.copy-property"));
            sender.sendMessage(Message.hint("easyarmorstands.hint.copy-all-properties", Message.command("/eas copy")));
            return;
        }

        PropertyCopier copier = new PropertyCopier();
        try (ManagedChangeContext context = eas.getChangeContextFactory().create(sender)) {
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
