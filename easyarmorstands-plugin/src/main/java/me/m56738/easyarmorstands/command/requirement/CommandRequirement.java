package me.m56738.easyarmorstands.command.requirement;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.Command;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.key.CloudKey;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.List;

import static org.incendo.cloud.key.CloudKey.cloudKey;

@NullMarked
public interface CommandRequirement extends Command.Builder.Applicable<CommandSource> {
    CloudKey<List<CommandRequirement>> KEY = cloudKey("requirements", new TypeToken<>() {
    });

    boolean evaluateRequirement(CommandContext<CommandSource> context);

    void handle(CommandContext<CommandSource> context);

    default List<CommandRequirement> parents() {
        return Collections.emptyList();
    }

    @Override
    default Command.Builder<CommandSource> applyToCommandBuilder(Command.Builder<CommandSource> builder) {
        return CommandRequirementBuilderModifier.addRequirement(builder, this);
    }
}
