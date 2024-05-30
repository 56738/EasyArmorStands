package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.processors.requirements.RequirementFailureHandler;

public class CommandRequirementFailureHandler implements RequirementFailureHandler<EasCommandSender, CommandRequirement> {
    @Override
    public void handleFailure(@NonNull CommandContext<EasCommandSender> context, @NonNull CommandRequirement requirement) {
        requirement.handle(context);
    }
}
