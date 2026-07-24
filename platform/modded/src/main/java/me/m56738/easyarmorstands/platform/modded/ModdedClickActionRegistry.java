package me.m56738.easyarmorstands.platform.modded;

import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.event.ClickCallback;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

class ModdedClickActionRegistry {
    private final Map<Identifier, Entry> entries = new HashMap<>();

    public Identifier registerClickAction(BiConsumer<DialogResponseView, Audience> action, ClickCallback.Options options) {
        String path = "callback/" + UUID.randomUUID().toString().replace('-', '_');
        Identifier id = Identifier.fromNamespaceAndPath("easyarmorstands", path);
        Entry entry = new Entry(action, System.nanoTime(), options.lifetime().toNanos());
        synchronized (entries) {
            entries.put(id, entry);
        }
        return id;
    }

    public @Nullable BiConsumer<DialogResponseView, Audience> resolve(Identifier id) {
        Entry entry;
        synchronized (entries) {
            entry = entries.remove(id);
        }
        if (entry == null) {
            return null;
        }
        return entry.action();
    }

    public void clean() {
        long time = System.nanoTime();
        synchronized (entries) {
            entries.values().removeIf(e -> e.isExpiredAt(time));
        }
    }

    private record Entry(BiConsumer<DialogResponseView, Audience> action, long created, long lifetime) {
        private boolean isExpiredAt(long time) {
            long age = time - created;
            return age >= lifetime;
        }
    }
}
