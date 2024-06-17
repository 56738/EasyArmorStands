package me.m56738.easyarmorstands.command.requirement;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.key.CloudKey;

import java.util.Collections;
import java.util.List;

import static org.incendo.cloud.key.CloudKey.cloudKey;

public interface CommandRequirement {
    CloudKey<List<CommandRequirement>> KEY = cloudKey("requirements", new TypeToken<List<CommandRequirement>>() {
    });

    boolean evaluateRequirement(@NonNull CommandContext<EasCommandSender> commandContext);

    void handle(CommandContext<EasCommandSender> context);

    @NonNull
    default List<@NonNull CommandRequirement> parents() {
        return Collections.emptyList();
    }
}
