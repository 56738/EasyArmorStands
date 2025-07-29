package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.postprocessor.CommandPostprocessingContext;
import org.incendo.cloud.execution.postprocessor.CommandPostprocessor;
import org.incendo.cloud.services.type.ConsumerService;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.List;

@NullMarked
public class CommandRequirementPostProcessor implements CommandPostprocessor<CommandSource> {
    @Override
    public void accept(CommandPostprocessingContext<CommandSource> context) {
        CommandContext<CommandSource> commandContext = context.commandContext();
        List<CommandRequirement> requirements = commandContext.command().commandMeta().getOrDefault(CommandRequirement.KEY, Collections.emptyList());
        for (CommandRequirement requirement : requirements) {
            if (!requirement.evaluateRequirement(commandContext)) {
                requirement.handle(commandContext);
                ConsumerService.interrupt();
            }
        }
    }
}
