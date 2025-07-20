package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.clipboard.Clipboard;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import static me.m56738.easyarmorstands.command.processor.ClipboardProcessor.clipboardKey;

public class ClipboardInjector implements ParameterInjector<Source, Clipboard> {
    @Override
    public @Nullable Clipboard create(@NonNull CommandContext<Source> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (context.contains(clipboardKey())) {
            return context.get(clipboardKey());
        } else {
            return null;
        }
    }
}
