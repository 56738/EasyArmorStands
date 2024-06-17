package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.annotations.BuilderModifier;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CommandRequirementBuilderModifier<A extends Annotation> implements BuilderModifier<A, EasCommandSender> {
    private final Function<A, CommandRequirement> requirementProvider;

    public CommandRequirementBuilderModifier(Function<A, CommandRequirement> requirementProvider) {
        this.requirementProvider = requirementProvider;
    }

    @Override
    public Command.Builder<? extends EasCommandSender> modifyBuilder(@NonNull A annotation, Command.Builder<EasCommandSender> builder) {
        List<CommandRequirement> requirements = new ArrayList<>(builder.meta().getOrDefault(CommandRequirement.KEY, Collections.emptyList()));
        addRequirement(requirementProvider.apply(annotation), requirements);
        return builder.meta(CommandRequirement.KEY, requirements);
    }

    private void addRequirement(CommandRequirement requirement, List<CommandRequirement> destination) {
        for (CommandRequirement parent : requirement.parents()) {
            addRequirement(parent, destination);
        }
        destination.add(requirement);
    }
}
