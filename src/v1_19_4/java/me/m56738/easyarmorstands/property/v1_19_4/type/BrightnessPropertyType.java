package me.m56738.easyarmorstands.property.v1_19_4.type;

import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Display.Brightness;
import org.jetbrains.annotations.NotNull;

public class BrightnessPropertyType extends ConfigurablePropertyType<Brightness> {
    private String valueTemplate;
    private Component none;

    public BrightnessPropertyType(@NotNull Key key) {
        super(key, Brightness.class);
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        valueTemplate = config.getString("value.template");
        none = MiniMessage.miniMessage().deserializeOr(config.getString("value.none"),
                Component.text("none", NamedTextColor.GRAY, TextDecoration.ITALIC));
    }

    @Override
    public Component getValueComponent(Brightness value) {
        if (value == null) {
            return none;
        }
        return MiniMessage.miniMessage().deserialize(valueTemplate,
                TagResolver.resolver("sky", Tag.selfClosingInserting(Component.text(value.getSkyLight()))),
                TagResolver.resolver("block", Tag.selfClosingInserting(Component.text(value.getBlockLight()))));
    }
}
