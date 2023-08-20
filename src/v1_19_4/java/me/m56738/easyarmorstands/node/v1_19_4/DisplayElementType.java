package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import me.m56738.easyarmorstands.util.ConfigUtil;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Display;

public class DisplayElementType<E extends Display> extends SimpleEntityElementType<E> {
    private final DisplayAddon addon;
    private final DisplayRootNodeFactory<E> factory;
    private ItemTemplate buttonTemplate;

    public DisplayElementType(DisplayAddon addon, Class<E> entityType, Component displayName, DisplayRootNodeFactory<E> factory) {
        super(entityType, displayName);
        this.addon = addon;
        this.factory = factory;
    }

    public void load(ConfigurationSection config, String key) {
        buttonTemplate = ConfigUtil.getButton(config, key)
                .addResolver(TagResolver.resolver("type", Tag.selfClosingInserting(getDisplayName())));
    }

    @Override
    protected SimpleEntityElement<E> createInstance(E entity) {
        return new DisplayElement<>(addon, entity, this, factory);
    }

    @Override
    public void applyDefaultProperties(PropertyRegistry properties) {
        super.applyDefaultProperties(properties);
        Property<Location> locationProperty = properties.get(PropertyTypes.ENTITY_LOCATION);
        Location location = locationProperty.getValue().clone();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
    }

    public ItemTemplate getButtonTemplate() {
        return buttonTemplate;
    }
}
