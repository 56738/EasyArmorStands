package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.GroupMember;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessingContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessor;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.stream.Collectors;

import static me.m56738.easyarmorstands.command.processor.ElementProcessor.elementKey;
import static me.m56738.easyarmorstands.command.processor.GroupProcessor.groupKey;
import static me.m56738.easyarmorstands.lib.cloud.key.CloudKey.cloudKey;

public class ElementSelectionProcessor implements CommandPreprocessor<EasCommandSender> {
    private static final CloudKey<ElementSelection> KEY = cloudKey("selection", ElementSelection.class);

    public static CloudKey<ElementSelection> elementSelectionKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<EasCommandSender> context) {
        CommandContext<EasCommandSender> commandContext = context.commandContext();

        Element element = commandContext.getOrDefault(elementKey(), null);
        if (element != null) {
            commandContext.set(KEY, new ElementSelection(Collections.singleton(element)));
            return;
        }

        Group group = commandContext.getOrDefault(groupKey(), null);
        if (group != null) {
            commandContext.set(KEY, new ElementSelection(group.getMembers().stream()
                    .map(GroupMember::getElement)
                    .collect(Collectors.toList())));
        }
    }
}
