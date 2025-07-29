package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.util.Color;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperColorAdapter;
import org.bukkit.entity.TextDisplay;

import java.util.Optional;

public class TextDisplayBackgroundProperty implements Property<Optional<Color>> {
    private static final int DEFAULT_COLOR = 0x40000000;
    private final TextDisplay entity;

    public TextDisplayBackgroundProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Optional<Color>> getType() {
        return TextDisplayPropertyTypes.BACKGROUND;
    }

    @Override
    public Optional<Color> getValue() {
        if (entity.isDefaultBackground()) {
            return Optional.empty();
        }
        org.bukkit.Color color = entity.getBackgroundColor();
        if (color == null) {
            color = org.bukkit.Color.WHITE;
        } else if (color.asARGB() == DEFAULT_COLOR) {
            return Optional.empty();
        }
        return Optional.of(Color.ofARGB(color.asARGB()));
    }

    @Override
    public boolean setValue(Optional<Color> value) {
        entity.setDefaultBackground(value.isEmpty());
        entity.setBackgroundColor(value.map(PaperColorAdapter::toNative).orElse(null));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
