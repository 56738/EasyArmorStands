package me.m56738.easyarmorstands.command.requirement;

import io.leangen.geantyref.TypeToken;
import org.incendo.cloud.Command;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.paper.util.sender.Source;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.List;

import static org.incendo.cloud.key.CloudKey.cloudKey;

@NullMarked
public interface CommandRequirement extends Command.Builder.Applicable<Source> {
    CloudKey<List<CommandRequirement>> KEY = cloudKey("requirements", new TypeToken<>() {
    });

    boolean evaluateRequirement(CommandContext<Source> commandContext);

    void handle(CommandContext<Source> context);

    default List<CommandRequirement> parents() {
        return Collections.emptyList();
    }

    @Override
    default Command.Builder<Source> applyToCommandBuilder(Command.Builder<Source> builder) {
        return CommandRequirementBuilderModifier.addRequirement(builder, this);
    }
}
