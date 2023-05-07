package me.m56738.easyarmorstands.command;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessor;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

public class SessionPreprocessor<C> implements CommandPreprocessor<C> {
    private final SessionManager sessionManager;
    private final Function<C, CommandSender> senderGetter;

    public SessionPreprocessor(SessionManager sessionManager, Function<C, CommandSender> senderGetter) {
        this.sessionManager = sessionManager;
        this.senderGetter = senderGetter;
    }

    public static Session getSessionOrNull(CommandContext<?> context) {
        return context.getOrDefault(Keys.SESSION, null);
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<C> context) {
        CommandSender sender = senderGetter.apply(context.getCommandContext().getSender());
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        Session session = sessionManager.getSession(player);
        context.getCommandContext().set(Keys.SESSION, session);
    }
}
