package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessingContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessor;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.PlayerSource;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import org.checkerframework.checker.nullness.qual.NonNull;

import static me.m56738.easyarmorstands.lib.cloud.key.CloudKey.cloudKey;

public class ClipboardProcessor implements CommandPreprocessor<Source> {
    private static final CloudKey<Clipboard> KEY = cloudKey("clipboard", Clipboard.class);

    public static CloudKey<Clipboard> clipboardKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<Source> context) {
        CommandContext<Source> commandContext = context.commandContext();
        Source sender = commandContext.sender();
        if (sender instanceof PlayerSource playerSource) {
            Clipboard clipboard = EasyArmorStandsPlugin.getInstance().getClipboard(playerSource.source());
            commandContext.set(KEY, clipboard);
        }
    }
}
