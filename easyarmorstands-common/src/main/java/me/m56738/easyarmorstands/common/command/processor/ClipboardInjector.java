package me.m56738.easyarmorstands.common.command.processor;

import me.m56738.easyarmorstands.common.clipboard.Clipboard;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import static me.m56738.easyarmorstands.common.command.processor.ClipboardProcessor.clipboardKey;

public class ClipboardInjector implements ParameterInjector<CommandSource, Clipboard> {
    @Override
    public @Nullable Clipboard create(@NonNull CommandContext<CommandSource> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (context.contains(clipboardKey())) {
            return context.get(clipboardKey());
        } else {
            return null;
        }
    }
}
