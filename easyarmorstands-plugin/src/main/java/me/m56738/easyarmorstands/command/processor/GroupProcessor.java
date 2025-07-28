package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.common.group.Group;
import me.m56738.easyarmorstands.common.group.node.GroupRootNode;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.paper.util.sender.Source;

import static me.m56738.easyarmorstands.command.processor.SessionProcessor.sessionKey;
import static org.incendo.cloud.key.CloudKey.cloudKey;

public class GroupProcessor implements CommandPreprocessor<Source> {
    private static final CloudKey<Group> KEY = cloudKey("group", Group.class);

    public static CloudKey<Group> groupKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<Source> context) {
        CommandContext<Source> commandContext = context.commandContext();
        if (commandContext.contains(sessionKey())) {
            SessionImpl session = commandContext.get(sessionKey());
            GroupRootNode node = session.findNode(GroupRootNode.class);
            if (node != null) {
                commandContext.set(KEY, node.getGroup());
            }
        }
    }
}
