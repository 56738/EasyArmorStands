package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.execution.postprocessor.CommandPostprocessingContext;
import me.m56738.easyarmorstands.lib.cloud.execution.postprocessor.CommandPostprocessor;
import me.m56738.easyarmorstands.lib.cloud.services.type.ConsumerService;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.List;

public class CommandRequirementPostProcessor implements CommandPostprocessor<EasCommandSender> {
    @Override
    public void accept(@NonNull CommandPostprocessingContext<EasCommandSender> context) {
        CommandContext<@NonNull EasCommandSender> commandContext = context.commandContext();
        List<CommandRequirement> requirements = commandContext.command().commandMeta().getOrDefault(CommandRequirement.KEY, Collections.emptyList());
        for (CommandRequirement requirement : requirements) {
            if (!requirement.evaluateRequirement(commandContext)) {
                requirement.handle(commandContext);
                ConsumerService.interrupt();
            }
        }
    }
}
