package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessingContext;
import me.m56738.easyarmorstands.lib.cloud.execution.preprocessor.CommandPreprocessor;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.session.SessionImpl;
import org.checkerframework.checker.nullness.qual.NonNull;

import static me.m56738.easyarmorstands.command.processor.SessionProcessor.sessionKey;
import static me.m56738.easyarmorstands.lib.cloud.key.CloudKey.cloudKey;

public class ElementProcessor implements CommandPreprocessor<Source> {
    private static final CloudKey<Element> KEY = cloudKey("element", Element.class);

    public static CloudKey<Element> elementKey() {
        return KEY;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<Source> context) {
        CommandContext<Source> commandContext = context.commandContext();
        if (commandContext.contains(sessionKey())) {
            SessionImpl session = commandContext.get(sessionKey());
            Element element = session.getElement();
            if (element != null) {
                commandContext.set(KEY, element);
            }
        }
    }
}
