package gg.bundlegroup.easyarmorstands.common.platform.test;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.meta.SimpleCommandMeta;
import gg.bundlegroup.easyarmorstands.common.platform.EasCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TestCommandManager extends CommandManager<EasCommandSender> {
    protected TestCommandManager() {
        super(CommandExecutionCoordinator.simpleCoordinator(), new TestCommandRegistrationHandler());
    }

    @Override
    public boolean hasPermission(@NonNull EasCommandSender sender, @NonNull String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public @NonNull CommandMeta createDefaultCommandMeta() {
        return SimpleCommandMeta.empty();
    }
}
