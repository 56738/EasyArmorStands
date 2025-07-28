package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.parser.ArgumentParser;

public interface ValueNode<T> extends Node {
    Component getName();

    Component formatValue(T value);

    ArgumentParser<CommandSource, T> getParser();

    default boolean canSetValue() {
        return true;
    }

    void setValue(T value);
}
