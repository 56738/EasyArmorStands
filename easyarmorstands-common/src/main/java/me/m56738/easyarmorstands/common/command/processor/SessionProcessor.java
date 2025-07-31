package me.m56738.easyarmorstands.common.command.processor;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;
import org.incendo.cloud.key.CloudKey;

import static org.incendo.cloud.key.CloudKey.cloudKey;

public class SessionProcessor implements CommandPreprocessor<CommandSource> {
    private static final CloudKey<SessionImpl> KEY = cloudKey("session", SessionImpl.class);

    public static CloudKey<SessionImpl> sessionKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<CommandSource> context) {
        CommandContext<CommandSource> commandContext = context.commandContext();
        SessionManager sessionManager = commandContext.inject(SessionManager.class).orElseThrow();
        if (commandContext.sender() instanceof PlayerCommandSource playerSource) {
            Session session = sessionManager.getSession(playerSource.source());
            commandContext.set(KEY, (SessionImpl) session);
        }
    }
}
