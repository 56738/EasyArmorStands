package me.m56738.easyarmorstands.common.command;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.CommandSender;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.editor.SessionListener;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;

import java.util.Objects;

@CommandContainer
@Command("eas")
public class GlobalCommands {
    @Command("")
    @Permission(Permissions.HELP)
    @CommandDescription("easyarmorstands.command.description")
    public void showOverview(CommandSource source, EasyArmorStandsCommon eas) {
        CommandSender sender = source.source();
        if (sender.hasPermission(Permissions.VERSION)) {
            String version = eas.getVersion();
            sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        } else {
            sender.sendMessage(Component.text("EasyArmorStands", NamedTextColor.GOLD));
        }
        if (sender.hasPermission(Permissions.GIVE)) {
            sender.sendMessage(Message.hint("easyarmorstands.hint.give-tool", Message.command("/eas give")));
        }
        if (sender.hasPermission(Permissions.HELP)) {
            sender.sendMessage(Message.hint("easyarmorstands.hint.show-help", Message.command("/eas help")));
        }
    }

    @Command("give")
    @Permission(Permissions.GIVE)
    @CommandDescription("easyarmorstands.command.description.give")
    public void give(PlayerCommandSource source, Platform platform, SessionListener sessionListener) {
        Player sender = source.source();
        sender.giveItem(platform.createTool());
        sender.sendMessage(Message.success("easyarmorstands.success.added-tool-to-inventory"));
        sender.sendMessage(Message.hint("easyarmorstands.hint.select-entity"));
        if (sender.hasPermission(Permissions.SPAWN)) {
            sender.sendMessage(Message.hint("easyarmorstands.hint.spawn-entity"));
        }
        sender.sendMessage(Message.hint("easyarmorstands.hint.deselect-entity"));
        sessionListener.updateHeldItem(sender);
    }

//    @Command("reload") TODO
//    @Permission(Permissions.RELOAD)
//    @CommandDescription("easyarmorstands.command.description.reload")
//    public void reloadConfig(CommandSource source) {
//        CommandSender sender = source.source();
//        EasyArmorStandsPlugin.getInstance().reload();
//        sender.sendMessage(Message.success("easyarmorstands.success.reloaded-config"));
//    }

    @Command("version")
    @Permission(Permissions.VERSION)
    @CommandDescription("easyarmorstands.command.description.version")
    public void version(CommandSource source, EasyArmorStandsCommon eas) {
        CommandSender sender = source.source();
        String version = eas.getVersion();
        String url = "https://github.com/56738/EasyArmorStands";
        sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        sender.sendMessage(Component.text(url).clickEvent(ClickEvent.openUrl(url)));
    }

    @Command("debug")
    @Permission(Permissions.DEBUG)
    @CommandDescription("easyarmorstands.command.description.debug")
    public void debug(CommandSource source, EasyArmorStandsCommon eas, SessionManager sessionManager) {
        CommandSender sender = source.source();
        String version = eas.getVersion();
        sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD, TextDecoration.UNDERLINED));

        if (sender instanceof Player player) {
            Session session = sessionManager.getSession(player);
            Element element = null;
            if (session != null) {
                sender.sendMessage(Component.text("Current session:", NamedTextColor.GOLD));
                boolean first = true;
                for (Node node : session.getAllNodes()) {
                    sender.sendMessage(
                            Component.text("* ", first ? NamedTextColor.GREEN : NamedTextColor.GRAY)
                                    .append(Component.text(node.getClass().getSimpleName())
                                            .hoverEvent(Component.text(node.getClass().getName()))));
                    first = false;
                }
                element = session.getElement();
            }
            if (element != null) {
                sender.sendMessage(Component.text("Selected element: ", NamedTextColor.GOLD)
                        .append(Component.text(element.getClass().getSimpleName(), NamedTextColor.GREEN)
                                .hoverEvent(Component.text(element.getClass().getName()))));
                TextComponent.Builder builder = Component.text();
                builder.color(NamedTextColor.GRAY);
                element.getProperties().forEach(property -> appendPropertyDebug(builder, property));
                sender.sendMessage(builder);
            }
        }
    }

    private <T> void appendPropertyDebug(TextComponent.Builder builder, Property<T> property) {
        PropertyType<T> type = property.getType();
        T value = Objects.requireNonNull(property.getValue());

        TextComponent.Builder hover = Component.text();
        hover.append(Component.text(type.key().namespace() + ':', NamedTextColor.YELLOW)
                .append(Component.text(type.key().value(), NamedTextColor.GOLD)));
        hover.appendNewline();
        hover.append(debugLine(Component.text("Type"), Component.text(type.getClass().getName())));
        hover.appendNewline();
        hover.append(debugLine(Component.text("Value"), type.getValueComponent(value)));
        String permission = type.permission();
        if (permission != null) {
            hover.appendNewline();
            hover.append(debugLine(Component.text("Permission"), Component.text(permission)));
        }

        if (!builder.children().isEmpty()) {
            builder.append(Component.text(", "));
        }
        builder.append(type.name().hoverEvent(hover.build()).clickEvent(ClickEvent.copyToClipboard(type.getValueString(value))));
    }

    private Component debugLine(Component key, Component value) {
        return Component.text()
                .append(key.applyFallbackStyle(NamedTextColor.GOLD))
                .append(Component.text(": ", NamedTextColor.GRAY))
                .append(value.applyFallbackStyle(NamedTextColor.GRAY))
                .build();
    }
}
