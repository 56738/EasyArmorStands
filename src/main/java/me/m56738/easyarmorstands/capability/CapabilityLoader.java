package me.m56738.easyarmorstands.capability;

import org.bukkit.plugin.Plugin;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeSet;
import java.util.logging.Level;

public class CapabilityLoader {
    private final Plugin plugin;
    private final Map<Class<?>, Entry> capabilities = new HashMap<>();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public CapabilityLoader(Plugin plugin, ClassLoader classLoader) {
        this.plugin = plugin;
        for (CapabilityProvider<?> provider : ServiceLoader.load(CapabilityProvider.class, classLoader)) {
            try {
                for (Type genericInterface : provider.getClass().getGenericInterfaces()) {
                    if (genericInterface instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                        Type rawType = parameterizedType.getRawType();
                        if (rawType == CapabilityProvider.class) {
                            Type typeArgument = parameterizedType.getActualTypeArguments()[0];
                            if (typeArgument instanceof Class) {
                                register((Class) typeArgument, provider);
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to load " + provider.getClass().getName(), e);
            }
        }
    }

    public void load() {
        for (Map.Entry<Class<?>, Entry> entry : capabilities.entrySet()) {
            entry.getValue().load(plugin);
        }
    }

    public boolean isCapability(Class<?> type) {
        return capabilities.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> capability) {
        Entry entry = capabilities.get(capability);
        if (entry == null) {
            return null;
        }
        return (T) entry.instance;
    }

    public <T> void register(Class<T> capability, CapabilityProvider<T> provider) {
        Entry entry = capabilities.computeIfAbsent(capability, c -> new Entry());
        entry.add(provider);
    }

    private class Entry {
        private final TreeSet<CapabilityProvider<?>> providers =
                new TreeSet<>(Comparator.comparing(CapabilityProvider::getPriority));
        private Object instance;

        public synchronized void add(CapabilityProvider<?> provider) {
            providers.add(provider);
        }

        public synchronized void load(Plugin plugin) {
            instance = null;
            for (CapabilityProvider<?> provider : providers) {
                try {
                    if (provider.isSupported()) {
                        plugin.getLogger().info("Using " + provider.getClass().getName());
                        instance = provider.create(plugin);
                        break;
                    }
                } catch (Throwable e) {
                    plugin.getLogger().log(Level.SEVERE, "Failed to process " + provider.getClass().getName(), e);
                }
            }
        }
    }
}
