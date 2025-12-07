package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.util.Color;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperColorAdapter;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Display;

import java.util.Optional;

public class DisplayGlowColorProperty extends EntityProperty<Display, Optional<Color>> {
    public DisplayGlowColorProperty(Display entity) {
        super(entity);
    }

    @Override
    public PropertyType<Optional<Color>> getType() {
        return DisplayPropertyTypes.GLOW_COLOR;
    }

    @Override
    public Optional<Color> getValue() {
        return Optional.ofNullable(entity.getGlowColorOverride())
                .map(c -> Color.ofARGB(c.asARGB()));
    }

    @Override
    public boolean setValue(Optional<Color> value) {
        entity.setGlowColorOverride(value
                .map(PaperColorAdapter::toNative)
                .orElse(null));
        return true;
    }
}
