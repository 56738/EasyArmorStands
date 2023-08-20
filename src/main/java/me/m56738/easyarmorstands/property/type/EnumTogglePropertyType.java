package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.button.EnumToggleButton;
import me.m56738.easyarmorstands.property.button.PropertyButton;
import me.m56738.easyarmorstands.util.ConfigUtil;
import me.m56738.easyarmorstands.item.ItemTemplate;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public class EnumTogglePropertyType<T extends Enum<T>> extends EnumPropertyType<T> {
    protected ItemTemplate buttonTemplate;

    public EnumTogglePropertyType(String key, Class<T> type) {
        super(key, type);
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        buttonTemplate = ConfigUtil.getButton(config, "button");
    }

    @Override
    public @Nullable PropertyButton createButton(Property<T> property, PropertyContainer container) {
        return new EnumToggleButton<>(property, container, buttonTemplate, values);
    }
}
