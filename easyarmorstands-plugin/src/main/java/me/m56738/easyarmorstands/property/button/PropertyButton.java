package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.Tag;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public abstract class PropertyButton<T> implements MenuSlot {
    protected final Property<T> property;
    protected final PropertyContainer container;
    private final SimpleItemTemplate itemTemplate;

    public PropertyButton(Property<T> property, PropertyContainer container, SimpleItemTemplate item) {
        this.property = property;
        this.container = container;
        this.itemTemplate = item;
    }

    protected SimpleItemTemplate prepareTemplate(SimpleItemTemplate template) {
        return template;
    }

    protected void prepareTagResolver(TagResolver.Builder builder) {
        PropertyType<T> type = property.getType();
        builder.tag("name", Tag.selfClosingInserting(type.getName()));
        builder.tag("value", Tag.selfClosingInserting(type.getValueComponent(property.getValue())));
    }

    @Override
    public ItemStack getItem(Locale locale) {
        SimpleItemTemplate template = prepareTemplate(itemTemplate);
        TagResolver.Builder resolver = TagResolver.builder();
        prepareTagResolver(resolver);
        return template.render(locale, resolver.build());
    }
}
