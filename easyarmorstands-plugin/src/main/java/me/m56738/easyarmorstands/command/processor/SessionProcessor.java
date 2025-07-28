package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;

import static org.incendo.cloud.key.CloudKey.cloudKey;

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
            SessionImpl session = EasyArmorStandsPlugin.getInstance().sessionManager().getSession(PaperPlayer.fromNative(playerSource.source()));
            commandContext.set(KEY, session);
        }
    }
}
