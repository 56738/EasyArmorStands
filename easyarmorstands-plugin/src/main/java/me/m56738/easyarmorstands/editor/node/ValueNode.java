package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.lib.cloud.parser.ArgumentParser;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public interface ValueNode<T> extends Node {
    Component getName();

    Component formatValue(T value);

    ArgumentParser<CommandSender, T> getParser();

    default boolean canSetValue() {
        return true;
    }

    void setValue(T value);
}
