package me.m56738.easyarmorstands.core.command;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.processing.CommandContainer;
import me.m56738.easyarmorstands.core.platform.EasPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@CommandContainer
public class GlobalCommands {
    @CommandMethod("eas give")
    @CommandPermission("easyarmorstands.give")
    public void give(EasPlayer sender) {
        sender.giveTool();
        sender.sendMessage(Component.text("Tool added to your inventory", NamedTextColor.GREEN));
    }
}
