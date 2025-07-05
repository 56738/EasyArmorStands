package me.m56738.easyarmorstands.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.translation.Translator;

import java.util.Locale;

public interface ItemRenderer {
    static ItemRenderer item() {
        return TranslatorItemRenderer.INSTANCE;
    }

    static ItemRenderer item(Translator translator) {
        return new TranslatorItemRenderer(TranslatableComponentRenderer.usingTranslationSource(translator));
    }

    static ItemRenderer button() {
        return StyleItemRenderer.BUTTON;
    }

    Component renderName(String input, Locale locale, TagResolver resolver);

    Component renderLore(String input, Locale locale, TagResolver resolver);
}
