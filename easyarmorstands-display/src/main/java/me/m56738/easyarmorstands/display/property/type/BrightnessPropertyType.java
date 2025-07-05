package me.m56738.easyarmorstands.display.property.type;

import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.MiniMessage;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.Tag;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import org.bukkit.entity.Display.Brightness;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BrightnessPropertyType extends ConfigurablePropertyType<Optional<Brightness>> {
    private String valueTemplate;
    private Component none;

    public BrightnessPropertyType(@NotNull Key key) {
        super(key, new TypeToken<Optional<Brightness>>() {
        });
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        valueTemplate = config.node("value", "template").getString();
        none = config.node("value", "none").get(Component.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Optional<Brightness> value) {
        if (!value.isPresent()) {
            return none;
        }
        Brightness brightness = value.get();
        return MiniMessage.miniMessage().deserialize(valueTemplate,
                TagResolver.resolver("sky", Tag.selfClosingInserting(Component.text(brightness.getSkyLight()))),
                TagResolver.resolver("block", Tag.selfClosingInserting(Component.text(brightness.getBlockLight()))));
    }
}
