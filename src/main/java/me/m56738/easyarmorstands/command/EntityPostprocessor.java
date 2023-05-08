package me.m56738.easyarmorstands.command;

import cloud.commandframework.execution.postprocessor.CommandPostprocessingContext;
import cloud.commandframework.execution.postprocessor.CommandPostprocessor;
import cloud.commandframework.services.types.ConsumerService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;
import java.util.function.Predicate;

public class EntityPostprocessor implements CommandPostprocessor<EasCommandSender> {
    @Override
    public void accept(@NonNull CommandPostprocessingContext<EasCommandSender> ctx) {
        Optional<Predicate<Entity>> optional = ctx.getCommand().getCommandMeta().get(Keys.ENTITY_REQUIRED);
        if (optional.isPresent()) {
            Predicate<Entity> predicate = optional.get();
            Entity entity = EntityPreprocessor.getEntityOrNull(ctx.getCommandContext());
            if (entity == null) {
                ctx.getCommandContext().getSender().sendMessage(Component.text("You are not editing an entity.", NamedTextColor.RED));
                ConsumerService.interrupt();
            }

            if (!predicate.test(entity)) {
                ctx.getCommandContext().getSender().sendMessage(Component.text("You are editing an unsupported entity.", NamedTextColor.RED));
                ConsumerService.interrupt();
            }
        }
    }
}
