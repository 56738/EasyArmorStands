package me.m56738.easyarmorstands.message;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.Translator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TranslationManager {
    private static final Pattern PATTERN = Pattern.compile("(.+)\\.json");
    private static final Key key = Key.key("easyarmorstands", "translation");
    private final Set<Locale> loadedLocales = new HashSet<>();
    private PatternTranslationStore registry = new PatternTranslationStore(key);

    public void load(Path dataFolder, ComponentLogger logger) {
        GlobalTranslator.translator().removeSource(registry);

        registry = new PatternTranslationStore(key);
        loadedLocales.clear();

        // Convert old message files
        Path langPath = dataFolder.resolve("lang");
        try (Stream<Path> paths = Files.list(dataFolder)) {
            paths.forEach(path -> {
                try {
                    if (MessageMigrator.migrate(path, langPath)) {
                        logger.info("Migrated custom messages: {}", path.getFileName().toString());
                    }
                } catch (Exception e) {
                    logger.error("Failed to convert old message file: {}", path.getFileName().toString(), e);
                }
            });
        } catch (NoSuchFileException ignored) {
        } catch (IOException e) {
            logger.error("Failed to convert old message files", e);
        }

        // Load custom locales
        try (Stream<Path> paths = Files.list(langPath)) {
            paths.forEach(path -> loadFile(path, logger));
        } catch (NoSuchFileException ignored) {
        } catch (IOException e) {
            logger.error("Failed to load messages", e);
        }

        // Load included default locales
        try {
            loadDefaultLocale(Locale.of("en", "us"), logger);
            loadDefaultLocale(Locale.of("de", "de"), logger);
            loadDefaultLocale(Locale.of("ru", "ru"), logger);
        } catch (IOException e) {
            logger.error("Failed to load default messages", e);
        }

        GlobalTranslator.translator().addSource(registry);
    }

    private void loadDefaultLocale(Locale locale, ComponentLogger logger) throws IOException {
        if (loadedLocales.add(locale)) {
            String name = "/assets/easyarmorstands/lang/" + locale.toString().toLowerCase(Locale.ROOT) + ".json";
            InputStream resource = getClass().getResourceAsStream(name);
            if (resource != null) {
                registry.readLocale(resource, locale);
            } else {
                logger.warn("Default messages for locale {} are missing: {}", locale, name);
            }
        }
    }

    private void loadFile(Path path, ComponentLogger logger) {
        Matcher matcher = PATTERN.matcher(path.getFileName().toString());
        if (!matcher.matches()) {
            return;
        }

        Locale locale = Translator.parseLocale(matcher.group(1));
        if (locale == null) {
            logger.warn("Invalid locale: {}", matcher.group(1));
            return;
        }

        if (!loadedLocales.add(locale)) {
            return;
        }

        try {
            registry.readLocale(path, locale);
            logger.info("Loaded custom messages for language: {}", locale);
        } catch (Exception e) {
            logger.error("Failed to load translations from {}", path.getFileName(), e);
        }
    }
}
