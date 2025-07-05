package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessingContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessor;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import me.m56738.easyarmorstands.session.SessionImpl;
import org.checkerframework.checker.nullness.qual.NonNull;

import static me.m56738.easyarmorstands.lib.cloud.key.CloudKey.cloudKey;

public class SessionProcessor implements CommandPreprocessor<EasCommandSender> {
    private static final CloudKey<SessionImpl> KEY = cloudKey("session", SessionImpl.class);

    public static CloudKey<SessionImpl> sessionKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<EasCommandSender> context) {
        CommandContext<EasCommandSender> commandContext = context.commandContext();
        EasCommandSender sender = commandContext.sender();
        if (sender instanceof EasPlayer) {
            SessionImpl session = ((EasPlayer) sender).session();
            commandContext.set(KEY, session);
        }
    }
}
