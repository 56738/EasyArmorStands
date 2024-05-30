package me.m56738.easyarmorstands.command.requirement;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.processors.requirements.Requirement;
import org.incendo.cloud.processors.requirements.Requirements;

import static org.incendo.cloud.key.CloudKey.cloudKey;

public interface CommandRequirement extends Requirement<EasCommandSender, CommandRequirement> {
    CloudKey<Requirements<EasCommandSender, CommandRequirement>> KEY = cloudKey("requirements", new TypeToken<Requirements<EasCommandSender, CommandRequirement>>() {
    });

    void handle(CommandContext<EasCommandSender> context);
}
