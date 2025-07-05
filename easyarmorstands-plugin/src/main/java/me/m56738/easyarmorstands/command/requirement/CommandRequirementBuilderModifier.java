package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.Command;
import me.m56738.easyarmorstands.lib.cloud.annotations.BuilderModifier;
import org.checkerframework.checker.nullness.qual.NonNull;

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

    public static Command.@NonNull Builder<EasCommandSender> addRequirement(
            Command.@NonNull Builder<EasCommandSender> builder, @NonNull CommandRequirement requirement) {
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
    public Command.@NonNull Builder<? extends EasCommandSender> modifyBuilder(@NonNull A annotation, Command.Builder<EasCommandSender> builder) {
        return builder.apply(requirementProvider.apply(annotation));
    }
}
