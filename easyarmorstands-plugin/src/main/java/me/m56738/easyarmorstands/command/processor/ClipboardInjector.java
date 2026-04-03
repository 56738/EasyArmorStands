package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import static me.m56738.easyarmorstands.command.processor.ClipboardProcessor.clipboardKey;

public class ClipboardInjector implements ParameterInjector<EasCommandSender, Clipboard> {
    @Override
    public @Nullable Clipboard create(@NonNull CommandContext<EasCommandSender> context, @NonNull AnnotationAccessor annotationAccessor) {
        return context.getOrDefault(clipboardKey(), null);
    }
}
