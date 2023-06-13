package me.m56738.easyarmorstands.property.v1_19_4.display;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.FloatArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.LegacyEntityPropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DisplaySizeProperty implements LegacyEntityPropertyType<Display, Float> {
    @Override
    public TypeToken<Float> getValueType() {
        return TypeToken.get(Float.class);
    }

    @Override
    public @NotNull Class<Display> getEntityType() {
        return Display.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Float> getArgumentParser() {
        return new FloatArgument.FloatParser<>(0, Float.POSITIVE_INFINITY);
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.size";
    }

    @Override
    public @NotNull Component getValueName(Float value) {
        return Component.text(value);
    }
}
