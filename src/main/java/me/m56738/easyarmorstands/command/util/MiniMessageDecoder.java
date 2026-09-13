package me.m56738.easyarmorstands.command.util;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import net.kyori.adventure.text.Component;

import java.util.function.Function;

public class MiniMessageDecoder implements Function<String, Component> {
    @Override
    public Component apply(String s) {
        return EasyArmorStandsPlugin.getInstance().getMiniMessage().deserialize(s);
    }
}
