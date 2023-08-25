package me.m56738.easyarmorstands.display.property.type;

import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Display.Brightness;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class BrightnessPropertyType extends ConfigurablePropertyType<Brightness> {
    private String valueTemplate;
    private Component none;

    public BrightnessPropertyType(@NotNull Key key) {
        super(key, Brightness.class);
    }

    @Override
    public void load(CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        valueTemplate = config.node("value", "template").getString();
        none = config.node("value", "none").get(Component.class);
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
