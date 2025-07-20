package me.m56738.easyarmorstands.common;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.common.command.VersionCommand;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.CommandManager;

public class EasyArmorStandsCommon {
    public static <T extends CommandSource> void registerCommands(CommandManager<T> commandManager, Class<T> commandSenderClass, Platform platform) {
        commandManager.parameterInjectorRegistry().registerInjector(Platform.class,
                (context, annotationAccessor) -> platform);
        commandManager.command(new VersionCommand<>());
    }
}
