package me.m56738.easyarmorstands.platform.dialog;

import net.kyori.adventure.text.Component;

public interface DialogBodyProvider {
    DialogBody createText(Component text);
}
