package me.m56738.easyarmorstands.property;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.StringArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public abstract class ComponentEntityProperty<E extends Entity> implements LegacyEntityPropertyType<E, Component> {
    @Override
    public ArgumentParser<EasCommandSender, Component> getArgumentParser() {
        return new StringArgument.StringParser<EasCommandSender>(
                StringArgument.StringMode.GREEDY,
                (v1, v2) -> Collections.emptyList()
        ).map((ctx, input) ->
                ArgumentParseResult.success(MiniMessage.miniMessage().deserialize(input)));
    }

    @Override
    public TypeToken<Component> getValueType() {
        return TypeToken.get(Component.class);
    }

    @Override
    public @NotNull Component getValueName(Component value) {
        return value;
    }

    @Override
    public @NotNull String getValueClipboardContent(Component value) {
        return MiniMessage.miniMessage().serialize(value);
    }
}
