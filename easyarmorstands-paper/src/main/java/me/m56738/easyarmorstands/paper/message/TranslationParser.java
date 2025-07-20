package me.m56738.easyarmorstands.paper.message;

import com.google.gson.stream.JsonReader;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.TranslationStore;
import net.kyori.adventure.translation.Translator;
import org.jspecify.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TranslationParser {
    private static final Pattern ARGUMENT_PATTERN = Pattern.compile("%(\\d+\\$)?s");

    private TranslationParser() {
    }

    public static Translator read(@Nullable InputStream input, Key key) {
        TranslationStore<MessageFormat> registry = TranslationStore.messageFormat(key);
        if (input == null) {
            return registry;
        }
        try (JsonReader reader = new JsonReader(new InputStreamReader(new BufferedInputStream(input)))) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                String value = reader.nextString();
                MessageFormat format = new MessageFormat(convertValue(value), Locale.US);
                registry.register(name, Locale.US, format);
            }
            reader.endObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return registry;
    }

    private static String convertValue(String value) {
        Matcher matcher = ARGUMENT_PATTERN.matcher(value);
        StringBuilder builder = new StringBuilder();
        int autoIndex = 0;
        while (matcher.find()) {
            String index = matcher.group(1);
            if (index == null) {
                index = String.valueOf(autoIndex);
                autoIndex++;
            }
            matcher.appendReplacement(builder, "{" + index + "}");
        }
        matcher.appendTail(builder);
        return builder.toString();
    }
}
