package me.m56738.easyarmorstands.editor.layer;

import me.m56738.easyarmorstands.api.editor.layer.Layer;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.parser.ArgumentParser;

public interface ValueLayer<T> extends Layer {
    Component getName();

    Component formatValue(T value);

    ArgumentParser<CommandSender, T> getParser();

    default boolean canSetValue() {
        return true;
    }

    void setValue(T value);
}
