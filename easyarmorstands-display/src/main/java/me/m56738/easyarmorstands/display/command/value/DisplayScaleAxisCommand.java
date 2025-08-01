package me.m56738.easyarmorstands.display.command.value;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.value.ScaleAxisCommand;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.lib.cloud.Command;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class DisplayScaleAxisCommand extends ScaleAxisCommand {
    private final String axisName;

    public DisplayScaleAxisCommand(Axis axis) {
        super(DisplayPropertyTypes.SCALE, axis);
        this.axisName = axis.getName().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull String formatCommand(@NotNull Float value) {
        return "/eas scale " + axisName + " " + value;
    }

    @Override
    public Command.@NonNull Builder<EasCommandSender> applyToCommandBuilder(Command.@NonNull Builder<EasCommandSender> builder) {
        return builder.literal("scale").literal(axisName);
    }
}
