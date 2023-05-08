package me.m56738.easyarmorstands.property.v1_19_4.display;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class TextDisplayTextProperty implements EntityProperty<TextDisplay, Component> {
    private final TextDisplayCapability textDisplayCapability;

    public TextDisplayTextProperty(TextDisplayCapability textDisplayCapability) {
        this.textDisplayCapability = textDisplayCapability;
    }

    @Override
    public Component getValue(TextDisplay entity) {
        return textDisplayCapability.getText(entity);
    }

    @Override
    public boolean hasDefaultValue() {
        return true;
    }

    @Override
    public @Nullable Component getDefaultValue(@NotNull CommandContext<EasCommandSender> ctx) {
        return Component.empty();
    }

    @Override
    public TypeToken<Component> getValueType() {
        return TypeToken.get(Component.class);
    }

    @Override
    public void setValue(TextDisplay entity, Component value) {
        textDisplayCapability.setText(entity, value);
    }

    @Override
    public @NotNull String getName() {
        return "text";
    }

    @Override
    public @NotNull Class<TextDisplay> getEntityType() {
        return TextDisplay.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Component> getArgumentParser() {
        return new StringArgument.StringParser<EasCommandSender>(
                StringArgument.StringMode.GREEDY,
                (v1, v2) -> Collections.emptyList()
        ).map((ctx, input) ->
                ArgumentParseResult.success(MiniMessage.miniMessage().deserialize(input)));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("text");
    }

    @Override
    public @NotNull Component getValueName(Component value) {
        return value;
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.text";
    }
}
