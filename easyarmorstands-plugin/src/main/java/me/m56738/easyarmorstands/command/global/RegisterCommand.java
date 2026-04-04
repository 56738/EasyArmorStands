package me.m56738.easyarmorstands.command.global;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.element.EntityElementKeys;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.bukkit.data.MultipleEntitySelector;

@CommandContainer
public class RegisterCommand {
    @Command("eas register <entity>")
    @Permission(Permissions.REGISTER)
    @CommandDescription("easyarmorstands.command.description.register")
    public void register(EasCommandSender sender, @Argument("entity") MultipleEntitySelector selector) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        int count = 0;
        for (Entity entity : selector.values()) {
            EntityElementProvider provider = plugin.entityElementProviderRegistry().registerEntity(entity);
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
    public void unregister(EasCommandSender sender, @Argument("entity") MultipleEntitySelector selector) {
        int count = 0;
        for (Entity entity : selector.values()) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            if (pdc.has(EntityElementKeys.ELEMENT_TYPE)) {
                EasyArmorStandsPlugin.getInstance().setEntityElementProvider(entity, null);
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
