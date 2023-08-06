package me.m56738.easyarmorstands.command.processor;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.InjectionService;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.types.tuples.Triplet;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.key.Key;
import me.m56738.easyarmorstands.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PropertyInjectionService implements InjectionService<EasCommandSender> {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public @Nullable Object handle(@NonNull Triplet<CommandContext<EasCommandSender>, Class<?>, AnnotationAccessor> triplet) throws Exception {
        CommandContext<EasCommandSender> context = triplet.getFirst();
        Session session = SessionPreprocessor.getSessionOrNull(context);
        if (session == null) {
            return null;
        }
        Class type = triplet.getSecond();
        return session.findProperty(Key.of(type));
    }
}
