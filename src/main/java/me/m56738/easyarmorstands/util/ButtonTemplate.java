package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Locale;

public class ButtonTemplate extends ItemTemplate {
    public ButtonTemplate(ItemTemplate template) {
        super(template);
    }

    @Override
    protected Component renderName(String name, Locale locale, TagResolver resolver) {
        return Message.buttonName(super.renderName(name, locale, resolver));
    }

    @Override
    protected Component renderLoreLine(String line, Locale locale, TagResolver resolver) {
        return Message.buttonDescription(super.renderLoreLine(line, locale, resolver));
    }
}
