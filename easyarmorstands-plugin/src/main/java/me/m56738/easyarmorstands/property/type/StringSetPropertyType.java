package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.TextComponent;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.MiniMessage;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

public class StringSetPropertyType extends ConfigurablePropertyType<Set<String>> {
    private Component separator;
    private Component empty;
    private Integer threshold;
    private String multiple;

    public StringSetPropertyType(@NotNull Key key) {
        super(key, new TypeToken<Set<String>>() {
        });
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        separator = config.node("value", "separator").get(Component.class);
        empty = config.node("value", "empty").get(Component.class);
        threshold = config.node("value", "threshold").get(Integer.class);
        multiple = config.node("value", "multiple").get(String.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Set<String> value) {
        if (threshold != null && threshold >= 0 && value.size() >= threshold) {
            return MiniMessage.miniMessage().deserialize(multiple,
                    Placeholder.component("amount", Component.text(value.size())));
        }

        TextComponent.Builder builder = Component.text();
        boolean first = true;

        for (String s : value) {
            if (!first) {
                builder.append(separator);
            }
            builder.append(Component.text(s));
            first = false;
        }

        if (first) {
            return empty;
        }

        return builder.build();
    }

    @Override
    public @NotNull Set<String> cloneValue(@NotNull Set<String> value) {
        return new LinkedHashSet<>(value);
    }
}
