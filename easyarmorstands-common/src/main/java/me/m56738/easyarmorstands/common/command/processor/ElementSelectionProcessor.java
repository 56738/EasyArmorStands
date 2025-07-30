package me.m56738.easyarmorstands.common.command.processor;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.common.command.util.ElementSelection;
import me.m56738.easyarmorstands.common.group.Group;
import me.m56738.easyarmorstands.common.group.GroupMember;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;
import org.incendo.cloud.key.CloudKey;

import java.util.Collections;
import java.util.stream.Collectors;

import static me.m56738.easyarmorstands.common.command.processor.ElementProcessor.elementKey;
import static me.m56738.easyarmorstands.common.command.processor.GroupProcessor.groupKey;
import static org.incendo.cloud.key.CloudKey.cloudKey;

public class ElementSelectionProcessor implements CommandPreprocessor<CommandSource> {
    private static final CloudKey<ElementSelection> KEY = cloudKey("selection", ElementSelection.class);

    public static CloudKey<ElementSelection> elementSelectionKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<CommandSource> context) {
        CommandContext<CommandSource> commandContext = context.commandContext();

        if (commandContext.contains(elementKey())) {
            Element element = commandContext.get(elementKey());
            commandContext.set(KEY, new ElementSelection(Collections.singleton(element)));
            return;
        }

        if (commandContext.contains(groupKey())) {
            Group group = commandContext.get(groupKey());
            commandContext.set(KEY, new ElementSelection(group.getMembers().stream()
                    .map(GroupMember::getElement)
                    .collect(Collectors.toList())));
        }
    }
}
