package me.m56738.easyarmorstands.message;

import me.m56738.easyarmorstands.config.EasConfig;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.translation.Translator;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MessageManager {
    private static final Pattern PATTERN = Pattern.compile("messages_(.+)\\.properties");
    private static final Key key = Key.key("easyarmorstands", "translation");
    private final Plugin plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final Map<MessageStyle, String> styleTemplates = new HashMap<>();
    private final TranslatorImpl translator = new TranslatorImpl();
    private final TranslatableComponentRenderer<Locale> renderer = TranslatableComponentRenderer.usingTranslationSource(translator);
    private TranslationRegistry registry = TranslationRegistry.create(key);

    public MessageManager(Plugin plugin) {
        this.plugin = plugin;
        Message.messageManager = this;
    }

    public void load(EasConfig config) {
        styleTemplates.putAll(config.messageFormats);

        GlobalTranslator.translator().removeSource(registry);

        registry = TranslationRegistry.create(key);

        // Load default locale from included or custom messages.properties
        Path path = plugin.getDataFolder().toPath();
        Path defaultLocalePath = path.resolve("messages.properties");
        if (Files.exists(defaultLocalePath)) {
            registry.registerAll(Locale.US, defaultLocalePath, false);
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("me.m56738.easyarmorstands.messages", Locale.US, UTF8ResourceBundleControl.get());
            registry.registerAll(Locale.US, bundle, false);
        }

        // Load other locales from custom messages_*.properties
        try (Stream<Path> paths = Files.list(path)) {
            paths.forEach(this::load);
        } catch (NoSuchFileException ignored) {
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load messages", e);
        }

        if (config.serverSideTranslation) {
            GlobalTranslator.translator().addSource(registry);
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

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            registry.registerAll(locale, new PropertyResourceBundle(reader), false);
            plugin.getLogger().info("Loaded language: " + locale);
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

    public Translator translator() {
        return translator;
    }

    public ComponentRenderer<Locale> renderer() {
        return renderer;
    }

    private class TranslatorImpl implements Translator {
        @Override
        public @NotNull Key name() {
            return registry.name();
        }

        @Override
        public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
            return registry.translate(key, locale);
        }

        @Override
        public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale) {
            return registry.translate(component, locale);
        }
    }
}
