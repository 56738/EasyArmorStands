package me.m56738.easyarmorstands.command.global;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;

@CommandContainer
public class ReloadCommand {
    @Command("eas reload")
    @Permission(Permissions.RELOAD)
    @CommandDescription("easyarmorstands.command.description.reload")
    public void reloadConfig(EasCommandSender sender) {
        EasyArmorStandsPlugin.getInstance().reload();
        sender.sendMessage(Message.success("easyarmorstands.success.reloaded-config"));
    }
}
