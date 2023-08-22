package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.menu.position.MenuSlotPosition;
import me.m56738.easyarmorstands.util.ConfigUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigurablePropertyType<T> implements PropertyType<T> {
    private final @NotNull Key key;
    private final @NotNull Class<T> valueType;
    protected ItemTemplate buttonTemplate;
    private @Nullable String permission;
    private Component name;
    private MenuSlotPosition slotPosition;

    public ConfigurablePropertyType(@NotNull Key key, @NotNull Class<T> valueType) {
        this.key = key;
        this.valueType = valueType;
    }

    @Override
    public @NotNull Key key() {
        return key;
    }

    @Override
    public @NotNull Class<T> getValueType() {
        return valueType;
    }

    public void load(ConfigurationSection config) {
        permission = config.getString("permission");
        name = MiniMessage.miniMessage().deserialize(config.getString("name"));
        slotPosition = ConfigUtil.getMenuSlotPosition(config, "position");
        buttonTemplate = ConfigUtil.getButton(config, "button");
    }

    @Override
    public @Nullable String getPermission() {
        return permission;
    }

    @Override
    public Component getName() {
        if (name == null) {
            throw new IllegalStateException("Property not configured: " + key);
        }
        return name;
    }

    public @Nullable MenuSlot createSlot(Property<T> property, PropertyContainer container) {
        return null;
    }

    @Override
    public void populateMenu(MenuBuilder builder, Property<T> property, PropertyContainer container) {
        MenuSlot slot = createSlot(property, container);
        if (slot != null) {
            slotPosition.place(builder, slot);
        }
    }
}
