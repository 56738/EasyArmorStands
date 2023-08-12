package me.m56738.easyarmorstands.command;

import cloud.commandframework.CommandHelpHandler;
import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import me.m56738.easyarmorstands.color.ColorPicker;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@CommandMethod("eas")
public class GlobalCommands {
    private final CommandManager<EasCommandSender> commandManager;
    private final MinecraftHelp<EasCommandSender> help;
    private final SessionManager sessionManager;
    private final SessionListener sessionListener;

    public GlobalCommands(CommandManager<EasCommandSender> commandManager, SessionManager sessionManager, SessionListener sessionListener) {
        this.commandManager = commandManager;
        this.help = new MinecraftHelp<>("/eas help", s -> s, commandManager);
        this.sessionManager = sessionManager;
        this.sessionListener = sessionListener;
    }

    @CommandMethod("")
    @CommandPermission("easyarmorstands.help")
    @CommandDescription("Shows an overview")
    public void showOverview(EasCommandSender sender) {
        if (sender.get().hasPermission("easyarmorstands.version")) {
            String version = EasyArmorStands.getInstance().getDescription().getVersion();
            sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        } else {
            sender.sendMessage(Component.text("EasyArmorStands", NamedTextColor.GOLD));
        }
        if (sender.get().hasPermission("easyarmorstands.give")) {
            sender.sendMessage(Component.text()
                    .content("Use ")
                    .append(Component.text("/eas give", NamedTextColor.YELLOW)
                            .hoverEvent(Component.text("Obtain the editor tool"))
                            .clickEvent(ClickEvent.runCommand("/eas give")))
                    .append(Component.text(" to get started."))
                    .color(NamedTextColor.GRAY));
        }
        sender.sendMessage(Component.text()
                .content("Use ")
                .append(Component.text("/eas help", NamedTextColor.YELLOW)
                        .hoverEvent(Component.text("Show the command list"))
                        .clickEvent(ClickEvent.runCommand("/eas help")))
                .append(Component.text(" to browse available commands."))
                .color(NamedTextColor.GRAY));
    }

    @CommandMethod("help [query]")
    @CommandPermission("easyarmorstands.help")
    @CommandDescription("Shows the command help")
    public void help(EasCommandSender sender,
                     @Argument(value = "query", suggestions = "help_queries") @Greedy String query) {
        help.queryCommands(query != null ? query : "", sender);
    }

    @Suggestions("help_queries")
    public List<String> suggestHelpQueries(CommandContext<EasCommandSender> ctx, String input) {
        return commandManager.createCommandHelpHandler().queryRootIndex(ctx.getSender()).getEntries().stream()
                .map(CommandHelpHandler.VerboseHelpEntry::getSyntaxString)
                .collect(Collectors.toList());
    }

    @CommandMethod("give")
    @CommandPermission("easyarmorstands.give")
    @CommandDescription("Gives you the editor tool")
    public void give(EasPlayer sender) {
        sender.get().getInventory().addItem(Util.createTool());
        sender.sendMessage(Component.text(
                "Tool added to your inventory\n",
                NamedTextColor.GREEN
        ).append(Component.text(
                "Right click an entity to edit it.\n" +
                        "Sneak + right click to spawn an entity.\n" +
                        "Drop to stop editing.",
                NamedTextColor.GRAY
        )));
        sessionListener.updateHeldItem(sender.get());
    }

    @CommandMethod("color")
    @CommandPermission("easyarmorstands.color")
    @CommandDescription("Opens the color picker")
    public void color(EasPlayer player) {
        player.get().openInventory(new ColorPicker(null, player.get()).getInventory());
    }

    @CommandMethod("version")
    @CommandPermission("easyarmorstands.version")
    @CommandDescription("Displays the plugin version")
    public void version(EasCommandSender sender) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        String version = plugin.getDescription().getVersion();
        String url = "https://github.com/56738/EasyArmorStands";
        sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        sender.sendMessage(Component.text(url).clickEvent(ClickEvent.openUrl(url)));
    }

    @CommandMethod("debug")
    @CommandPermission("easyarmorstands.debug")
    @CommandDescription("Displays debug information")
    public void debug(EasCommandSender sender) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        CapabilityLoader loader = plugin.getCapabilityLoader();
        String version = plugin.getDescription().getVersion();
        sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD, TextDecoration.UNDERLINED));
        sender.sendMessage(debugLine(Component.text("Server"), Component.text(Bukkit.getVersion())));
        sender.sendMessage(debugLine(Component.text("Bukkit"), Component.text(Bukkit.getBukkitVersion())));
        for (CapabilityLoader.Entry capability : loader.getCapabilities()) {
            Object instance = capability.getInstance();
            Component value;
            if (instance != null) {
                String packageName = capability.getType().getPackage().getName();
                String providerName = capability.getProvider().getClass().getName();
                if (providerName.startsWith(packageName)) {
                    providerName = providerName.substring(packageName.length());
                }
                value = Component.text(providerName, NamedTextColor.GREEN)
                        .hoverEvent(Component.text(instance.getClass().getName()));
            } else {
                value = Component.text("Not supported", NamedTextColor.RED);
            }
            sender.sendMessage(debugLine(
                    Component.text(capability.getName()).hoverEvent(Component.text(capability.getType().getName())),
                    value
            ));
        }

        if (sender.get() instanceof Player) {
            Session session = sessionManager.getSession(((Player) sender.get()));
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

        TextComponent.Builder hover = Component.text();
        hover.append(debugLine(Component.text("Type"), Component.text(type.getClass().getName())));
        hover.appendNewline();
        hover.append(debugLine(Component.text("Value"), type.getValueComponent(property.getValue())));
        String permission = type.getPermission();
        if (permission != null) {
            hover.appendNewline();
            hover.append(debugLine(Component.text("Permission"), Component.text(permission)));
        }

        if (!builder.children().isEmpty()) {
            builder.append(Component.text(", "));
        }
        builder.append(type.getDisplayName().hoverEvent(hover.build()));
    }

    private Component debugLine(Component key, Component value) {
        return Component.text()
                .append(key.applyFallbackStyle(NamedTextColor.GOLD))
                .append(Component.text(": ", NamedTextColor.GRAY))
                .append(value.applyFallbackStyle(NamedTextColor.GRAY))
                .build();
    }
}
