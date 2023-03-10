package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class AudienceInjector implements ParameterInjector<CommandSender, Audience> {
    private final BukkitAudiences adventure;

    public AudienceInjector(BukkitAudiences adventure) {
        this.adventure = adventure;
    }

    @Override
    public @Nullable Audience create(
            @NonNull CommandContext<CommandSender> context,
            @NonNull AnnotationAccessor annotationAccessor) {
        return adventure.sender(context.getSender());
    }
}
