package me.m56738.easyarmorstands.display.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.property.button.TextBackgroundToggleButton;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextBackgroundTogglePropertyType extends ConfigurablePropertyType<Color> {
    public TextBackgroundTogglePropertyType(@NotNull Key key) {
        super(key, Color.class);
    }

    @Override
    public Component getValueComponent(Color value) {
        if (value != null) {
            if (value.getAlpha() == 0) {
                return Message.component("easyarmorstands.property.text-display.background.none");
            }
            TextColor textColor = TextColor.color(value.asRGB());
            return Component.text(textColor.asHexString(), textColor);
        } else {
            return Message.component("easyarmorstands.property.text-display.background.default").color(NamedTextColor.DARK_GRAY);
        }
    }

    @Override
    public @Nullable MenuSlot createSlot(Property<Color> property, PropertyContainer container) {
        return new TextBackgroundToggleButton(property, container, buttonTemplate);
    }
}
