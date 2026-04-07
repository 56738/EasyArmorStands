package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;

import java.util.List;
import java.util.Optional;

public class TextBackgroundToggleButton extends ToggleButton<Optional<Color>> {
    public TextBackgroundToggleButton(Property<Optional<Color>> property, MenuIcon icon, List<Component> description) {
        super(property, icon, description);
    }

    @Override
    public Optional<Color> getNextValue() {
        if (property.getValue().isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(Color.fromARGB(0, 0, 0, 0));
        }
    }

    @Override
    public Optional<Color> getPreviousValue() {
        return getNextValue();
    }
}
