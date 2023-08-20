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

    public static Component title(Component component) {
        return messageManager.format(MessageStyle.TITLE, component);
    }

    public static Component title(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.TITLE, component(key));
    }

    public static Component title(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.TITLE, component(key, args));
    }

    public static Component success(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.SUCCESS, component(key));
    }

    public static Component success(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.SUCCESS, component(key, args));
    }

    public static Component warning(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.WARNING, component(key));
    }

    public static Component warning(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.WARNING, component(key, args));
    }

    public static Component error(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.ERROR, component(key));
    }

    public static Component error(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.ERROR, component(key, args));
    }

    public static Component hint(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.HINT, component(key));
    }

    public static Component hint(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.HINT, component(key, args));
    }

    public static Component chatButton(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.CHAT_BUTTON, component(key));
    }

    public static Component chatButton(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.CHAT_BUTTON, component(key, args));
    }

    public static HoverEventSource<Component> hover(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.HOVER, component(key));
    }

    public static HoverEventSource<Component> hover(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.HOVER, component(key, args));
    }

    public static Component buttonName(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.BUTTON_NAME, component(key));
    }

    public static Component buttonName(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.BUTTON_NAME, component(key, args));
    }

    public static Component buttonDescription(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return messageManager.format(MessageStyle.BUTTON_DESCRIPTION, component(key));
    }

    public static Component buttonDescription(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return messageManager.format(MessageStyle.BUTTON_DESCRIPTION, component(key, args));
    }

    public static Component component(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return Component.translatable(key);
    }

    public static Component component(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return Component.translatable(key, args);
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
