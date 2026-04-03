package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import static me.m56738.easyarmorstands.command.processor.ElementSelectionProcessor.elementSelectionKey;

public class ElementSelectionInjector implements ParameterInjector<EasCommandSender, ElementSelection> {
    @Override
    public @Nullable ElementSelection create(@NonNull CommandContext<EasCommandSender> context, @NonNull AnnotationAccessor annotationAccessor) {
        return context.getOrDefault(elementSelectionKey(), null);
    }
}
