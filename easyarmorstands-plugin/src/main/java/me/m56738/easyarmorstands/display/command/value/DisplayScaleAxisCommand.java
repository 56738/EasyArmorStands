package me.m56738.easyarmorstands.display.command.value;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.command.value.ScaleAxisCommand;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
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
    public Command.@NonNull Builder<CommandSource> applyToCommandBuilder(Command.@NonNull Builder<CommandSource> builder) {
        return builder.literal("scale").literal(axisName);
    }
}
