package me.m56738.easyarmorstands.common.command;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.command.processor.ElementProcessor;
import me.m56738.easyarmorstands.common.command.requirement.ElementRequirement;
import me.m56738.easyarmorstands.common.command.value.DisplayScaleAxisCommand;
import me.m56738.easyarmorstands.common.command.value.PitchCommand;
import me.m56738.easyarmorstands.common.command.value.PositionCommand;
import me.m56738.easyarmorstands.common.command.value.ValueCommand;
import me.m56738.easyarmorstands.common.command.value.YawCommand;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.permission.Permission;

public final class PropertyCommands {
    private PropertyCommands() {
    }

    public static void register(CommandManager<CommandSource> commandManager) {
        register(commandManager, new PositionCommand());
        register(commandManager, new YawCommand());
        register(commandManager, new PitchCommand());
        for (Axis axis : Axis.values()) {
            register(commandManager, new DisplayScaleAxisCommand(axis));
        }
    }

    public static <T> void register(CommandManager<CommandSource> commandManager, ValueCommand<T> valueCommand) {
        Permission permission = valueCommand.getPermission();
        Command.Builder<CommandSource> builder = commandManager.commandBuilder("eas").apply(valueCommand);

        commandManager.command(builder
                .permission(permission)
                .commandDescription(valueCommand.getShowDescription())
                .apply(new ElementRequirement())
                .handler(context -> {
                    Element element = context.get(ElementProcessor.elementKey());
                    PropertyContainer properties = element.getProperties();
                    if (!valueCommand.isSupported(properties)) {
                        valueCommand.sendNotSupported(context.sender().source());
                        return;
                    }
                    T value = valueCommand.getValue(properties);
                    context.sender().source().sendMessage(Component.text()
                            .append(Message.title(valueCommand.getDisplayName()))
                            .append(Component.space())
                            .append(valueCommand.formatValue(value))
                            .append(Component.space())
                            .append(Message.chatButton("easyarmorstands.button.edit")
                                    .hoverEvent(Message.hover("easyarmorstands.click-to-edit"))
                                    .clickEvent(ClickEvent.suggestCommand(valueCommand.formatCommand(value)))));
                }));

        commandManager.command(builder
                .permission(permission)
                .commandDescription(valueCommand.getSetterDescription())
                .apply(new ElementRequirement())
                .required("value", valueCommand.getParser())
                .senderType(PlayerCommandSource.class)
                .handler(context -> {
                    Element element = context.get(ElementProcessor.elementKey());
                    Player sender = context.sender().source();
                    EasyArmorStands eas = context.inject(EasyArmorStands.class).orElseThrow();
                    try (ManagedChangeContext changeContext = eas.getChangeContextFactory().create(sender)) {
                        PropertyContainer properties = changeContext.getProperties(element);
                        if (!valueCommand.isSupported(properties)) {
                            valueCommand.sendNotSupported(sender);
                            return;
                        }
                        T value = context.get("value");
                        if (valueCommand.setValue(properties, value)) {
                            valueCommand.sendSuccess(sender, value);
                        } else {
                            valueCommand.sendFailure(sender, value);
                        }
                    }
                }));
    }
}
