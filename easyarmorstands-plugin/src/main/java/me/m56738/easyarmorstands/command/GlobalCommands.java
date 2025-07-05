package me.m56738.easyarmorstands.command;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.lib.cloud.CommandManager;
import me.m56738.easyarmorstands.lib.cloud.annotation.specifier.Greedy;
import me.m56738.easyarmorstands.lib.cloud.annotations.Argument;
import me.m56738.easyarmorstands.lib.cloud.annotations.Command;
import me.m56738.easyarmorstands.lib.cloud.annotations.CommandDescription;
import me.m56738.easyarmorstands.lib.cloud.annotations.Permission;
import me.m56738.easyarmorstands.lib.cloud.annotations.suggestion.Suggestions;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.help.result.CommandEntry;
import me.m56738.easyarmorstands.lib.cloud.minecraft.extras.MinecraftHelp;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.TextComponent;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.event.ClickEvent;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.TextDecoration;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.session.SessionImpl;
import me.m56738.easyarmorstands.session.SessionListener;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Command("eas")
public class GlobalCommands {
    private final CommandManager<EasCommandSender> commandManager;
    private final MinecraftHelp<EasCommandSender> help;
    private final SessionListener sessionListener;

    public GlobalCommands(CommandManager<EasCommandSender> commandManager, SessionListener sessionListener) {
        this.commandManager = commandManager;
        this.help = MinecraftHelp.createNative("/eas help", commandManager);
        this.sessionListener = sessionListener;
    }

    @Command("")
    @Permission(Permissions.HELP)
    @CommandDescription("easyarmorstands.command.description")
    public void showOverview(EasCommandSender sender) {
        if (sender.get().hasPermission(Permissions.VERSION)) {
            String version = EasyArmorStandsPlugin.getInstance().getDescription().getVersion();
            sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        } else {
            sender.sendMessage(Component.text("EasyArmorStands", NamedTextColor.GOLD));
        }
        if (sender.get().hasPermission(Permissions.GIVE)) {
            sender.sendMessage(Message.hint("easyarmorstands.hint.give-tool", Message.command("/eas give")));
        }
        sender.sendMessage(Message.hint("easyarmorstands.hint.show-help", Message.command("/eas help")));
    }

    @Command("help [query]")
    @Permission(Permissions.HELP)
    @CommandDescription("easyarmorstands.command.description.help")
    public void help(EasCommandSender sender,
                     @Argument(value = "query", suggestions = "help_queries") @Greedy String query) {
        help.queryCommands(query != null ? query : "", sender);
    }

    @Suggestions("help_queries")
    public List<String> suggestHelpQueries(CommandContext<EasCommandSender> ctx, String input) {
        return commandManager.createHelpHandler().queryRootIndex(ctx.sender()).entries().stream()
                .map(CommandEntry::syntax)
                .collect(Collectors.toList());
    }

    @Command("give")
    @Permission(Permissions.GIVE)
    @CommandDescription("easyarmorstands.command.description.give")
    public void give(EasPlayer sender) {
        sender.get().getInventory().addItem(EasyArmorStandsPlugin.getInstance().createTool(sender.locale()));
        sender.sendMessage(Message.success("easyarmorstands.success.added-tool-to-inventory"));
        sender.sendMessage(Message.hint("easyarmorstands.hint.select-entity"));
        if (sender.get().hasPermission(Permissions.SPAWN)) {
            sender.sendMessage(Message.hint("easyarmorstands.hint.spawn-entity"));
        }
        sender.sendMessage(Message.hint("easyarmorstands.hint.deselect-entity"));
        sessionListener.updateHeldItem(sender.get());
    }

    @Command("reload")
    @Permission(Permissions.RELOAD)
    @CommandDescription("easyarmorstands.command.description.reload")
    public void reloadConfig(EasCommandSender sender) {
        EasyArmorStandsPlugin.getInstance().reload();
        sender.sendMessage(Message.success("easyarmorstands.success.reloaded-config"));
    }

    @Command("version")
    @Permission(Permissions.VERSION)
    @CommandDescription("easyarmorstands.command.description.version")
    public void version(EasCommandSender sender) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        String version = plugin.getDescription().getVersion();
        String url = "https://github.com/56738/EasyArmorStands";
        sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        sender.sendMessage(Component.text(url).clickEvent(ClickEvent.openUrl(url)));
    }

    @Command("debug")
    @Permission(Permissions.DEBUG)
    @CommandDescription("easyarmorstands.command.description.debug")
    public void debug(EasCommandSender sender) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        CapabilityLoader loader = plugin.getCapabilityLoader();
        String version = plugin.getDescription().getVersion();
        sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD, TextDecoration.UNDERLINED));
        sender.sendMessage(debugLine(Component.text("Server"), Component.text(Bukkit.getVersion())));
        sender.sendMessage(debugLine(Component.text("Bukkit"), Component.text(Bukkit.getBukkitVersion())));
        for (CapabilityLoader.Entry capability : loader.getCapabilities()) {
            Object instance = capability.getInstance();
            int attempts = capability.getAttempts();
            Component value;
            if (instance != null) {
                String packageName = capability.getType().getPackage().getName();
                String providerName = capability.getProvider().getClass().getName();
                if (providerName.startsWith(packageName)) {
                    providerName = providerName.substring(packageName.length());
                }
                NamedTextColor color;
                if (attempts > 1) {
                    color = NamedTextColor.YELLOW;
                } else {
                    color = NamedTextColor.GREEN;
                }
                value = Component.text(providerName, color)
                        .hoverEvent(Component.text(instance.getClass().getName()));
            } else {
                value = Component.text("Not supported", NamedTextColor.RED);
            }
            sender.sendMessage(debugLine(
                    Component.text(capability.getName()).hoverEvent(Component.text(capability.getType().getName())),
                    value
            ));
        }

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
