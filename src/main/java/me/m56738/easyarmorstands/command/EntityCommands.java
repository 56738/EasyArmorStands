package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.annotation.RequireEntity;
import me.m56738.easyarmorstands.property.EntityProperty;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;

import java.util.Objects;

@CommandMethod("eas")
public class EntityCommands {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @CommandMethod("info [property]")
    @CommandPermission("easyarmorstands.info")
    @RequireEntity
    public void info(EasCommandSender sender, Entity entity, @Argument("property") EntityProperty selectedProperty) {
        if (selectedProperty != null) {
            showProperty(sender, entity, selectedProperty);
            return;
        }
        sender.sendMessage(Component.text(entity.getUniqueId().toString(), NamedTextColor.GOLD)
                .clickEvent(ClickEvent.copyToClipboard(entity.getUniqueId().toString())));
        for (EntityProperty property : EasyArmorStands.getInstance().getEntityPropertyRegistry().getProperties(entity.getClass()).values()) {
            showProperty(sender, entity, property);
        }
    }

    private <E extends Entity, T> void showProperty(Audience audience, E entity, EntityProperty<E, T> property) {
        T value = property.getValue(entity);
        audience.sendMessage(Component.text()
                .color(NamedTextColor.GOLD)
                .append(property.getDisplayName())
                .append(Component.text(": "))
                .append(Objects.requireNonNull(property.getValueName(value),
                                () -> "value of " + property.getClass())
                        .colorIfAbsent(NamedTextColor.GRAY)));

    }
}
