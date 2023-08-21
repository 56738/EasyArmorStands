package me.m56738.easyarmorstands.display.property.type;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.button.PropertyButton;
import me.m56738.easyarmorstands.display.property.button.TextBackgroundToggleButton;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import me.m56738.easyarmorstands.util.ConfigUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextBackgroundTogglePropertyType extends ConfigurablePropertyType<Color> {
    protected ItemTemplate buttonTemplate;

    public TextBackgroundTogglePropertyType(@NotNull Key key) {
        super(key, Color.class);
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        buttonTemplate = ConfigUtil.getButton(config, "button");
    }

    @Override
    public Component getValueComponent(Color value) {
        // TODO translate
        if (value != null) {
            if (value.getAlpha() == 0) {
                return Component.text("none", NamedTextColor.WHITE);
            }
            TextColor textColor = TextColor.color(value.asRGB());
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
    public @Nullable PropertyButton createButton(Property<Color> property, PropertyContainer container) {
        return new TextBackgroundToggleButton(property, container, buttonTemplate);
    }
}
