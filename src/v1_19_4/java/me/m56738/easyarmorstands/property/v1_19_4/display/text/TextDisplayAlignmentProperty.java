package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.EnumArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.ToggleEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;

public class TextDisplayAlignmentProperty extends ToggleEntityProperty<TextDisplay, TextDisplay.TextAlignment> {
    public static boolean isSupported() {
        try {
            TextDisplay.class.getMethod("getAlignment");
            TextDisplay.class.getMethod("setAlignment", TextDisplay.TextAlignment.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public TextDisplay.TextAlignment getValue(TextDisplay entity) {
        return entity.getAlignment();
    }

    @Override
    public TypeToken<TextDisplay.TextAlignment> getValueType() {
        return TypeToken.get(TextDisplay.TextAlignment.class);
    }

    @Override
    public void setValue(TextDisplay entity, TextDisplay.TextAlignment value) {
        entity.setAlignment(value);
    }

    @Override
    public @NotNull String getName() {
        return "alignment";
    }

    @Override
    public @NotNull Class<TextDisplay> getEntityType() {
        return TextDisplay.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, TextDisplay.TextAlignment> getArgumentParser() {
        return new EnumArgument.EnumParser<>(TextDisplay.TextAlignment.class);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("alignment");
    }

    @Override
    public @NotNull Component getValueName(TextDisplay.TextAlignment value) {
        return Component.text(value.name().toLowerCase(Locale.ROOT));
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.text.alignment";
    }

    @Override
    public TextDisplay.TextAlignment getNextValue(TextDisplay entity) {
        TextDisplay.TextAlignment[] values = TextDisplay.TextAlignment.values();
        return values[(entity.getAlignment().ordinal() + 1) % values.length];
    }

    @Override
    public ItemStack createToggleButton(TextDisplay entity) {
        return Util.createItem(
                ItemType.FEATHER,
                Component.text("Toggle alignment", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes the text", NamedTextColor.GRAY),
                        Component.text("alignment.", NamedTextColor.GRAY)
                )
        );
    }
}
