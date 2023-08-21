package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.button.PropertyButton;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.property.button.EnumToggleButton;
import me.m56738.easyarmorstands.util.ConfigUtil;
import net.kyori.adventure.key.Key;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnumTogglePropertyType<T extends Enum<T>> extends EnumPropertyType<T> {
    protected ItemTemplate buttonTemplate;

    public EnumTogglePropertyType(@NotNull Key key, Class<T> type) {
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
