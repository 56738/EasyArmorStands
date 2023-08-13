package me.m56738.easyarmorstands.message;

import cloud.commandframework.CommandManager;
import cloud.commandframework.CommandTree;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.StaticArgument;
import cloud.commandframework.meta.CommandMeta;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CommandTagResolver implements TagResolver {
    private final CommandManager<EasCommandSender> commandManager;

    public CommandTagResolver(CommandManager<EasCommandSender> commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
        if (!has(name)) {
            return null;
        }

        String cmd = arguments.popOr("Command expected.").value();
        if (!cmd.startsWith("/")) {
            return null;
        }

        Collection<CommandTree.Node<CommandArgument<EasCommandSender, ?>>> nodes = commandManager.commandTree().getRootNodes();
        CommandTree.Node<CommandArgument<EasCommandSender, ?>> lastNode = null;
        for (String word : cmd.substring(1).split(" ")) {
            CommandTree.Node<CommandArgument<EasCommandSender, ?>> node = findNode(nodes, word);
            if (node == null) {
                break;
            }
            lastNode = node;
            nodes = node.getChildren();
        }


        HoverEvent<?> hoverEvent = null;
        if (lastNode != null && lastNode.isLeaf() && lastNode.getValue() != null && lastNode.getValue().getOwningCommand() != null) {
            CommandMeta meta = lastNode.getValue().getOwningCommand().getCommandMeta();
            String description = meta.get(CommandMeta.LONG_DESCRIPTION)
                    .orElseGet(() -> meta.getOrDefault(CommandMeta.DESCRIPTION, "No description"));
            hoverEvent = Component.text(description).asHoverEvent();
        }

        return Tag.selfClosingInserting(Component.text()
                .content(cmd)
                .decorate(TextDecoration.UNDERLINED)
                .hoverEvent(hoverEvent)
                .clickEvent(ClickEvent.runCommand(cmd)));
    }

    private CommandTree.Node<CommandArgument<EasCommandSender, ?>> findNode(Collection<CommandTree.Node<CommandArgument<EasCommandSender, ?>>> nodes, String word) {
        for (CommandTree.Node<CommandArgument<EasCommandSender, ?>> node : nodes) {
            CommandArgument<?, ?> argument = node.getValue();
            if (argument instanceof StaticArgument) {
                StaticArgument<?> staticArgument = (StaticArgument<?>) argument;
                if (staticArgument.getName().equals(word)) {
                    return node;
                }
                for (String alias : staticArgument.getAliases()) {
                    if (alias.equals(word)) {
                        return node;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("command") || name.equals("cmd");
    }
}
