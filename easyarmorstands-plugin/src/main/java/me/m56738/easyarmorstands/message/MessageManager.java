package me.m56738.easyarmorstands.message;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.MiniMessage;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.Tag;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import me.m56738.easyarmorstands.lib.kyori.adventure.translation.GlobalTranslator;
import me.m56738.easyarmorstands.lib.kyori.adventure.translation.Translator;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MessageManager {
    private static final Pattern PATTERN = Pattern.compile("(.+)\\.json");
    private static final Key key = Key.key("easyarmorstands", "translation");
    private final Plugin plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final Map<MessageStyle, String> styleTemplates = new HashMap<>();
    private final Set<Locale> loadedLocales = new HashSet<>();
    private PatternTranslationStore registry = new PatternTranslationStore(key);

    public MessageManager(Plugin plugin) {
        this.plugin = plugin;
        Message.messageManager = this;
    }

    public void load(EasConfig config) {
        styleTemplates.putAll(config.message.format);

        GlobalTranslator.translator().removeSource(registry);

        registry = new PatternTranslationStore(key);
        loadedLocales.clear();

        // Convert old message files
        Path dataPath = plugin.getDataFolder().toPath();
        Path langPath = dataPath.resolve("lang");
        try (Stream<Path> paths = Files.list(dataPath)) {
            paths.forEach(path -> {
                try {
                    if (MessageMigrator.migrate(path, langPath)) {
                        plugin.getLogger().info("Migrated custom messages: " + path.getFileName().toString());
                    }
                } catch (Exception e) {
                    plugin.getLogger().log(Level.SEVERE, "Failed to convert old message file: " + path.getFileName().toString(), e);
                }
            });
        } catch (NoSuchFileException ignored) {
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to convert old message files", e);
        }

        // Load custom locales
        try (Stream<Path> paths = Files.list(langPath)) {
            paths.forEach(this::load);
        } catch (NoSuchFileException ignored) {
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load messages", e);
        }

        // Load included default locales
        try {
            loadDefaultLocale(Locale.US);
            loadDefaultLocale(Locale.GERMANY);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load default messages", e);
        }

        if (config.message.serverSideTranslation) {
            GlobalTranslator.translator().addSource(registry);
        }
    }

    private void loadDefaultLocale(Locale locale) throws IOException {
        if (loadedLocales.add(locale)) {
            String name = "/assets/easyarmorstands/lang/" + locale.toString().toLowerCase(Locale.ROOT) + ".json";
            InputStream resource = getClass().getResourceAsStream(name);
            if (resource != null) {
                registry.readLocale(resource, locale);
            } else {
                plugin.getLogger().warning("Default messages for locale " + locale + " are missing: " + name);
            }
        }
    }

    private void load(Path path) {
        Matcher matcher = PATTERN.matcher(path.getFileName().toString());
        if (!matcher.matches()) {
            return;
        }

        Locale locale = Translator.parseLocale(matcher.group(1));
        if (locale == null) {
            plugin.getLogger().warning("Invalid locale: " + matcher.group(1));
            return;
        }

        if (!loadedLocales.add(locale)) {
            return;
        }

        try {
            registry.readLocale(path, locale);
            plugin.getLogger().info("Loaded custom messages for language: " + locale);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load translations from " + path.getFileName(), e);
        }
    }

    public Component format(MessageStyle style, Component message) {
        return format(style, message, TagResolver.empty());
    }

    public Component format(MessageStyle style, Component message, TagResolver resolver) {
        return miniMessage.deserialize(
                styleTemplates.get(style),
                TagResolver.builder()
                        .tag("message", Tag.selfClosingInserting(message))
                        .resolver(resolver)
                        .build());
    }
}
