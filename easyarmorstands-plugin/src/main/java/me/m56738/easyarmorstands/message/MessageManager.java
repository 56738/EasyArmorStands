package me.m56738.easyarmorstands.message;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.config.EasConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.HashMap;
import java.util.Map;

public class MessageManager {
    private final MiniMessage miniMessage = EasyArmorStandsPlugin.getInstance().getMiniMessage();
    private final Map<MessageStyle, String> styleTemplates = new HashMap<>();

    public MessageManager() {
        Message.messageManager = this;
    }

    public void load(EasConfig config) {
        styleTemplates.putAll(config.message.format);
    }

    public Component format(MessageStyle style, Component message) {
        return format(style, message, TagResolver.empty());
    }

    public Component format(MessageStyle style, Component message, TagResolver resolver) {
        return miniMessage.deserialize(
                styleTemplates.get(style),
                TagResolver.builder()
                        .tag("message", Tag.selfClosingInserting(message))
                        .resolver(resolver)
                        .build());
    }
}
