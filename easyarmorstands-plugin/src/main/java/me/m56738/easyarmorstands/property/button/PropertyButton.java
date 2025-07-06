package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public abstract class PropertyButton<T> implements MenuSlot {
    private final Element element;
    private final PropertyType<T> type;
    private final SimpleItemTemplate itemTemplate;
    private final Property<T> untrackedProperty;

    public PropertyButton(Element element, PropertyType<T> type, SimpleItemTemplate item) {
        this.element = element;
        this.type = type;
        this.itemTemplate = item;
        this.untrackedProperty = element.getProperties().get(type);
    }

    protected Element getElement() {
        return element;
    }

    protected PropertyType<T> getType() {
        return type;
    }

    protected Property<T> getUntrackedProperty() {
        return untrackedProperty;
    }

    protected Property<T> getProperty(ChangeContext context) {
        return context.getProperties(element).get(type);
    }

    protected SimpleItemTemplate prepareTemplate(SimpleItemTemplate template) {
        return template;
    }

    protected void prepareTagResolver(TagResolver.Builder builder) {
        builder.tag("name", Tag.selfClosingInserting(type.getName()));
        builder.tag("value", Tag.selfClosingInserting(type.getValueComponent(untrackedProperty.getValue())));
    }

    @Override
    public ItemStack getItem(Locale locale) {
        SimpleItemTemplate template = prepareTemplate(itemTemplate);
        TagResolver.Builder resolver = TagResolver.builder();
        prepareTagResolver(resolver);
        return template.render(locale, resolver.build());
    }
}
