package me.m56738.easyarmorstands.command;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.command.parser.ArgumentParserProvider;
import me.m56738.easyarmorstands.command.parser.BlockDataArgumentParser;
import me.m56738.easyarmorstands.command.processor.ElementProcessor;
import me.m56738.easyarmorstands.command.requirement.ElementRequirement;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.command.value.DisplayScaleAxisCommand;
import me.m56738.easyarmorstands.command.value.PitchCommand;
import me.m56738.easyarmorstands.command.value.PositionCommand;
import me.m56738.easyarmorstands.command.value.PropertyCommand;
import me.m56738.easyarmorstands.command.value.ValueCommand;
import me.m56738.easyarmorstands.command.value.YawCommand;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.permission.Permission;

public final class PropertyCommands {
    private PropertyCommands() {
    }

    public static void register(CommandManager<EasCommandSender> commandManager, ArgumentParserProvider parserProvider) {
        register(commandManager, new PositionCommand(parserProvider));
        register(commandManager, new YawCommand());
        register(commandManager, new PitchCommand());
        for (Axis axis : Axis.values()) {
            register(commandManager, new DisplayScaleAxisCommand(axis));
        }

        register(commandManager, new PropertyCommand<>("block", BlockDisplayPropertyTypes.BLOCK, BlockDataArgumentParser.blockDataParser()));
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
                    EasyArmorStandsCommon eas = context.inject(EasyArmorStandsCommon.class).orElseThrow();
                    Element element = context.get(ElementProcessor.elementKey());
                    PropertyContainer properties = new TrackedPropertyContainer(eas, element, context.sender());
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
