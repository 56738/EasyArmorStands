package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.button.BooleanToggleButton;
import me.m56738.easyarmorstands.property.button.PropertyButton;
import me.m56738.easyarmorstands.util.ConfigUtil;
import me.m56738.easyarmorstands.item.ItemTemplate;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public class BooleanTogglePropertyType extends BooleanPropertyType {
    protected ItemTemplate buttonTemplate;

    public BooleanTogglePropertyType(String key) {
        super(key);
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        buttonTemplate = ConfigUtil.getButton(config, "button");
    }

    @Override
    public @Nullable PropertyButton createButton(Property<Boolean> property, PropertyContainer container) {
        return new BooleanToggleButton(property, container, buttonTemplate);
    }
}
