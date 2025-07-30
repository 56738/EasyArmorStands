package me.m56738.easyarmorstands.common.command.processor;

import me.m56738.easyarmorstands.common.clipboard.Clipboard;
import me.m56738.easyarmorstands.common.clipboard.ClipboardManager;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;
import org.incendo.cloud.key.CloudKey;

import static org.incendo.cloud.key.CloudKey.cloudKey;

public class ClipboardProcessor implements CommandPreprocessor<CommandSource> {
    private static final CloudKey<Clipboard> KEY = cloudKey("clipboard", Clipboard.class);

    public static CloudKey<Clipboard> clipboardKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<CommandSource> context) {
        CommandContext<CommandSource> commandContext = context.commandContext();
        ClipboardManager clipboardManager = commandContext.inject(ClipboardManager.class).orElseThrow();
        CommandSource sender = commandContext.sender();
        if (sender instanceof PlayerCommandSource playerSource) {
            Clipboard clipboard = clipboardManager.getClipboard(playerSource.source());
            commandContext.set(KEY, clipboard);
        }
    }
}
