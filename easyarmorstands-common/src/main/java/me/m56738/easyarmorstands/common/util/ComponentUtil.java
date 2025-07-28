package me.m56738.easyarmorstands.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;

import static net.kyori.adventure.text.format.Style.style;

@NullMarked
public final class ComponentUtil {
    private static final Style ITEM_FALLBACK_STYLE =
            style(NamedTextColor.WHITE, TextDecoration.ITALIC.withState(false));

    private ComponentUtil() {
    }

    public static Component render(Component component, Locale locale) {
        return GlobalTranslator.render(component, locale);
    }

    public static Component renderForItem(Component component, Locale locale) {
        return render(component.applyFallbackStyle(ITEM_FALLBACK_STYLE), locale);
    }
}
