package gg.bundlegroup.easyarmorstands;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import gg.bundlegroup.easyarmorstands.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.session.SessionListener;
import gg.bundlegroup.easyarmorstands.session.SessionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.Closeable;

public class Main implements Closeable {
    private final SessionManager sessionManager;

    public Main(EasPlatform platform) {
        this.sessionManager = new SessionManager();
        CommandManager<EasCommandSender> commandManager = platform.commandManager();
        platform.registerListener(new SessionListener(sessionManager));
        platform.registerTickTask(sessionManager::update);
        Command.Builder<EasCommandSender> commandBuilder = commandManager.commandBuilder("eas");
        commandManager.command(
                commandBuilder.literal("give")
                        .permission("easyarmorstands.edit")
                        .senderType(EasPlayer.class)
                        .handler(context -> {
                            EasPlayer player = (EasPlayer) context.getSender();
                            player.giveTool();
                            player.sendMessage(Component.text("Tool added to your inventory", NamedTextColor.GREEN));
                        })
        );
    }

    @Override
    public void close() {
        sessionManager.stopAllSessions();
    }
}
