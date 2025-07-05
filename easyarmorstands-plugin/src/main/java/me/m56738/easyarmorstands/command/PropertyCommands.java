package me.m56738.easyarmorstands.command;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.command.processor.ElementProcessor;
import me.m56738.easyarmorstands.command.requirement.ElementRequirement;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.command.value.PitchCommand;
import me.m56738.easyarmorstands.command.value.PositionCommand;
import me.m56738.easyarmorstands.command.value.ValueCommand;
import me.m56738.easyarmorstands.command.value.YawCommand;
import me.m56738.easyarmorstands.display.command.value.DisplayScaleAxisCommand;
import me.m56738.easyarmorstands.lib.cloud.Command;
import me.m56738.easyarmorstands.lib.cloud.CommandManager;
import me.m56738.easyarmorstands.lib.cloud.permission.Permission;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

public final class PropertyCommands {
    private PropertyCommands() {
    }

    public static void register(CommandManager<EasCommandSender> commandManager) {
        register(commandManager, new PositionCommand());
        register(commandManager, new YawCommand());
        register(commandManager, new PitchCommand());
        for (Axis axis : Axis.values()) {
            register(commandManager, new DisplayScaleAxisCommand(axis));
        }
    }

    public static <T> void register(CommandManager<EasCommandSender> commandManager, ValueCommand<T> valueCommand) {
        Permission permission = valueCommand.getPermission();
        Command.Builder<EasCommandSender> builder = commandManager.commandBuilder("eas").apply(valueCommand);

        commandManager.command(builder
                .permission(permission)
                .commandDescription(valueCommand.getShowDescription())
                .apply(new ElementRequirement())
                .handler(context -> {
                    Element element = context.get(ElementProcessor.elementKey());
                    PropertyContainer properties = element.getProperties();
                    if (!valueCommand.isSupported(properties)) {
                        valueCommand.sendNotSupported(context.sender());
                        return;
                    }
                    T value = valueCommand.getValue(properties);
                    context.sender().sendMessage(Component.text()
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
                .senderType(EasPlayer.class)
                .handler(context -> {
                    Element element = context.get(ElementProcessor.elementKey());
                    PropertyContainer properties = new TrackedPropertyContainer(element, context.sender());
                    if (!valueCommand.isSupported(properties)) {
                        valueCommand.sendNotSupported(context.sender());
                        return;
                    }
                    T value = context.get("value");
                    if (valueCommand.setValue(properties, value)) {
                        valueCommand.sendSuccess(context.sender(), value);
                    } else {
                        valueCommand.sendFailure(context.sender(), value);
                    }
                    properties.commit();
                }));
    }
}
