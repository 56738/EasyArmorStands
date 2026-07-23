package me.m56738.easyarmorstands.editor.layer;

import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.platform.command.CommandSender;
import net.kyori.adventure.text.Component;
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
