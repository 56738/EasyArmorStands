package me.m56738.easyarmorstands.capability.command.v1_8;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.command.CommandCapability;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import org.bukkit.plugin.Plugin;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.bukkit.BukkitCommandManager;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;

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
                        ExecutionCoordinator.simpleCoordinator(),
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
