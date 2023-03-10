package me.m56738.easyarmorstands.command;

import cloud.commandframework.CommandHelpHandler;
import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.specifier.Range;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import me.m56738.easyarmorstands.color.ColorPicker;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryAction;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@CommandMethod("eas")
public class GlobalCommands {
    private final CommandManager<CommandSender> commandManager;
    private final MinecraftHelp<CommandSender> help;

    public GlobalCommands(CommandManager<CommandSender> commandManager, BukkitAudiences adventure) {
        this.commandManager = commandManager;
        this.help = new MinecraftHelp<>("/eas help", adventure::sender, commandManager);
    }

    @CommandMethod("help [query]")
    @CommandPermission("easyarmorstands.help")
    @CommandDescription("Shows the command help")
    public void help(CommandSender sender,
                     @Argument(value = "query", suggestions = "help_queries") @Greedy String query) {
        help.queryCommands(query != null ? query : "", sender);
    }

    @Suggestions("help_queries")
    public List<String> suggestHelpQueries(CommandContext<CommandSender> ctx, String input) {
        return commandManager.createCommandHelpHandler().queryRootIndex(ctx.getSender()).getEntries().stream()
                .map(CommandHelpHandler.VerboseHelpEntry::getSyntaxString)
                .collect(Collectors.toList());
    }

    @CommandMethod("give")
    @CommandPermission("easyarmorstands.give")
    @CommandDescription("Gives you the editor tool")
    public void give(Player sender, Audience audience) {
        sender.getInventory().addItem(Util.createTool());
        audience.sendMessage(Component.text(
                "Tool added to your inventory\n",
                NamedTextColor.GREEN
        ).append(Component.text(
                "Right click an armor stand to start editing.\n" +
                        "Sneak + right click to spawn an armor stand.\n" +
                        "Drop to stop editing.",
                NamedTextColor.GRAY
        )));
    }

    @CommandMethod("color")
    @CommandPermission("easyarmorstands.color")
    @CommandDescription("Displays the color picker")
    public void color(Player player) {
        player.openInventory(new ColorPicker(null, player).getInventory());
    }

    @CommandMethod("undo [count]")
    @CommandPermission("easyarmorstands.undo")
    @CommandDescription("Undo a change")
    public void undo(Player player, Audience audience,
                     @Range(min = "1", max = "10") @Argument(value = "count", defaultValue = "1") int count) {
        History history = EasyArmorStands.getInstance().getHistory(player);
        for (int i = 0; i < count; i++) {
            HistoryAction action = history.takeUndoAction();
            if (action != null) {
                try {
                    action.undo();
                } catch (IllegalStateException e) {
                    audience.sendMessage(Component.text("Unable to undo change: ", NamedTextColor.RED)
                            .append(action.describe()));
                    break;
                }
                audience.sendMessage(Component.text()
                        .append(Component.text("Undone change: ", NamedTextColor.GREEN))
                        .append(action.describe()));
            } else {
                audience.sendMessage(Component.text("No changes left to undo", NamedTextColor.RED));
                break;
            }
        }
    }

    @CommandMethod("redo [count]")
    @CommandPermission("easyarmorstands.redo")
    @CommandDescription("Redo a change")
    public void redo(Player player, Audience audience,
                     @Range(min = "1", max = "10") @Argument(value = "count", defaultValue = "1") int count) {
        History history = EasyArmorStands.getInstance().getHistory(player);
        for (int i = 0; i < count; i++) {
            HistoryAction action = history.takeRedoAction();
            if (action != null) {
                try {
                    action.redo();
                } catch (IllegalStateException e) {
                    audience.sendMessage(Component.text("Unable to redo change: ", NamedTextColor.RED)
                            .append(action.describe()));
                    break;
                }
                audience.sendMessage(Component.text()
                        .append(Component.text("Redone change: ", NamedTextColor.GREEN))
                        .append(action.describe()));
            } else {
                audience.sendMessage(Component.text("No changes left to redo", NamedTextColor.RED));
                break;
            }
        }
    }

    @CommandMethod("version")
    @CommandPermission("easyarmorstands.version")
    @CommandDescription("Displays the plugin version")
    public void version(Audience audience) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        String version = plugin.getDescription().getVersion();
        String url = "https://github.com/56738/EasyArmorStands";
        audience.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        audience.sendMessage(Component.text(url).clickEvent(ClickEvent.openUrl(url)));
    }

    @CommandMethod("debug")
    @CommandPermission("easyarmorstands.debug")
    @CommandDescription("Displays debug information")
    public void debug(Audience audience) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        CapabilityLoader loader = plugin.getCapabilityLoader();
        String version = plugin.getDescription().getVersion();
        audience.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        audience.sendMessage(debugLine(Component.text("Server"), Component.text(Bukkit.getVersion())));
        audience.sendMessage(debugLine(Component.text("Bukkit"), Component.text(Bukkit.getBukkitVersion())));
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
            audience.sendMessage(debugLine(
                    Component.text(capability.getName()).hoverEvent(Component.text(capability.getType().getName())),
                    value
            ));
        }
    }

    private Component debugLine(Component key, Component value) {
        return Component.text()
                .append(key.applyFallbackStyle(NamedTextColor.GOLD))
                .append(Component.text(": ", NamedTextColor.GRAY))
                .append(value.applyFallbackStyle(NamedTextColor.GRAY))
                .build();
    }
}
