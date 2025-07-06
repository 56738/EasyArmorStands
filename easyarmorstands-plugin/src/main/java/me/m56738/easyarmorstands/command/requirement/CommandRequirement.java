package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.lib.cloud.Command;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.List;

import static me.m56738.easyarmorstands.lib.cloud.key.CloudKey.cloudKey;

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
