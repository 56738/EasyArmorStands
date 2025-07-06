package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.execution.postprocessor.CommandPostprocessingContext;
import me.m56738.easyarmorstands.lib.cloud.execution.postprocessor.CommandPostprocessor;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.lib.cloud.services.type.ConsumerService;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.List;

@NullMarked
public class CommandRequirementPostProcessor implements CommandPostprocessor<Source> {
    @Override
    public void accept(CommandPostprocessingContext<Source> context) {
        CommandContext<Source> commandContext = context.commandContext();
        List<CommandRequirement> requirements = commandContext.command().commandMeta().getOrDefault(CommandRequirement.KEY, Collections.emptyList());
        for (CommandRequirement requirement : requirements) {
            if (!requirement.evaluateRequirement(commandContext)) {
                requirement.handle(commandContext);
                ConsumerService.interrupt();
            }
        }
    }
}
