package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;
import org.incendo.cloud.key.CloudKey;

import static me.m56738.easyarmorstands.command.processor.SessionProcessor.sessionKey;
import static org.incendo.cloud.key.CloudKey.cloudKey;

public class ElementProcessor implements CommandPreprocessor<CommandSource> {
    private static final CloudKey<Element> KEY = cloudKey("element", Element.class);

    public static CloudKey<Element> elementKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<CommandSource> context) {
        CommandContext<CommandSource> commandContext = context.commandContext();
        if (commandContext.contains(sessionKey())) {
            SessionImpl session = commandContext.get(sessionKey());
            Element element = session.getElement();
            if (element != null) {
                commandContext.set(KEY, element);
            }
        }
    }
}
