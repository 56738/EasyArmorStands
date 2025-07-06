package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.injection.ParameterInjector;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.lib.cloud.util.annotation.AnnotationAccessor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import static me.m56738.easyarmorstands.command.processor.ElementSelectionProcessor.elementSelectionKey;

public class ElementSelectionInjector implements ParameterInjector<Source, ElementSelection> {
    @Override
    public @Nullable ElementSelection create(@NonNull CommandContext<Source> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (context.contains(elementSelectionKey())) {
            return context.get(elementSelectionKey());
        } else {
            return null;
        }
    }
}
