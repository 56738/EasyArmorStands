package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.session.SessionImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;
import org.incendo.cloud.key.CloudKey;

import static me.m56738.easyarmorstands.command.processor.SessionProcessor.sessionKey;
import static org.incendo.cloud.key.CloudKey.cloudKey;

public class GroupProcessor implements CommandPreprocessor<EasCommandSender> {
    private static final CloudKey<Group> KEY = cloudKey("group", Group.class);

    public static CloudKey<Group> groupKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<EasCommandSender> context) {
        CommandContext<EasCommandSender> commandContext = context.commandContext();
        SessionImpl session = commandContext.getOrDefault(sessionKey(), null);
        if (session != null) {
            GroupRootNode node = session.findNode(GroupRootNode.class);
            if (node != null) {
                commandContext.set(KEY, node.getGroup());
            }
        }
    }
}
