package me.m56738.easyarmorstands.command.global;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;

@CommandContainer
public class OverviewCommand {
    @Command("eas")
    @Permission(Permissions.HELP)
    @CommandDescription("easyarmorstands.command.description")
    public void showOverview(EasCommandSender sender) {
        if (sender.get().hasPermission(Permissions.VERSION)) {
            String version = EasyArmorStandsPlugin.getInstance().getPluginMeta().getVersion();
            sender.sendMessage(Component.text("EasyArmorStands v" + version, NamedTextColor.GOLD));
        } else {
            sender.sendMessage(Component.text("EasyArmorStands", NamedTextColor.GOLD));
        }
        if (sender.get().hasPermission(Permissions.GIVE)) {
            sender.sendMessage(Message.hint("easyarmorstands.hint.give-tool", Message.command("/eas give")));
        }
        sender.sendMessage(Message.hint("easyarmorstands.hint.show-help", Message.command("/eas help")));
    }
}
