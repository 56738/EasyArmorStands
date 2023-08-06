package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.ToggleEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

@SuppressWarnings("deprecation") // presence checked in isSupported
public class TextDisplayBackgroundProperty extends ToggleEntityProperty<TextDisplay, Optional<Color>> {
    public static boolean isSupported() {
        try {
            TextDisplay.class.getMethod("getBackgroundColor");
            TextDisplay.class.getMethod("setBackgroundColor", Color.class);
            TextDisplay.class.getMethod("isDefaultBackground");
            TextDisplay.class.getMethod("setDefaultBackground", boolean.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Optional<Color> getValue(TextDisplay entity) {
        if (entity.isDefaultBackground()) {
            return Optional.empty();
        }
        Color color = entity.getBackgroundColor();
        if (color == null) {
            color = Color.WHITE;
        }
        return Optional.of(color);
    }

    @Override
    public TypeToken<Optional<Color>> getValueType() {
        return new TypeToken<Optional<Color>>() {
        };
    }

    @Override
    public void setValue(TextDisplay entity, Optional<Color> value) {
        if (value.isPresent()) {
            entity.setDefaultBackground(false);
            entity.setBackgroundColor(value.get());
        } else {
            entity.setDefaultBackground(true);
            entity.setBackgroundColor(null);
        }
    }

    @Override
    public @NotNull String getName() {
        return "background";
    }

    @Override
    public @NotNull Class<TextDisplay> getEntityType() {
        return TextDisplay.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("background");
    }

    @Override
    public @NotNull Component getValueName(Optional<Color> value) {
        if (value.isPresent()) {
            Color color = value.get();
            if (color.getAlpha() == 0) {
                return Component.text("none", NamedTextColor.WHITE);
            }
            TextColor textColor = TextColor.color(color.asRGB());
            TextComponent hex = Component.text(textColor.asHexString(), textColor);
            if (textColor instanceof NamedTextColor) {
                return hex.append(Component.text(" (" + textColor + ")"));
            }
            return hex;
        } else {
            return Component.text("default", NamedTextColor.DARK_GRAY);
        }
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.text.background";
    }

    @Override
    public Optional<Color> getNextValue(TextDisplay entity) {
        if (entity.isDefaultBackground()) {
            return Optional.of(Color.fromARGB(0, 0, 0, 0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ItemStack createToggleButton(TextDisplay entity) {
        return Util.createItem(
                ItemType.STONE_SLAB,
                Component.text("Toggle background", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes the background", NamedTextColor.GRAY),
                        Component.text("behind the text.", NamedTextColor.GRAY),
                        Component.text("Use ", NamedTextColor.GRAY)
                                .append(Component.text("/eas text background", NamedTextColor.BLUE)),
                        Component.text("to set the color.", NamedTextColor.GRAY)
                )
        );
    }
}
