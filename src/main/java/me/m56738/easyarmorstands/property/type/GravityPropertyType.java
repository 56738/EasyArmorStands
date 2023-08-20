package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.button.GravityToggleButton;
import me.m56738.easyarmorstands.property.button.PropertyButton;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class GravityPropertyType extends BooleanTogglePropertyType {
    private List<String> canTickWarning = Collections.emptyList();

    public GravityPropertyType(String key) {
        super(key);
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        canTickWarning = config.getStringList("can-tick-warning");
    }

    @Override
    public @Nullable PropertyButton createButton(Property<Boolean> property, PropertyContainer container) {
        return new GravityToggleButton(property, container, buttonTemplate, canTickWarning);
    }
}
