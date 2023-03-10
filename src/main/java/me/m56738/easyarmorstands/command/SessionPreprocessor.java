package me.m56738.easyarmorstands.command;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessor;
import cloud.commandframework.keys.CloudKey;
import cloud.commandframework.keys.SimpleCloudKey;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SessionPreprocessor implements CommandPreprocessor<CommandSender> {
    private static final CloudKey<Session> SESSION_KEY = SimpleCloudKey.of("session", TypeToken.get(Session.class));
    private final SessionManager sessionManager;

    public SessionPreprocessor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public static Session getSessionOrNull(CommandContext<?> context) {
        return context.getOrDefault(SessionPreprocessor.SESSION_KEY, null);
    }

    public static Session getSession(CommandContext<?> context) {
        Session session = getSessionOrNull(context);
        if (session == null) {
            throw new NoSessionException();
        }
        return session;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<CommandSender> context) {
        CommandSender sender = context.getCommandContext().getSender();
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        Session session = sessionManager.getSession(player);
        context.getCommandContext().set(SESSION_KEY, session);
    }
}
