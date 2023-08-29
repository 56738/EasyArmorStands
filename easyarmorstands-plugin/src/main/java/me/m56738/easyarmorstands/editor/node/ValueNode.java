package me.m56738.easyarmorstands.editor.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import me.m56738.easyarmorstands.api.editor.node.Node;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public interface ValueNode<T> extends Node {
    Component getName();

    Component formatValue(T value);

    ArgumentParser<CommandSender, T> getParser();

    void setValue(T value);
}
