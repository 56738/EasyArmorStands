package me.m56738.easyarmorstands.command.global;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.session.SessionImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;

import java.util.Objects;

@CommandContainer
public class DebugCommand {
    @Command("eas debug")
    @Permission(Permissions.DEBUG)
    @CommandDescription("easyarmorstands.command.description.debug")
    public void debug(EasCommandSender sender) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        String version = plugin.getPluginMeta().getVersion();
        sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD, TextDecoration.UNDERLINED));
        sender.sendMessage(debugLine(Component.text("Server"), Component.text()
                .append(Component.text(Bukkit.getName()))
                .appendSpace()
                .append(Component.text(Bukkit.getVersion()))
                .build()));
        sender.sendMessage(debugLine(Component.text("Bukkit"), Component.text(Bukkit.getBukkitVersion())));

        if (sender instanceof EasPlayer) {
            SessionImpl session = ((EasPlayer) sender).session();
            Element element = null;
            if (session != null) {
                sender.sendMessage(Component.text("Current session:", NamedTextColor.GOLD));
                boolean first = true;
                for (Node node : session.getNodeStack()) {
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
                element.getProperties().forEach(property -> {
                    try {
                        appendPropertyDebug(builder, property);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to process property: " + property.getType().key(), e);
                    }
                });
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
        String permission = type.getPermission();
        if (permission != null) {
            hover.appendNewline();
            hover.append(debugLine(Component.text("Permission"), Component.text(permission)));
        }

        if (!builder.children().isEmpty()) {
            builder.append(Component.text(", "));
        }
        builder.append(type.getName().hoverEvent(hover.build()).clickEvent(ClickEvent.copyToClipboard(type.getValueString(value))));
    }

    private Component debugLine(Component key, Component value) {
        return Component.text()
                .append(key.applyFallbackStyle(NamedTextColor.GOLD))
                .append(Component.text(": ", NamedTextColor.GRAY))
                .append(value.applyFallbackStyle(NamedTextColor.GRAY))
                .build();
    }
}
