package me.m56738.easyarmorstands.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.PropertyKey;

public class Message {
    private static final String BUNDLE = "me.m56738.easyarmorstands.messages";
    static MessageManager messageManager;

    private Message() {
    }

    public static Component success(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.SUCCESS, Component.translatable(key));
    }

    public static Component success(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.SUCCESS, Component.translatable(key, args));
    }

    public static Component warning(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.WARNING, Component.translatable(key));
    }

    public static Component warning(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.WARNING, Component.translatable(key, args));
    }

    public static Component error(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.ERROR, Component.translatable(key));
    }

    public static Component error(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.ERROR, Component.translatable(key, args));
    }

    public static Component hint(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.HINT, Component.translatable(key));
    }

    public static Component hint(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.HINT, Component.translatable(key, args));
    }

    public static Component button(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.BUTTON, Component.translatable(key));
    }

    public static Component button(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.BUTTON, Component.translatable(key, args));
    }

    public static HoverEventSource<Component> hover(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.HOVER, Component.translatable(key));
    }

    public static HoverEventSource<Component> hover(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.HOVER, Component.translatable(key, args));
    }

    public static Component command(String command) {
        return Component.text()
                .content(command)
                .decorate(TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.runCommand(command))
                .hoverEvent(hover("easyarmorstands.click-to-run", Component.text(command)))
                .build();
    }
}
