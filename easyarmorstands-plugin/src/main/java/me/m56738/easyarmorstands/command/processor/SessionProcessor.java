package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessingContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessor;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.PlayerSource;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.session.SessionImpl;
import org.checkerframework.checker.nullness.qual.NonNull;

import static me.m56738.easyarmorstands.lib.cloud.key.CloudKey.cloudKey;

public class SessionProcessor implements CommandPreprocessor<Source> {
    private static final CloudKey<SessionImpl> KEY = cloudKey("session", SessionImpl.class);

    public static CloudKey<SessionImpl> sessionKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<Source> context) {
        CommandContext<Source> commandContext = context.commandContext();
        Source sender = commandContext.sender();
        if (sender instanceof PlayerSource playerSource) {
            SessionImpl session = EasyArmorStandsPlugin.getInstance().sessionManager().getSession(playerSource.source());
            commandContext.set(KEY, session);
        }
    }
}
