package me.m56738.easyarmorstands.command.requirement;

import org.incendo.cloud.Command;
import org.incendo.cloud.annotations.BuilderModifier;
import org.incendo.cloud.paper.util.sender.Source;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@NullMarked
public class CommandRequirementBuilderModifier<A extends Annotation> implements BuilderModifier<A, Source> {
    private final Function<A, CommandRequirement> requirementProvider;

    public CommandRequirementBuilderModifier(Function<A, CommandRequirement> requirementProvider) {
        this.requirementProvider = requirementProvider;
    }

    public static Command.Builder<Source> addRequirement(
            Command.Builder<Source> builder, CommandRequirement requirement) {
        List<CommandRequirement> requirements = new ArrayList<>(builder.meta().getOrDefault(CommandRequirement.KEY, Collections.emptyList()));
        addRequirement(requirement, requirements);
        return builder.meta(CommandRequirement.KEY, requirements);
    }

    private static void addRequirement(CommandRequirement requirement, List<CommandRequirement> destination) {
        for (CommandRequirement parent : requirement.parents()) {
            addRequirement(parent, destination);
        }
        destination.add(requirement);
    }

    @Override
    public Command.Builder<Source> modifyBuilder(@NonNull A annotation, Command.Builder<Source> builder) {
        return builder.apply(requirementProvider.apply(annotation));
    }
}
