package me.m56738.easyarmorstands.command.global;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.util.MultipleEntitySelector;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.entity.Entity;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;

@CommandContainer
public class RegisterCommand {
    @Command("eas register <entity>")
    @Permission(Permissions.REGISTER)
    @CommandDescription("easyarmorstands.command.description.register")
    public void register(EasyArmorStandsCommon eas, EasCommandSender sender, @Argument("entity") MultipleEntitySelector selector) {
        int count = 0;
        for (Entity entity : selector.values()) {
            EntityElementProvider provider = eas.entityElementProviderRegistry().registerEntity(entity);
            if (provider != null) {
                count++;
            }
        }
        if (count > 1) {
            sender.sendMessage(Message.success("easyarmorstands.success.entity-registered.multiple", Component.text(count)));
        } else if (count == 1) {
            sender.sendMessage(Message.success("easyarmorstands.success.entity-registered"));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.entity-not-found"));
        }
    }

    @Command("eas unregister <entity>")
    @Permission(Permissions.REGISTER)
    @CommandDescription("easyarmorstands.command.description.unregister")
    public void unregister(EasyArmorStandsCommon eas, EasCommandSender sender, @Argument("entity") MultipleEntitySelector selector) {
        int count = 0;
        for (Entity entity : selector.values()) {
            if (eas.getEntityElementProvider(entity) != null) {
                eas.setEntityElementProvider(entity, null);
                count++;
            }
        }
        if (count > 1) {
            sender.sendMessage(Message.success("easyarmorstands.success.entity-unregistered.multiple", Component.text(count)));
        } else if (count == 1) {
            sender.sendMessage(Message.success("easyarmorstands.success.entity-unregistered"));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.entity-not-found"));
        }
    }
}
