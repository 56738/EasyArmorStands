package me.m56738.easyarmorstands.capability.command.v1_8;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.command.CommandCapability;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.CommandManager;
import me.m56738.easyarmorstands.lib.cloud.bukkit.BukkitCommandManager;
import me.m56738.easyarmorstands.lib.cloud.bukkit.CloudBukkitCapabilities;
import me.m56738.easyarmorstands.lib.cloud.execution.ExecutionCoordinator;
import me.m56738.easyarmorstands.lib.cloud.paper.LegacyPaperCommandManager;
import me.m56738.easyarmorstands.util.MainThreadExecutor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class CommandCapabilityProvider implements CapabilityProvider<CommandCapability> {
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.FALLBACK;
    }

    @Override
    public CommandCapability create(Plugin plugin) {
        return new CommandCapabilityImpl(plugin);
    }

    private static class CommandCapabilityImpl implements CommandCapability {
        private final Plugin plugin;

        public CommandCapabilityImpl(Plugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public CommandManager<EasCommandSender> createCommandManager() {
            LegacyPaperCommandManager<EasCommandSender> commandManager;
            try {
                commandManager = new LegacyPaperCommandManager<>(
                        plugin,
                        ExecutionCoordinator.coordinatorFor(new MainThreadExecutor(plugin)),
                        new CommandSenderMapper(EasyArmorStandsPlugin.getInstance().getAdventure()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
                try {
                    commandManager.registerBrigadier();
                } catch (BukkitCommandManager.BrigadierInitializationException e) {
                    plugin.getLogger().log(Level.WARNING, "Failed to register Brigadier mappings");
                } catch (Throwable e) {
                    plugin.getLogger().log(Level.SEVERE, "Failed to register Brigadier mappings", e);
                }
            }

            return commandManager;
        }
    }
}
