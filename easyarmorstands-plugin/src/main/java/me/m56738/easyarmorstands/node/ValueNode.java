package me.m56738.easyarmorstands.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public interface ValueNode<T> extends me.m56738.easyarmorstands.api.editor.node.Node {
    Component getName();

    Component getValueComponent(T value);

    ArgumentParser<CommandSender, T> getParser();

    void setValue(T value);
}
