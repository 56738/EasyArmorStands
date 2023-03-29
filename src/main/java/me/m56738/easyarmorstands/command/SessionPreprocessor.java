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

import java.util.function.Function;

public class SessionPreprocessor<C> implements CommandPreprocessor<C> {
    private static final CloudKey<Session> SESSION_KEY = SimpleCloudKey.of("session", TypeToken.get(Session.class));
    private final SessionManager sessionManager;
    private final Function<C, CommandSender> senderGetter;

    public SessionPreprocessor(SessionManager sessionManager, Function<C, CommandSender> senderGetter) {
        this.sessionManager = sessionManager;
        this.senderGetter = senderGetter;
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
    public void accept(@NonNull CommandPreprocessingContext<C> context) {
        CommandSender sender = senderGetter.apply(context.getCommandContext().getSender());
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        Session session = sessionManager.getSession(player);
        context.getCommandContext().set(SESSION_KEY, session);
    }
}
