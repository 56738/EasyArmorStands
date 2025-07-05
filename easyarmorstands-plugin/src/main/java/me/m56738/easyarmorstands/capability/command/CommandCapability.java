package me.m56738.easyarmorstands.capability.command;

import me.m56738.easyarmorstands.capability.Capability;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.CommandManager;

@Capability(name = "Commands")
public interface CommandCapability {
    CommandManager<EasCommandSender> createCommandManager();
}
