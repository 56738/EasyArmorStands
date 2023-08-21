package me.m56738.easyarmorstands.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Locale;

class TranslatorItemRenderer implements ItemRenderer {
    public static final TranslatorItemRenderer INSTANCE = new TranslatorItemRenderer(GlobalTranslator.renderer());

    private final TranslatableComponentRenderer<Locale> renderer;

    public TranslatorItemRenderer(TranslatableComponentRenderer<Locale> renderer) {
        this.renderer = renderer;
    }

    private Component render(String input, Locale locale, TagResolver resolver) {
        return renderer.render(MiniMessage.miniMessage().deserialize(input, resolver), locale);
    }

    @Override
    public Component renderName(String input, Locale locale, TagResolver resolver) {
        return render(input, locale, resolver);
    }

    @Override
    public Component renderLore(String input, Locale locale, TagResolver resolver) {
        return render(input, locale, resolver);
    }
}
