package me.m56738.easyarmorstands.common.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.PropertyKey;

public class Message {
    private static final String BUNDLE = "me.m56738.easyarmorstands.messages";
    public static MessageFormatter messageFormatter = (style, component) -> switch (style) {
        case TITLE -> component.colorIfAbsent(NamedTextColor.GOLD);
        case SUCCESS -> component.colorIfAbsent(NamedTextColor.GREEN);
        case WARNING -> component.colorIfAbsent(NamedTextColor.YELLOW);
        case ERROR -> component.colorIfAbsent(NamedTextColor.RED);
        case HINT, BUTTON_DESCRIPTION, HOVER -> component.colorIfAbsent(NamedTextColor.GRAY);
        case CHAT_BUTTON -> Component.text()
                .append(Component.text("["))
                .append(component)
                .append(Component.text("]"))
                .color(NamedTextColor.GRAY)
                .build();
        case BUTTON_NAME -> component.colorIfAbsent(NamedTextColor.BLUE);
    };

    private Message() {
    }

    public static Component format(MessageStyle style, Component component) {
        return messageFormatter.format(style, component);
    }

    public static Component format(MessageStyle style,
                                   @PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(style, component(key));
    }

    public static Component format(MessageStyle style,
                                   @PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(style, component(key, args));
    }

    public static Component title(Component component) {
        return format(MessageStyle.TITLE, component);
    }

    public static Component title(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.TITLE, key);
    }

    public static Component title(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.TITLE, key, args);
    }

    public static Component success(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.SUCCESS, key);
    }

    public static Component success(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.SUCCESS, key, args);
    }

    public static Component warning(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.WARNING, key);
    }

    public static Component warning(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.WARNING, key, args);
    }

    public static Component error(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.ERROR, key);
    }

    public static Component error(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.ERROR, key, args);
    }

    public static Component hint(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.HINT, key);
    }

    public static Component hint(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.HINT, key, args);
    }

    public static Component chatButton(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.CHAT_BUTTON, key);
    }

    public static Component chatButton(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.CHAT_BUTTON, key, args);
    }

    public static HoverEventSource<Component> hover(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.HOVER, key);
    }

    public static HoverEventSource<Component> hover(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.HOVER, key, args);
    }

    public static Component buttonName(Component component) {
        return format(MessageStyle.BUTTON_NAME, component);
    }

    public static Component buttonName(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.BUTTON_NAME, key);
    }

    public static Component buttonName(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.BUTTON_NAME, key, args);
    }

    public static Component buttonDescription(Component component) {
        return format(MessageStyle.BUTTON_DESCRIPTION, component);
    }

    public static Component buttonDescription(@PropertyKey(resourceBundle = BUNDLE) String key) {
        return format(MessageStyle.BUTTON_DESCRIPTION, key);
    }

    public static Component buttonDescription(@PropertyKey(resourceBundle = BUNDLE) String key, ComponentLike... args) {
        return format(MessageStyle.BUTTON_DESCRIPTION, key, args);
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
