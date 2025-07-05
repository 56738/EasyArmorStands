package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessingContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessor;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import org.checkerframework.checker.nullness.qual.NonNull;

import static me.m56738.easyarmorstands.lib.cloud.key.CloudKey.cloudKey;

public class ClipboardProcessor implements CommandPreprocessor<EasCommandSender> {
    private static final CloudKey<Clipboard> KEY = cloudKey("clipboard", Clipboard.class);

    public static CloudKey<Clipboard> clipboardKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<EasCommandSender> context) {
        CommandContext<EasCommandSender> commandContext = context.commandContext();
        EasCommandSender sender = commandContext.sender();
        if (sender instanceof EasPlayer) {
            Clipboard clipboard = ((EasPlayer) sender).clipboard();
            commandContext.set(KEY, clipboard);
        }
    }
}
