package me.m56738.easyarmorstands.common.message;

import net.kyori.adventure.text.Component;

public interface MessageFormatter {
    Component format(MessageStyle style, Component component);
}
