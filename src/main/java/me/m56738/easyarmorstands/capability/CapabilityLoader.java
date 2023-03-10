package me.m56738.easyarmorstands.capability;

import org.bukkit.plugin.Plugin;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
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
        for (Entry entry : capabilities.values()) {
            if (entry.instance == null && !entry.capability.optional()) {
                throw new IllegalStateException("Required capability not supported: " + entry.capability.name());
            }
        }
    }

    public boolean isCapability(Class<?> type) {
        return capabilities.containsKey(type);
    }

    public Collection<Entry> getCapabilities() {
        return Collections.unmodifiableCollection(capabilities.values());
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
        Entry entry = capabilities.computeIfAbsent(capability, Entry::new);
        entry.add(provider);
    }

    public static class Entry {
        private final Class<?> type;
        private final Capability capability;
        private final TreeSet<CapabilityProvider<?>> providers =
                new TreeSet<>(Comparator.comparing(CapabilityProvider::getPriority));
        private CapabilityProvider<?> provider;
        private Object instance;

        private Entry(Class<?> type) {
            this.type = type;
            this.capability = type.getAnnotation(Capability.class);
            if (this.capability == null) {
                throw new RuntimeException("Capability is not annotated: " + type.getName());
            }
        }

        public Class<?> getType() {
            return type;
        }

        public String getName() {
            return capability.name();
        }

        public CapabilityProvider<?> getProvider() {
            return provider;
        }

        public Object getInstance() {
            return instance;
        }

        public synchronized void add(CapabilityProvider<?> provider) {
            providers.add(provider);
        }

        public synchronized void load(Plugin plugin) {
            instance = null;
            for (CapabilityProvider<?> provider : providers) {
                try {
                    if (provider.isSupported()) {
                        this.provider = provider;
                        this.instance = provider.create(plugin);
                        return;
                    }
                } catch (Throwable e) {
                    plugin.getLogger().log(Level.SEVERE, "Failed to process " + provider.getClass().getName(), e);
                }
            }
        }
    }
}
