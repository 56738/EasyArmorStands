package gg.bundlegroup.easyarmorstands.plugin;

import cloud.commandframework.Command;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.BukkitCommandManager.BrigadierFailureException;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import cloud.commandframework.paper.PaperCommandManager;
import gg.bundlegroup.easyarmorstands.module.Module;
import gg.bundlegroup.easyarmorstands.module.ModuleFactory;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    private BukkitAudiences adventure;
    private BukkitCommandManager<CommandSender> commandManager;
    private MinecraftHelp<CommandSender> minecraftHelp;
    private SessionManager sessionManager;
    private final List<Module> modules = new ArrayList<>();

    public @NotNull BukkitAudiences getAdventure() {
        return Objects.requireNonNull(adventure);
    }

    @Override
    public void onEnable() {
        adventure = BukkitAudiences.create(this);

        try {
            commandManager = new PaperCommandManager<>(
                    this,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    Function.identity(),
                    Function.identity()
            );
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to initialize command manager", e);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            try {
                commandManager.registerBrigadier();
            } catch (BrigadierFailureException e) {
                getLogger().warning("Failed to register Brigadier support");
            }
        }

        minecraftHelp = new MinecraftHelp<>(
                "/eas help",
                adventure::sender,
                commandManager
        );

        new MinecraftExceptionHandler<CommandSender>()
                .withInvalidSyntaxHandler()
                .withInvalidSenderHandler()
                .withNoPermissionHandler()
                .withArgumentParsingHandler()
                .withCommandExecutionHandler()
                .apply(commandManager, adventure::sender);

        sessionManager = new SessionManager(this);
        getServer().getPluginManager().registerEvents(new SessionListener(sessionManager, adventure), this);

        registerCommands();

        for (ModuleFactory factory : ServiceLoader.load(ModuleFactory.class, getClassLoader())) {
            if (factory.supported()) {
                getLogger().info("Enabling module " + factory.name());
                Module module;
                try {
                    module = factory.create(this);
                } catch (Exception e) {
                    getLogger().log(Level.SEVERE, "Failed to enable module " + factory.name());
                    continue;
                }
                modules.add(module);
            } else {
                getLogger().info("Module not supported: " + factory.name());
            }
        }
    }

    @Override
    public void onDisable() {
        for (Module module : modules) {
            try {
                getLogger().info("Disabling module " + module.name());
                module.disable();
            } catch (Exception e) {
                getLogger().log(Level.SEVERE, "Failed to disable module " + module.name());
            }
        }
        modules.clear();

        if (sessionManager != null) {
            sessionManager.stopAllSessions();
            sessionManager = null;
        }

        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    private void registerCommands() {
        final Command.Builder<CommandSender> builder = commandManager.commandBuilder("eas", "easyarmorstands");

        commandManager.command(builder
                .literal("help")
                .argument(StringArgument.optional("query", StringArgument.StringMode.GREEDY))
                .permission("easyarmorstands.help")
                .handler(context -> {
                    final String query = context.getOrDefault("query", "");
                    minecraftHelp.queryCommands(query, context.getSender());
                })
        );

        commandManager.command(builder
                .literal("edit")
                .senderType(Player.class)
                .permission("easyarmorstands.edit")
                .handler(context -> {
                    final Player sender = (Player) context.getSender();
                    sender.getInventory().addItem(new ItemStack(Material.STICK));
                    sender.sendMessage("Right click an armor stand with a stick to start editing it");
                    sender.sendMessage("Hold right click and drag the end of a bone");
                })
        );
    }
}
