package me.m56738.easyarmorstands.item;

import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageStyle;

import java.util.Locale;

class StyleItemRenderer implements ItemRenderer {
    public static final StyleItemRenderer BUTTON = new StyleItemRenderer(
            TranslatorItemRenderer.INSTANCE,
            MessageStyle.BUTTON_NAME,
            MessageStyle.BUTTON_DESCRIPTION);

    private final ItemRenderer renderer;
    private final MessageStyle nameStyle;
    private final MessageStyle loreStyle;

    public StyleItemRenderer(ItemRenderer renderer, MessageStyle nameStyle, MessageStyle loreStyle) {
        this.renderer = renderer;
        this.nameStyle = nameStyle;
        this.loreStyle = loreStyle;
    }

    @Override
    public Component renderName(String input, Locale locale, TagResolver resolver) {
        return Message.format(nameStyle, renderer.renderName(input, locale, resolver));
    }

    @Override
    public Component renderLore(String input, Locale locale, TagResolver resolver) {
        return Message.format(loreStyle, renderer.renderLore(input, locale, resolver));
    }
}
