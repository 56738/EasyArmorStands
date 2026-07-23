package me.m56738.easyarmorstands.platform.dialog;

import net.kyori.adventure.text.Component;

public interface DialogInputProvider {
    DialogInput createBoolean(String key, Component label, boolean initial);

    DialogInput createText(String key, Component label, String initial);
}
