package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.injection.ParameterInjector;
import me.m56738.easyarmorstands.lib.cloud.util.annotation.AnnotationAccessor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import static me.m56738.easyarmorstands.command.processor.ClipboardProcessor.clipboardKey;

public class ClipboardInjector implements ParameterInjector<EasCommandSender, Clipboard> {
    @Override
    public @Nullable Clipboard create(@NonNull CommandContext<EasCommandSender> context, @NonNull AnnotationAccessor annotationAccessor) {
        return context.getOrDefault(clipboardKey(), null);
    }
}
