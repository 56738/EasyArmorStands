package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.property.button.ComponentButton;
import me.m56738.easyarmorstands.api.property.button.PropertyButton;
import me.m56738.easyarmorstands.util.ConfigUtil;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public class ComponentPropertyType extends ConfigurablePropertyType<Component> {
    private final String command;
    private Component none;
    private ItemTemplate buttonTemplate;

    public ComponentPropertyType(String key, String command) {
        super(key);
        this.command = command;
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        none = MiniMessage.miniMessage().deserializeOr(config.getString("value.none"),
                Component.text("none", NamedTextColor.GRAY, TextDecoration.ITALIC));
        buttonTemplate = ConfigUtil.getButton(config, "button");
    }

    @Override
    public Component getValueComponent(Component value) {
        if (value == null) {
            return none;
        }
        return value;
    }

    @Override
    public @Nullable PropertyButton createButton(Property<Component> property, PropertyContainer container) {
        return new ComponentButton(property, container, buttonTemplate, command);
    }
}
