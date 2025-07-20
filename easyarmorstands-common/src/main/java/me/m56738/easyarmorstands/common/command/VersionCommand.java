package me.m56738.easyarmorstands.common.command;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.CommandSender;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandFactory;
import org.incendo.cloud.CommandManager;

import java.util.List;

public class VersionCommand<C extends CommandSource> implements CommandFactory<C> {
    @Override
    public List<Command<? extends C>> createCommands(CommandManager<C> commandManager) {
        return List.of(
                commandManager.commandBuilder("eas")
                        .literal("version")
                        .permission("easyarmorstands.version")
                        .handler(context -> {
                            CommandSender sender = context.sender().source();
                            Platform platform = context.inject(Platform.class).orElseThrow();
                            String version = platform.getEasyArmorStandsVersion();
                            String url = "https://github.com/56738/EasyArmorStands";
                            sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
                            sender.sendMessage(Component.text(url).clickEvent(ClickEvent.openUrl(url)));
                        }).build()
        );
    }
}
