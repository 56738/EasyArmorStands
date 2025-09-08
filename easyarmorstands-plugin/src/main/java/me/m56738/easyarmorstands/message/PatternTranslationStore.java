package me.m56738.easyarmorstands.message;

import com.google.gson.stream.JsonReader;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.TextComponent;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.TranslatableComponent;
import me.m56738.easyarmorstands.lib.kyori.adventure.translation.AbstractTranslationStore;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NotNullByDefault
public class PatternTranslationStore extends AbstractTranslationStore<String> {
    private static final Pattern ARGUMENT_PATTERN = Pattern.compile("%((\\d+)\\$)?s");

    public PatternTranslationStore(Key name) {
        super(name);
    }

    @Override
    public @Nullable MessageFormat translate(String key, Locale locale) {
        return null;
    }

    public void readLocale(Path input, Locale locale) throws IOException {
        readLocale(Files.newBufferedReader(input, StandardCharsets.UTF_8), locale);
    }

    public void readLocale(InputStream input, Locale locale) throws IOException {
        readLocale(new BufferedReader(new InputStreamReader(input)), locale);
    }

    public void readLocale(Reader reader, Locale locale) throws IOException {
        try (JsonReader jsonReader = new JsonReader(reader)) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                String value = jsonReader.nextString();
                register(name, locale, value);
            }
            jsonReader.endObject();
        }
    }

    @Override
    public @Nullable Component translate(TranslatableComponent component, Locale locale) {
        String pattern = translationValue(component.key(), locale);
        if (pattern == null) {
            return null;
        }
        TextComponent.Builder builder = Component.text();
        Matcher matcher = ARGUMENT_PATTERN.matcher(pattern);
        IndexParser indexParser = new IndexParser();
        int lastEnd = 0;
        while (matcher.find()) {
            // text before match
            String before = pattern.substring(lastEnd, matcher.start());
            if (!before.isEmpty()) {
                builder.append(Component.text(before));
            }

            // value of match
            int index = indexParser.parseIndex(matcher.group(2));
            if (index >= 1 && index <= component.arguments().size()) {
                builder.append(component.arguments().get(index - 1));
            } else {
                builder.append(Component.text(matcher.group()));
            }

            lastEnd = matcher.end();
        }

        // text after last match
        String tail = pattern.substring(lastEnd);
        if (!tail.isEmpty()) {
            builder.append(Component.text(tail));
        }

        return builder.append(component.children()).style(component.style()).build().compact();
    }

    private static class IndexParser {
        private int counter;

        public int parseIndex(@Nullable String input) {
            int index;
            if (input != null) {
                index = Integer.parseInt(input);
            } else {
                index = ++counter;
            }
            return index;
        }
    }
}
