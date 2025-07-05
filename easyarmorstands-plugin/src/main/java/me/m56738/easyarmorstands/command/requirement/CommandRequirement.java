package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.Command;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.List;

import static me.m56738.easyarmorstands.lib.cloud.key.CloudKey.cloudKey;

public interface CommandRequirement extends Command.Builder.Applicable<EasCommandSender> {
    CloudKey<List<CommandRequirement>> KEY = cloudKey("requirements", new TypeToken<List<CommandRequirement>>() {
    });

    boolean evaluateRequirement(@NonNull CommandContext<EasCommandSender> commandContext);

    void handle(CommandContext<EasCommandSender> context);

    @NonNull
    default List<@NonNull CommandRequirement> parents() {
        return Collections.emptyList();
    }

    @Override
    default Command.@NonNull Builder<EasCommandSender> applyToCommandBuilder(Command.@NonNull Builder<EasCommandSender> builder) {
        return CommandRequirementBuilderModifier.addRequirement(builder, this);
    }
}
