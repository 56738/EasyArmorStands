package me.m56738.easyarmorstands.capability.command.v1_20_6_paper;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.command.CommandCapability;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.CommandManager;
import me.m56738.easyarmorstands.lib.cloud.execution.ExecutionCoordinator;
import me.m56738.easyarmorstands.lib.cloud.paper.PaperCommandManager;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.plugin.Plugin;

public class CommandCapabilityProvider implements CapabilityProvider<CommandCapability> {
    @Override
    public boolean isSupported() {
        try {
            Plugin.class.getMethod("getLifecycleManager");
            return ReflectionUtil.hasClass("io.papermc.paper.command.brigadier.CommandSourceStack");
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public CommandCapability create(Plugin plugin) {
        return new CommandCapabilityImpl(plugin);
    }

    public static class CommandCapabilityImpl implements CommandCapability {
        private final Plugin plugin;

        public CommandCapabilityImpl(Plugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public CommandManager<EasCommandSender> createCommandManager() {
            CommandSourceStackMapper mapper = new CommandSourceStackMapper(new CommandSenderMapper(EasyArmorStandsPlugin.getInstance().getAdventure()));
            return PaperCommandManager.builder(mapper)
                    .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                    .buildOnEnable(plugin);
        }
    }
}
