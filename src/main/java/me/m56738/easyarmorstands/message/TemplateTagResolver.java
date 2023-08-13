package me.m56738.easyarmorstands.message;

import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TemplateTagResolver implements TagResolver {
    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
        if (!has(name)) {
            return null;
        }

        String templateName = arguments.popOr("Template name expected.").value();
        String templateValue = MessageLoader.getConfig().getString("templates." + templateName);
        return Tag.preProcessParsed(templateValue);
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("template") || name.equals("t");
    }
}
