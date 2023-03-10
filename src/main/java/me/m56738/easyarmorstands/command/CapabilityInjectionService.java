package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.InjectionService;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.services.types.ConsumerService;
import cloud.commandframework.types.tuples.Triplet;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CapabilityInjectionService implements InjectionService<CommandSender> {
    private final CapabilityLoader loader;
    private final BukkitAudiences adventure;

    public CapabilityInjectionService(CapabilityLoader loader, BukkitAudiences adventure) {
        this.loader = loader;
        this.adventure = adventure;
    }

    @Override
    public @Nullable Object handle(@NonNull Triplet<CommandContext<CommandSender>, Class<?>, AnnotationAccessor> triplet) {
        Class<?> type = triplet.getSecond();
        if (!loader.isCapability(type)) {
            return null;
        }
        Object instance = loader.get(type);
        if (instance == null) {
            CommandSender sender = triplet.getFirst().getSender();
            adventure.sender(sender).sendMessage(
                    Component.text("This feature is not supported on this server", NamedTextColor.RED));
            ConsumerService.interrupt();
        }
        return instance;
    }
}
